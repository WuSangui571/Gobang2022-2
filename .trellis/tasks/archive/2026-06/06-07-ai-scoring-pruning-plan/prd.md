# Improve AI Scoring and Pruning

## Goal

Improve the current Gobang AI so obvious tactical moves are found quickly and consistently. The weakness to address is not only search depth; the current scoring, candidate ordering, and pruning design can make the AI think too long about positions that should be decided by tactical priority.

This Codex round is planning only. Business code must not be changed until the implementation handoff.

## Task Scope Classification

Complex Task.

Reasons:

- The change touches board evaluation, candidate generation, move ordering, alpha-beta pruning behavior, and deterministic benchmark evidence.
- Multiple valid implementation approaches exist: weight tuning only, tactical pre-check expansion, richer move ordering, candidate caps, or iterative deepening.
- Regressions are easy in immediate win/block, forbidden-hand legality, full-board behavior, and Swing responsiveness.
- The implementation must be position-first and test-backed, not subjective manual play.

## Current Project State

- Branch at planning start: `main`.
- Working tree at planning start: clean.
- Recent AI work already implemented configurable alpha-beta search in `Shou.getAnswer`, `AiSearchConfig`, and `SearchResult`.
- Existing harness is `src/test/java/com/ztydwz/gobang2022/Service/AiSearchHarness.java`.
- The project has no Maven or Gradle build. Compile and harness checks are run with `javac` and `java -cp .tmp/classes ...`.
- Trellis `task.py init-context` is not available in this version; context files are seeded by `task.py create` and curated through `task.py add-context`.

## Requirements

1. Keep the AI public integration surface stable:
   - `AI.getLocation()` returns `int[] {row, col}`.
   - `Shou.getAnswer(int[][] map, int MAX_ROW, int MAX_COLUMN, int enemyColor, int aiColor)` remains the core move-selection entry point.
   - Board encoding remains `0` empty, `1` black, `2` white.
2. Improve tactical priority before deep search:
   - Immediate own win must be selected without unnecessary full-depth search.
   - Immediate opponent win must be blocked unless AI has its own immediate win.
   - Own open four / forcing four and opponent open four / forcing four block must outrank low-value positional gains.
   - Own open three / jump three and opponent open three / jump three block should influence move ordering before general positional score.
3. Improve scoring design:
   - Avoid relying only on five-cell tuple totals from `getFiveGrade`.
   - Score attack and defense separately enough that a forced block cannot be outweighed by several weak own patterns.
   - Use large separated weights for tactical classes: win, open four, blocked/rush four, open three, blocked three, open two, positional/center bonus.
   - Prefer a small, named set of scoring constants rather than hidden magic numbers.
4. Improve candidate pruning and ordering:
   - Keep neighborhood pruning after stones exist; do not search all 225 cells in normal mid-game positions.
   - Preserve edge legality: do not exclude edge moves just because they are on the board edge.
   - Always include tactical win/block candidates even if a normal neighborhood or cap would exclude them.
   - Sort candidates deterministically before recursion.
   - Add a bounded candidate cap only after tactical candidates are protected.
5. Improve avoidable long thinking:
   - Representative obvious tactical positions should complete quickly at LOW and MEDIUM strength.
   - HIGH strength may still think deeper, but obvious immediate win/block should not wait for the full time budget.
   - If new diagnostics are added, they must be outside tight loops and optional or harness-only.
6. Preserve forbidden-hand behavior:
   - Black-only forbidden-hand rules must not be applied to white.
   - If forbidden hands are disabled/allowed by current settings, AI may consider otherwise forbidden black moves.
   - If forbidden hands are enabled and disallowed, candidate generation/search must not choose illegal black moves.
7. Preserve supported modes and existing integration:
   - Free-start human vs AI.
   - Designated-start human vs AI including exchange/five-N-da flow.
   - Two-player mode unaffected.
   - Two-AI mode still able to run.
8. Add or update deterministic harness coverage:
   - Existing tests must continue to pass.
   - Add position-first tests that prove the specific scoring/pruning improvement.
   - Add benchmark evidence for before/after or at least new representative elapsed-time/candidate evidence after implementation.

## API / Command / Payload Contract

No external API, database, storage, network, or DTO contract is introduced.

Internal command/test contract:

```powershell
New-Item -ItemType Directory -Force -Path .tmp\classes | Out-Null
Get-ChildItem -Recurse -Path src -Filter *.java | ForEach-Object { $_.FullName } | Set-Content -Encoding ASCII .tmp\sources.txt
javac -encoding UTF-8 -d .tmp\classes "@.tmp\sources.txt"
Copy-Item -Recurse -Force src\main\java\com\ztydwz\gobang2022\image .tmp\classes\main\java\com\ztydwz\gobang2022\
Copy-Item -Recurse -Force src\main\java\com\ztydwz\gobang2022\config .tmp\classes\main\java\com\ztydwz\gobang2022\
java -cp .tmp\classes com.ztydwz.gobang2022.Service.AiSearchHarness
```

Internal Java payload/return shapes to preserve:

```java
int[][] board; // 15x15, 0 empty, 1 black, 2 white
int aiColor;   // 1 black, 2 white
int enemyColor = 3 - aiColor;
int[] move = {row, col}; // {-1, -1} when no legal move exists
SearchResult(score, row, col, depth, elapsedMs);
```

If new internal helper types are added, keep them package-local or narrowly scoped under `Service` unless a broader engine extraction is explicitly approved later.

## Validation / Error Matrix

| Case | Input / State | Expected Behavior | Assertion Point |
|---|---|---|---|
| Own immediate win | AI has four in a row with open winning point | Return winning row/col quickly | Harness exact move or allowed winning endpoints; elapsed below tactical threshold |
| Opponent immediate win | Opponent has four in a row and AI has no immediate win | Return blocking row/col | Harness exact block |
| Own win vs opponent threat | Both sides have tactical threats, AI can win now | Choose own win | Harness exact winning move |
| Opponent open four | Opponent can create/has forcing four | Candidate ordering prioritizes block | Harness expected move/reason |
| Own open four | AI can create forcing four | Prefer forcing move over low positional score | Harness expected move/reason |
| Open three / jump three | Tactical setup exists but no immediate five | Ranking reflects threat class deterministically | Harness stable move and reason |
| Candidate cap | Dense mid-game board | Search does not evaluate all empty cells; tactical candidates retained | Benchmark note or SearchResult/diagnostic count |
| Edge tactical point | Edge move wins or blocks | Edge point remains legal candidate | Harness exact edge move where possible |
| Black forbidden move disallowed | Black AI candidate is forbidden and setting rejects it | Do not choose forbidden move | Harness legality check |
| White forbidden-like pattern | White AI has same pattern shape | Do not apply black forbidden filter | Harness valid white move |
| Full board | No empty legal move | Return `{-1, -1}` | Existing harness |
| Input board | Search simulates moves | Original board remains unchanged | Existing and updated harness |
| Time budget | LOW/MEDIUM/HIGH settings | Stay within configured limit; obvious tactics finish well below limit | Harness elapsed assertions or benchmark output |

## Good / Base / Bad Cases

Good cases:

- AI has four connected stones and selects the fifth point immediately.
- Opponent has four connected stones and AI blocks at the only legal point.
- AI chooses a forcing open-four move instead of a center-biased but tactically weak point.
- A tactical edge point is selected when it wins or blocks.
- Candidate list is bounded in mid-game positions without losing forced tactical moves.

Base cases:

- Empty board returns center.
- Existing mid-game benchmark remains deterministic.
- Existing LOW/MEDIUM/HIGH depth mapping remains valid unless explicitly adjusted in this PRD with test updates.
- Full board returns `{-1, -1}`.
- No mutation of caller-provided board.

Bad cases:

- AI searches many low-value local points while missing or delaying an immediate block.
- Several open-two or center bonuses outweigh opponent four/five prevention.
- Candidate cap removes the only winning/blocking point.
- Edge points are excluded blindly.
- Black forbidden-hand filtering is accidentally applied to white.
- New debug output prints every searched node.
- Algorithm behavior depends on Swing mouse movement frequency.

## Acceptance Criteria

- [ ] Existing `AiSearchHarness` tests still pass.
- [ ] Add at least three new position-first harness cases for this task:
  - [ ] own forcing-four/open-four preference,
  - [ ] opponent forcing-four/open-four block preference,
  - [ ] obvious tactical move returns quickly without exhausting LOW/MEDIUM time budget.
- [ ] Candidate ordering is deterministic and documented by tests or benchmark output.
- [ ] Candidate pruning/cap, if introduced, protects immediate win/block and forcing-threat candidates.
- [ ] Search/evaluation does not mutate input board.
- [ ] Forbidden-hand behavior remains covered for black and white.
- [ ] Compile command passes with `javac -encoding UTF-8`.
- [ ] Manual Swing smoke verifies free-start human-vs-AI and one designated-start path still reach normal AI play.

## Technical Approach

Recommended MVP: Tactical ordering and evaluation refinement, not a full engine rewrite.

1. Add position-first harness cases before changing scoring.
2. Extract or add small internal helpers in `Shou` for move threat classification if needed.
3. Expand move ordering to rank:
   - own immediate win,
   - opponent immediate win block,
   - own open/forcing four,
   - opponent open/forcing four block,
   - own open/jump three,
   - opponent open/jump three block,
   - local density / center / deterministic row-col tie-breaker.
4. Refine evaluator weights so tactical classes dominate positional bonuses.
5. Only after tactical ordering is protected, add a candidate cap for recursion if benchmark evidence shows too many candidates.
6. Keep `AI.getLocation` and controller integration unchanged unless a narrow guard is needed for invalid `{-1, -1}`.

## Decision (ADR-lite)

Context: The prior alpha-beta task added depth, time budgets, deterministic sorting, and baseline tests. The current user feedback is that some obvious positions still take too much thought, which points to scoring and move ordering quality rather than simply needing more depth.

Decision: Improve tactical classification, candidate ordering, and evaluator weights first. Add candidate caps only with protected tactical inclusion. Do not rewrite the Renju engine, build system, or Swing controllers in this task.

Consequences: This keeps the implementation narrow and testable, but it will not make the AI a complete professional Gomoku engine. Future tasks can extract a pure board engine, add iterative deepening/transposition tables, or replace forbidden-hand logic after position coverage is stronger.

## Out of Scope

- No full rewrite of `Shou.java`.
- No package-name migration.
- No Maven/Gradle build migration.
- No database/API/storage/network changes.
- No Swing redesign or settings UI redesign.
- No full Renju forbidden-hand engine replacement.
- No transposition table, opening book, Monte Carlo, neural model, or external AI engine unless a future task explicitly approves it.
- No business-code changes in this Codex planning round.

## Files Likely To Modify Later

- `src/main/java/com/ztydwz/gobang2022/Service/Shou.java`
- `src/main/java/com/ztydwz/gobang2022/Service/AiSearchConfig.java` only if candidate caps or tactical time thresholds become configurable.
- `src/main/java/com/ztydwz/gobang2022/Service/SearchResult.java` only if candidate count or node count is added as benchmark evidence.
- `src/test/java/com/ztydwz/gobang2022/Service/AiSearchHarness.java`

Files to avoid unless a narrow integration defect is found:

- `src/main/java/com/ztydwz/gobang2022/Controller/**`
- `src/main/java/com/ztydwz/gobang2022/Model/GameDialog.java`
- `src/main/java/com/ztydwz/gobang2022/Model/Static.java`
- build/project metadata files

## Required Tests

Compile:

```powershell
New-Item -ItemType Directory -Force -Path .tmp\classes | Out-Null
Get-ChildItem -Recurse -Path src -Filter *.java | ForEach-Object { $_.FullName } | Set-Content -Encoding ASCII .tmp\sources.txt
javac -encoding UTF-8 -d .tmp\classes "@.tmp\sources.txt"
Copy-Item -Recurse -Force src\main\java\com\ztydwz\gobang2022\image .tmp\classes\main\java\com\ztydwz\gobang2022\
Copy-Item -Recurse -Force src\main\java\com\ztydwz\gobang2022\config .tmp\classes\main\java\com\ztydwz\gobang2022\
```

Harness:

```powershell
java -cp .tmp\classes com.ztydwz.gobang2022.Service.AiSearchHarness
```

Manual smoke:

- Start Swing app from `Start.main`.
- Open settings and choose human-vs-AI free start.
- Make legal moves until AI responds at least twice.
- Verify no repeated AI move on mouse movement and no frozen UI for ordinary tactical positions.
- Run one designated-start path through exchange/five-N-da into normal AI play.

## Technical Notes

- Relevant prior task: `.trellis/tasks/archive/2026-06/06-06-alpha-beta-ai/`.
- Existing `Shou.getAnswer` already copies input board, uses `AiSearchConfig`, checks empty board, finds own immediate winning move, generates radius-2 candidates, sorts candidates, runs negamax, and returns first legal fallback.
- Existing `getSortScore` only explicitly recognizes own immediate five and opponent immediate five; lower tactical classes mostly depend on `getGrade(board, aiColor) / 5` plus center distance.
- Existing `getGrade` scans five-cell windows and uses `10^ownCount` versus `-1.2 * 10^enemyCount`; it does not directly classify open four, blocked four, open three, or jump three.
- Existing public helpers `liveFour`, `rushFour`, `newLiveThree`, `jumpLiveThree`, and `longSix` can support tactical classification, but they are large legacy methods and must be used carefully.
- Legacy `alpha_beta`, `minmax`, and `canExclude` still exist later in `Shou.java`; do not revive them for new work.
- Existing harness tests immediate win/block, no mutation, determinism, strength mapping, edge candidate, black forbidden handling, white non-filtering, full board, and a mid-game benchmark.
