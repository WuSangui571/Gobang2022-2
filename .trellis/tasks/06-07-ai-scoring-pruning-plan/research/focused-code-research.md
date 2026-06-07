# Focused Code Research: AI Scoring and Pruning

## Relevant Specs Read

- `.trellis/spec/guides/index.md`
- `.trellis/spec/guides/gomoku-ai-thinking-guide.md`
- `.trellis/spec/guides/code-reuse-thinking-guide.md`
- `.trellis/spec/backend/index.md`
- `.trellis/spec/backend/algorithm-guidelines.md`
- `.trellis/spec/backend/quality-guidelines.md`
- `.trellis/spec/backend/logging-guidelines.md`
- `.trellis/spec/frontend/index.md`
- `.trellis/spec/frontend/hook-guidelines.md`
- `.trellis/spec/frontend/state-management.md`
- `.trellis/spec/frontend/quality-guidelines.md`

## Code Patterns Found

### Entry Path

- `AI.getLocation()` creates `Shou` and delegates to `Shou.getAnswer(Map, ROW, COL, direnType, AiType)`.
- `ChessController.AiPutChess()` calls `AI.getLocation()` and then `applyAiMove(row, col)`.
- `ChessController.applyAiMove()` already rejects out-of-board and occupied moves.
- `Static.aiStrength` feeds `AiSearchConfig`.

### Current Search Behavior

- `Shou.getAnswer()`:
  - deep-copies the input board with `myCopyArray`;
  - returns center for an empty board;
  - checks own immediate winning move with `findWinningMove`;
  - generates radius-2 candidates near existing stones;
  - sorts candidates with `sortCandidates`;
  - uses negamax with scalar alpha/beta and a time limit;
  - falls back to the first legal sorted candidate if no scored result is found.
- `negamax()`:
  - exits on time budget;
  - checks terminal five-in-a-row for opponent/current color;
  - evaluates with `getGrade(board, color) - getGrade(board, opponent)` at leaves;
  - regenerates and sorts candidates at each depth;
  - skips illegal black forbidden-hand moves through `isLegalCandidate`.

### Current Scoring / Ordering Weaknesses

- `getSortScore()` explicitly distinguishes only:
  - own immediate win: `WIN_SCORE`,
  - opponent immediate win block: `WIN_SCORE - 1`,
  - otherwise center distance plus `getGrade(board, aiColor) / 5`.
- `getGrade()` scans all five-cell windows and calls `getFiveGrade()`.
- `getFiveGrade()` scores by counts only:
  - own-only five-window: `10^countAi`,
  - enemy-only five-window: about `-1.2 * 10^countEnemy`,
  - mixed windows: `0`.
- This count-only five-window score does not directly classify open four, blocked/rush four, open three, jump three, blocked three, or double threats.
- Existing tactical helper methods exist in `Shou`:
  - `liveFour`,
  - `rushFour`,
  - `newLiveThree`,
  - `jumpLiveThree`,
  - `longSix`.
- These helpers are already used by forbidden-hand checks, so reusing them for threat classification may avoid duplicating pattern logic, but they are legacy-heavy and should be wrapped carefully.

### Legacy Code To Avoid

- `Shou.alpha_beta(...)` still exists near the end of the file and uses mutable `int[] alpha/beta`, fixed depth 4, and `canExclude`.
- `canExclude()` excludes all edges and catches exceptions for boundary scanning.
- `minmax(...)` and `f(...)` contain incomplete/old search code and debug print behavior.
- New implementation should not revive these paths unless deleting or isolating dead code is explicitly included later.

### Existing Harness

`AiSearchHarness` currently covers:

- immediate win,
- immediate block,
- no board mutation,
- deterministic repeated result,
- strength/depth/time mapping,
- edge candidate not fully excluded,
- black forbidden-hand filtering,
- white not filtered by black forbidden-hand rules,
- full board no move,
- benchmark elapsed time for LOW/MEDIUM/HIGH on one mid-game position.

Gaps for this task:

- No assertion for open-four / rush-four priority.
- No assertion for opponent forcing-four block before general attack.
- No assertion that obvious tactical moves complete quickly below LOW/MEDIUM budgets.
- No candidate count, node count, or candidate cap evidence.
- Benchmark prints elapsed time but not selected move reason, candidate count, or score.

## Files Likely To Modify

- `src/main/java/com/ztydwz/gobang2022/Service/Shou.java`
  - Add or refine tactical classification, candidate ordering, evaluator weights, and possibly candidate caps.
- `src/test/java/com/ztydwz/gobang2022/Service/AiSearchHarness.java`
  - Add position-first tests for scoring/pruning improvements and benchmark evidence.
- `src/main/java/com/ztydwz/gobang2022/Service/SearchResult.java`
  - Only if candidate count/node count is needed for benchmark reporting.
- `src/main/java/com/ztydwz/gobang2022/Service/AiSearchConfig.java`
  - Only if candidate caps, tactical thresholds, or time-budget behavior should be strength-specific.

## Risk / Boundary Notes

- Do not change `AI.getLocation()` return shape.
- Do not change board encoding.
- Do not mutate global `Static.Map` from pure search/evaluation code.
- Do not make normal AI choice random.
- Do not add console output inside tight search loops.
- Do not broaden into a full Renju forbidden-hand rewrite.
- Do not modify Swing controllers unless implementation discovers a narrow invalid-move or responsiveness integration defect.
- Do not introduce Maven/Gradle or package migration in this task.
- Preserve current modes: free start, designated start, two-player, and two-AI.

## Required Tests and Assertion Points

1. Compile all Java sources:
   - `javac -encoding UTF-8 -d .tmp\classes "@.tmp\sources.txt"`
2. Run harness:
   - `java -cp .tmp\classes com.ztydwz.gobang2022.Service.AiSearchHarness`
3. Add harness cases:
   - own open-four/forcing-four preferred over positional move;
   - opponent open-four/forcing-four block preferred over low-value own attack;
   - obvious tactical win/block returns quickly under LOW and MEDIUM;
   - candidate cap, if added, does not remove immediate win/block;
   - board remains unchanged after search.
4. Manual Swing smoke:
   - free-start human-vs-AI still responds;
   - designated-start flow reaches normal AI play;
   - no visible UI freeze for ordinary tactical positions.

## Suggested Implementation Sequence

1. Add failing or pending harness positions that reproduce the current weakness.
2. Add small named scoring constants and a threat classification helper in `Shou`.
3. Rework `getSortScore()` to use tactical class priority before center/grade.
4. Refine leaf evaluation to respect tactical classes and separated attack/defense weights.
5. Add candidate cap only after preserving immediate and forcing tactical points.
6. Re-run compile and harness after each meaningful step.
