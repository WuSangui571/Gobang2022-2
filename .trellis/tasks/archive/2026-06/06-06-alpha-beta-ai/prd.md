# Improve AI with Configurable Alpha-Beta Search

## Goal

Improve the Gobang2022 AI from the current experimental fixed-depth search into a deterministic alpha-beta based AI that can think deeper, respect game constraints, and remain usable in the Swing desktop game.

The user wants stronger AI thinking with a slightly longer acceptable thinking time, ideally configurable by depth or by strength levels such as Low / Medium / High. High strength may take longer, but a single AI move should stay within roughly 1-2 minutes. Early-game moves should not over-search unnecessarily.

## Scope Classification

**Complex Task**

Reasons:

- Touches the AI/search engine, board evaluation, candidate generation, global settings, Swing settings UI, and turn-flow controllers.
- Requires deterministic algorithm evidence, performance boundaries, and manual Swing acceptance.
- Existing AI already has an `alpha_beta`-shaped method, but it is fixed-depth and not spec-compliant enough to treat this as a narrow replacement.

## Current Project State

- Branch at planning time: `main`.
- Working tree was clean before task setup.
- Previous journal entry says the project cleanup task was completed and archived.
- `00-bootstrap-guidelines` remains active as a Trellis bootstrap/spec task and should not be reused for this feature.
- This task directory is `.trellis/tasks/06-06-alpha-beta-ai`.
- This planning pass must not modify business implementation files. DeepSeek will implement later on a new feature branch.

## Requirements

1. Replace or substantially refactor the current AI move search so normal AI move selection is based on deterministic alpha-beta pruning.
2. Prefer negamax with alpha-beta pruning. If keeping explicit max/min, make side-to-move and maximizing/minimizing roles clear.
3. Make AI thinking strength configurable:
   - Recommended UI: Low / Medium / High in the existing settings dialog.
   - Recommended depth mapping: Low = 2, Medium = 4, High = 6 or iterative deepening to the time cap.
   - The implementation may expose exact depth internally, but the user-facing setting should be simple.
4. Keep a time boundary:
   - Low/Medium should feel responsive.
   - High may think longer but must target a maximum of 1-2 minutes per move.
   - If exact time control is implemented, stop cleanly and return the best completed-depth result.
5. Optimize early moves:
   - Before enough stones exist, use reduced effective depth and center/local opening preference.
   - Avoid wasting deep search on empty-board or near-empty-board states.
6. Improve candidate generation:
   - Do not search all 225 points once stones exist.
   - Generate candidates near existing stones, normally radius 1 or 2.
   - Do not blindly exclude all edge moves.
   - Always include tactical immediate win and immediate block points.
   - Sort candidates before recursion using tactical priority.
7. Improve terminal and tactical handling:
   - Detect own immediate win before general search if practical.
   - Detect opponent immediate win and block before low-value attacks.
   - Evaluate terminal states before depth exhaustion.
   - Treat full-board/draw states explicitly if encountered.
8. Preserve forbidden-hand behavior:
   - Black-only forbidden-hand rules must not be applied to white.
   - If forbidden hands are disabled, AI may consider otherwise forbidden black moves.
   - If forbidden hands are enabled and forbidden moves are disallowed, candidate generation must filter illegal black moves before search.
9. Search simulation must not mutate global `Static.Map`.
   - Use copied board rows or explicit apply/undo with guaranteed rollback.
10. Preserve existing playable modes:
   - Human-vs-AI free start.
   - Human-vs-AI designated start.
   - Two-player battle.
   - Two-AI battle.
   - Existing three-hand exchange and five-N-da flows.
11. Do not freeze or repeatedly trigger expensive AI search from Swing mouse movement.
   - If a move can exceed a short UI threshold, use a background mechanism or guard so only one AI move is in progress.
12. Add deterministic test coverage or a reproducible local harness for AI positions and performance evidence.

## User-Facing Contract

### UI Command / Payload Fields

There is no HTTP API. The relevant user-facing command is the existing Swing settings dialog opened by the "game settings" button.

Expected new setting:

| Field | Type | Values | Default | Meaning |
|---|---|---|---|---|
| AI strength | Radio/Combo selection | Low / Medium / High | Medium | Controls AI search depth or iterative deepening budget |

Recommended internal state:

| Field | Type | Values | Purpose |
|---|---|---|---|
| `aiSearchStrength` or equivalent | enum/int | LOW/MEDIUM/HIGH or 2/4/6 | Current global AI strength setting |
| `maxSearchDepth` or equivalent | int | normally 2/4/6 | Effective search depth |
| `searchTimeLimitMillis` or equivalent | long/int | e.g. Low short, Medium moderate, High <= 120000 | Per-move time cap |

The implementation may avoid adding all three fields if a smaller model is cleaner, but it must be explicit and testable.

### Core Command Contract

Expected AI move entry remains conceptually:

```java
int[] move = new AI().getLocation();
```

Expected returned payload:

```text
int[0] = row
int[1] = col
```

Recommended internal result object:

```java
SearchResult(score, row, col, depth, elapsedMillis)
```

The public controller-facing return may remain `int[]` for scope control, but internal search should use a structured result or an equivalent clear type.

## Validation / Error Matrix

| Case | Validation | Expected Behavior | Assertion Point |
|---|---|---|---|
| Empty board or near-empty board | Stone count is low | Choose deterministic center/local opening candidate with reduced depth | Returned move is legal and deterministic |
| AI has immediate five | Tactical pre-check or terminal search | Choose winning move | Expected row/col in harness |
| Opponent has immediate five | Tactical pre-check or search ordering | Block opponent win unless AI has own immediate win | Expected block row/col in harness |
| Candidate is occupied | Candidate generation / move legality | Never return occupied point | Harness checks board[row][col] was empty |
| Edge point is tactical | Candidate generation | Do not exclude solely because it is on edge | Edge tactical test includes candidate |
| Black forbidden move, disallowed | Forbidden-hand filter | Do not choose illegal black move | Position harness or documented manual case |
| White move with forbidden pattern | Color legality | Do not apply black-only forbidden logic to white | Position harness |
| Search hits time cap | Iterative deepening/time guard | Return best completed result, not invalid `[-1,-1]` | Benchmark output |
| No legal moves/full board | Board scan | Return a safe no-move result or prevent AI call after draw | Harness or manual note |
| Swing AI move in progress | UI controller guard/background task | Prevent duplicate AI moves and keep board state synchronized | Manual smoke |
| Settings missing selection | UI default | Medium is used | Manual settings smoke |

## Good / Base / Bad Cases

### Good Cases

- AI finds a one-move win at configured strength.
- AI blocks an opponent one-move win.
- High strength searches deeper than Medium on a mid-game position and reports elapsed time within the 1-2 minute cap.
- Equal-score candidates produce the same selected move on repeated runs.
- Undo after an AI move keeps `Static.Map`, `pointers`, and `chessList` synchronized.
- Two-AI mode can progress without duplicate or overlapping AI moves.

### Base Cases

- Medium strength uses depth 4 or equivalent and remains playable.
- Low strength uses shallow search and returns quickly.
- Early-game AI does not waste high-depth search on an empty or sparse board.
- Existing settings choices for game mode, first player, forbidden-hand detection, and allow-forbidden-hand continue to work.

### Bad Cases

- AI returns an occupied point.
- AI misses a direct win or direct block due to heuristic scoring.
- Search mutates `Static.Map` during simulation.
- Search uses `Random` for normal move choice.
- High strength freezes the Swing UI indefinitely.
- `mouseMoved` repeatedly starts expensive AI searches.
- Candidate generation excludes a legal winning edge move.
- Black forbidden-hand filtering applies to white.

## Acceptance Criteria

- [ ] Normal AI move selection uses alpha-beta pruning or negamax alpha-beta, not random selection or only single-layer score selection.
- [ ] AI strength/depth is configurable from the settings UI or an equivalent user-accessible setting.
- [ ] Default strength is documented and safe for normal play.
- [ ] High strength has a clear per-move time cap or iterative-deepening stop behavior targeting 1-2 minutes maximum.
- [ ] Early-game effective depth is reduced or otherwise optimized.
- [ ] Candidate generation is neighborhood-pruned, deterministic, sorted, and does not blindly exclude all edges.
- [ ] Tactical immediate win and immediate block behavior is covered by harness/tests.
- [ ] Search does not mutate the input board.
- [ ] Forbidden-hand settings are respected for black only.
- [ ] Human-vs-AI free start, designated start, two-player, and two-AI modes still start and play.
- [ ] Deep search does not repeatedly fire from `mouseMoved` or duplicate AI moves.
- [ ] Build command succeeds.
- [ ] Manual Swing smoke test is recorded after implementation.

## Required Tests And Assertion Points

Because the project has no test framework, DeepSeek should either introduce a small focused Java harness or add a minimal test setup if that is practical without broad build-system work.

Required automated or reproducible position checks:

1. Immediate win:
   - Board with AI four-in-a-row and one open winning point.
   - Assert selected row/col is the winning point.
2. Immediate block:
   - Board with opponent four-in-a-row and one open winning point.
   - Assert selected row/col blocks it.
3. No mutation:
   - Copy a mid-game board, call search, assert every board cell remains unchanged.
4. Determinism:
   - Run the same equal-score or symmetrical position multiple times, assert the same row/col.
5. Strength/depth:
   - Assert Low/Medium/High map to expected effective depth or budget.
6. Edge candidate:
   - Construct a tactical edge move and assert it can be chosen/included.
7. Forbidden hand:
   - If touching forbidden-hand filtering, cover black disallowed move and white non-filtered move.
8. Benchmark evidence:
   - Record candidate count, configured strength/depth, elapsed time, selected move, and score for at least one mid-game board.

Required compile command:

```powershell
New-Item -ItemType Directory -Force -Path .tmp\classes | Out-Null
Get-ChildItem -Recurse -Path src -Filter *.java | ForEach-Object { $_.FullName } | Set-Content -Encoding ASCII .tmp\sources.txt
javac -encoding UTF-8 -d .tmp\classes "@.tmp\sources.txt"
Copy-Item -Recurse -Force src\main\java\com\ztydwz\gobang2022\image .tmp\classes\main\java\com\ztydwz\gobang2022\
Copy-Item -Recurse -Force src\main\java\com\ztydwz\gobang2022\config .tmp\classes\main\java\com\ztydwz\gobang2022\
```

Manual Swing smoke command:

```powershell
java -cp .tmp\classes main.java.com.ztydwz.gobang2022.Controller.Start
```

Manual assertions:

- Settings dialog exposes AI strength and keeps existing settings functional.
- Low/Medium/High can each be selected and used in a game.
- Free-start human-vs-AI mode: player move triggers exactly one AI response.
- Designated-start mode: exchange/five-N-da flow still works until normal AI play.
- Two-player mode is unaffected by AI changes.
- Two-AI mode can progress without duplicate moves.
- Undo after AI moves remains safe.
- High strength does not exceed the documented per-move cap in a representative mid-game position.

## Technical Approach

Recommended implementation sequence:

1. Preserve the current `AI.getLocation()` controller-facing entry and add explicit AI search configuration.
2. Introduce a small internal result/config model if useful, e.g. `SearchResult`, `MoveCandidate`, `AiSearchConfig`.
3. Implement deterministic candidate generation:
   - Empty board: center or deterministic center-near move.
   - Non-empty board: empty points within radius 1-2 of existing stones.
   - Add tactical immediate win/block points.
   - Sort using tactical score, center distance, and row/col tie-breaker.
4. Implement alpha-beta/negamax:
   - Use scalar alpha/beta parameters.
   - Apply moves on a copied board or with guaranteed undo.
   - Evaluate terminal states before heuristic depth stop.
   - Respect time cap if iterative deepening/time budget is included.
5. Improve evaluation enough for tactical strength:
   - Own/opponent five.
   - Open four and blocked/rush four.
   - Open three/jump three.
   - Open/blocked two and center/local density as lower weights.
6. Add AI strength UI:
   - Add Low/Medium/High controls in `GameDialog`.
   - Store selected strength in existing global state or a narrow config class compatible with current code.
7. Fix AI trigger boundary if needed:
   - Prevent long search from firing repeatedly from `mouseMoved()`.
   - Prefer a single guarded AI action per turn, optionally with background search if High can be slow.
8. Add deterministic harness/tests and benchmark note.

## Decision (ADR-lite)

**Context**: The existing code already has `Shou.alpha_beta`, but it is fixed at depth 4, uses mutable alpha/beta arrays, lacks clear terminal checks, and prunes candidates too aggressively by excluding all edges. Deeper search also risks freezing the Swing UI because human-vs-AI modes trigger AI from `mouseMoved()`.

**Decision**: Implement a deterministic alpha-beta/negamax search with configurable strength, sorted candidate generation, tactical pre-checks, and explicit performance guard. Preserve the public `AI.getLocation()` return shape to keep controller integration narrow.

**Consequences**:

- This avoids a full package/engine rewrite while still giving DeepSeek a clear path to improve strength.
- UI integration must be touched because user-facing strength settings and long-search behavior cross the Swing boundary.
- A future task can further extract a pure game engine and replace legacy forbidden-hand helpers; this task should not attempt that full cleanup.

## Out Of Scope

- No package renaming or source-root migration.
- No Maven/Gradle migration unless DeepSeek determines a tiny harness is impossible otherwise; even then, ask before broad build-system changes.
- No full rewrite of Renju forbidden-hand detection.
- No database, network, API, or storage changes.
- No visual redesign of the board.
- No change to board encoding (`0` empty, `1` black, `2` white).
- No replacement of five-N-da / three-hand exchange rules beyond compatibility fixes.
- No auto-commit or branch creation by Codex in this planning pass.

## Files Likely To Modify

Likely:

- `src/main/java/com/ztydwz/gobang2022/Service/Shou.java`
- `src/main/java/com/ztydwz/gobang2022/Service/AI.java`
- `src/main/java/com/ztydwz/gobang2022/Model/Static.java`
- `src/main/java/com/ztydwz/gobang2022/Model/GameDialog.java`
- `src/main/java/com/ztydwz/gobang2022/Controller/ChessController.java`
- `src/main/java/com/ztydwz/gobang2022/Controller/FreeStartController.java`
- `src/main/java/com/ztydwz/gobang2022/Controller/DesignatedStartController.java`
- `src/main/java/com/ztydwz/gobang2022/Controller/TwoAiBattleController.java`

Optional new implementation/harness files:

- `src/main/java/com/ztydwz/gobang2022/Service/SearchResult.java`
- `src/main/java/com/ztydwz/gobang2022/Service/MoveCandidate.java`
- `src/main/java/com/ztydwz/gobang2022/Service/AiSearchConfig.java`
- `src/test/java/...` or a simple local Java harness under a clearly named test/harness path.

## Technical Notes

- Research details are in `research/focused-code-research.md`.
- The README compile path is the current authoritative build route.
- Existing README known limitations already include weak AI, incomplete forbidden-hand logic, no automated tests, and no Maven/Gradle build.
- `task.py init-context` was requested by the user, but this Trellis version removed it. `task.py create` already seeded context files; use `task.py add-context` for curation.

## Planning Self-Check

- Acceptance criteria defined: yes.
- Forbidden modification scope defined: yes.
- Expected files listed: yes.
- Required tests listed: yes.
- Concrete guideline files read, not just indexes: yes.
- Need user confirmation before implementation: no blocking product question remains for planning; implementation may proceed with the recommended Low/Medium/High mapping unless the user changes it.
- API / DB / frontend types / DTO alignment: no API/DB/DTO. Swing settings and global config fields are the relevant contract.
