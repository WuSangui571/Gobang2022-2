# Focused Code Research: Alpha-Beta AI Improvement

## Current Project State

- Current branch: `main`.
- Working tree was clean before task setup.
- Previous journal entry says the project-cleanup task was completed and archived at `.trellis/tasks/archive/2026-06/06-06-project-cleanup`.
- `00-bootstrap-guidelines` remains `in_progress`, but it is a bootstrap/spec task and not the active session task for this feature.
- This project has no Maven or Gradle build file and no automated test framework. README documents manual `javac` compile and Swing launch commands.

## Relevant Specs Read

- `.trellis/spec/guides/index.md`
- `.trellis/spec/guides/gomoku-ai-thinking-guide.md`
- `.trellis/spec/guides/cross-layer-thinking-guide.md`
- `.trellis/spec/guides/code-reuse-thinking-guide.md`
- `.trellis/spec/backend/index.md`
- `.trellis/spec/backend/algorithm-guidelines.md`
- `.trellis/spec/backend/quality-guidelines.md`
- `.trellis/spec/backend/error-handling.md`
- `.trellis/spec/frontend/index.md`
- `.trellis/spec/frontend/hook-guidelines.md`
- `.trellis/spec/frontend/state-management.md`
- `.trellis/spec/frontend/component-guidelines.md`
- `.trellis/spec/frontend/quality-guidelines.md`
- `.trellis/spec/frontend/type-safety.md`

## Code Patterns Found

### AI Entry Path

- `src/main/java/com/ztydwz/gobang2022/Controller/ChessController.java`
  - `AiPutChess()` sets `AI` color, calls `AI.getLocation()`, then applies the selected board point to `pointers`, `Static.Map`, and `chessList`.
- `src/main/java/com/ztydwz/gobang2022/Service/AI.java`
  - `getLocation()` delegates to `new Shou().getAnswer(Map, ROW, COL, direnType, AiType)`.
  - `setAiType(int)` stores static `AiType` / `direnType`.
  - `fiveDa()` and `drFiveDa()` still use `Random`; spec allows this only for demonstration/placeholder five-N-da behavior, not normal move selection.
- `src/main/java/com/ztydwz/gobang2022/Service/Shou.java`
  - Has `int deep = 4`, but current `getAnswer()` hardwires `alpha_beta(0, map, aiColor, alpha, beta)`.
  - Existing `alpha_beta()` stops at `depth == 4`, uses mutable `int[] alpha/beta`, alternates color by `3 - aiColor`, and returns an array `[score,row,col]`.
  - Existing candidate pruning uses `canExclude()`, which excludes all board edges and only checks distance 1 neighbors. This violates the spec's "do not exclude all edge moves blindly" guidance.
  - Existing alpha-beta lacks clear terminal-state checks before depth exhaustion.
  - Existing scoring is five-tuple based and does not clearly distinguish open four, blocked four, open three, jump three, and other tactical patterns.
  - Several forbidden-hand helpers use broad `catch (Exception e) {}` for boundary control. Refactoring all of that is likely out of scope, but new search/evaluation code should not copy that pattern.

### UI / Turn Flow Boundary

- `FreeStartController.mouseMoved()` triggers AI when `putChess == aiPutChess`.
- `DesignatedStartController.mouseMoved()` triggers AI after pointer update and five-N-da checks.
- `TwoAiBattleController.mouseClicked()` advances the two-AI game one move per click, while `mouseMoved()` does not trigger search.
- Because deeper alpha-beta can take longer, the current `mouseMoved()` AI trigger is risky: it can repeat expensive work and freeze the Swing Event Dispatch Thread.
- `GameDialog` currently supports player order, game mode, forbidden-hand detection, and "allow forbidden hand". It does not expose AI strength/depth.
- `Static` currently stores global game settings and state, including `Map`, `aiType`, `playerType`, `ifForbiddenHandOpen`, and `ifAllowForbiddenHandOpen`. Adding a narrow AI strength setting here matches current integration style, but broader engine state should not be added casually.

## Files Likely To Modify

Expected implementation files:

- `src/main/java/com/ztydwz/gobang2022/Service/Shou.java`
- `src/main/java/com/ztydwz/gobang2022/Service/AI.java`
- `src/main/java/com/ztydwz/gobang2022/Model/Static.java`
- `src/main/java/com/ztydwz/gobang2022/Model/GameDialog.java`
- `src/main/java/com/ztydwz/gobang2022/Controller/ChessController.java`
- `src/main/java/com/ztydwz/gobang2022/Controller/FreeStartController.java`
- `src/main/java/com/ztydwz/gobang2022/Controller/DesignatedStartController.java`
- `src/main/java/com/ztydwz/gobang2022/Controller/TwoAiBattleController.java` if two-AI mode needs the same search/apply path or progress guard.

Optional/new files if needed:

- `src/main/java/com/ztydwz/gobang2022/Service/SearchResult.java`
- `src/main/java/com/ztydwz/gobang2022/Service/AiSearchConfig.java`
- `src/main/java/com/ztydwz/gobang2022/Service/MoveCandidate.java`
- `src/test/java/com/ztydwz/gobang2022/Service/AiSearchHarness.java` or another simple deterministic harness if no test framework is introduced.

## Risk / Boundary Notes

- Do not rewrite the whole Renju forbidden-hand engine in this task unless necessary for filtering candidate moves. Existing behavior is incomplete and risky.
- Do not change board encoding: `0` empty, `1` black, `2` white.
- Do not move package roots or rename `main.java.com.ztydwz.gobang2022`.
- Do not make normal AI move selection random.
- Do not rely on console output as correctness evidence.
- Do not trigger long AI search from `mouseMoved()` without a guard/background mechanism.
- Do not mutate `Static.Map` inside search simulation. Search must use copied board rows or guaranteed apply/undo rollback.
- Preserve supported modes: free start, designated start, two-player, and two-AI.
- Preserve five-N-da and three-hand exchange flows unless a narrow integration change is required to keep turn flow correct.

## Suggested Technical Direction

- Replace the current experimental `alpha_beta()` with deterministic negamax or explicit max/min alpha-beta.
- Keep the public entry surface compatible with `AI.getLocation()` initially, but route it through a configurable search depth/strength.
- Add an AI strength setting with Low/Medium/High levels. Recommended mapping:
  - Low: depth 2, short budget.
  - Medium: depth 4, default.
  - High: depth 6 or iterative deepening up to a 90-120 second cap.
- Use adaptive early-game behavior: before enough stones exist, search at a reduced effective depth and prefer center/local opening candidates.
- Generate candidates near existing stones within radius 1 or 2; do not search all 225 points after stones exist.
- Always include immediate win and immediate block candidates.
- Sort candidates by tactical priority before recursion.
- Add a time budget or iterative-deepening stop check so High does not exceed the 1-2 minute target.
- If search can exceed a few hundred milliseconds, move it off the Swing Event Dispatch Thread with `SwingWorker` or a bounded background thread and guard against duplicate AI moves.

## Required Tests And Evidence

Automated or reproducible harness checks:

- AI chooses an immediate winning move.
- AI blocks an opponent immediate winning move.
- AI search does not mutate the input board.
- Result is deterministic for equal-score candidates.
- Depth/strength configuration changes the effective depth used by the search.
- Candidate generation includes legal edge moves when they are tactically relevant.
- Forbidden-hand filtering respects `ifAllowForbiddenHandOpen` for black, without applying black-only rules to white.
- A mid-game position produces a stable selected move with recorded depth, elapsed time, candidate count, and score.

Build/manual checks:

```powershell
New-Item -ItemType Directory -Force -Path .tmp\classes | Out-Null
Get-ChildItem -Recurse -Path src -Filter *.java | ForEach-Object { $_.FullName } | Set-Content -Encoding ASCII .tmp\sources.txt
javac -encoding UTF-8 -d .tmp\classes "@.tmp\sources.txt"
Copy-Item -Recurse -Force src\main\java\com\ztydwz\gobang2022\image .tmp\classes\main\java\com\ztydwz\gobang2022\
Copy-Item -Recurse -Force src\main\java\com\ztydwz\gobang2022\config .tmp\classes\main\java\com\ztydwz\gobang2022\
```

Manual Swing smoke:

```powershell
java -cp .tmp\classes main.java.com.ztydwz.gobang2022.Controller.Start
```

Manual acceptance should cover:

- Open settings and choose Low/Medium/High AI strength.
- Start free-start human-vs-AI mode and verify the AI responds once per turn.
- Start designated-start mode and verify three-hand exchange/five-N-da flow still reaches normal AI play.
- Start two-AI mode and verify it progresses without duplicate moves.
- Try undo after AI moves and confirm `Map`, `pointers`, and `chessList` remain synchronized.
- Confirm the UI does not lock indefinitely; High may think longer but must stay within the documented 1-2 minute cap.
