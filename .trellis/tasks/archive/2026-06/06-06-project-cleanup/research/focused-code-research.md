# Focused Code Research

## Relevant Specs Read

- `.trellis/spec/guides/index.md`
- `.trellis/spec/guides/code-reuse-thinking-guide.md`
- `.trellis/spec/guides/cross-layer-thinking-guide.md`
- `.trellis/spec/guides/gomoku-ai-thinking-guide.md`
- `.trellis/spec/backend/index.md`
- `.trellis/spec/backend/directory-structure.md`
- `.trellis/spec/backend/algorithm-guidelines.md`
- `.trellis/spec/backend/database-guidelines.md`
- `.trellis/spec/backend/error-handling.md`
- `.trellis/spec/backend/quality-guidelines.md`
- `.trellis/spec/backend/logging-guidelines.md`
- `.trellis/spec/frontend/index.md`
- `.trellis/spec/frontend/directory-structure.md`
- `.trellis/spec/frontend/component-guidelines.md`
- `.trellis/spec/frontend/hook-guidelines.md`
- `.trellis/spec/frontend/state-management.md`
- `.trellis/spec/frontend/type-safety.md`
- `.trellis/spec/frontend/quality-guidelines.md`

## Trellis Context Notes

- `python ./.trellis/scripts/get_context.py` reported branch `main`, clean working directory, no current task, and one active bootstrap task.
- `.trellis/tasks/00-bootstrap-guidelines/prd.md` has backend/frontend guideline checklist items marked complete, but the task is still active and not archived.
- `.trellis/workspace/sangui/journal-1.md` contains only the initialized journal header; no prior substantive project status was recorded there.
- `task.py init-context` is not available in this Trellis version. It returns that `init-context` was removed in `v0.5.0-beta.12` and that `implement.jsonl` / `check.jsonl` are seeded by `task.py create`. The equivalent setup is to verify the seeded files and populate them via `task.py add-context`.

## Code Patterns Found

- Project is an IntelliJ-style Java Swing app, not a web app or server.
- Main entry is `src/main/java/com/ztydwz/gobang2022/Controller/Start.java`.
- Current source package prefix is `main.java.com.ztydwz.gobang2022`.
- `Gobang2022-2.iml` marks `src` as the source folder, matching the unusual package prefix.
- `src/main/java/com/ztydwz/gobang2022/Model/Static.java` holds global board and game state:
  - `Map` as `int[15][15]`
  - `pointers`
  - `chessList`
  - `gameFlag`
  - `winFlag`
  - player and AI color/mode settings
- UI is fixed-size Swing with absolute positioning.
- `GamePanel.paint(Graphics)` calls `repaint()` unconditionally after drawing, which matches a spec warning about constant repaint loops.
- `GamePanel.createListenMouseListener()` adds duplicate `MouseMotionListener` instances for two-player and two-AI modes.
- `GameDialog` calls `gamePanel.createListenMouseListener()` every confirmation, so repeated settings confirmation may add duplicate listeners.
- `GameButton` has a "game instructions" button with no action.
- `ChessController.restract()` reads `chessList.get(chessList.size() - 1)` without checking empty state.
- `Option.createInputFiveDaNumber()` directly parses `JOptionPane.showInputDialog` result with no null, numeric, or range validation.
- `ExportRecord` writes to hard-coded `C:\棋谱\...` paths and only prints stack traces on `IOException`.
- `ImageValue` loads image resources from package-relative paths under `src/main/java/.../image`.
- `Service/Shou.java` is the largest file by far and contains alpha-beta, scoring, many broad or empty `catch (Exception e)` blocks, and performance-sensitive debug output. It should not be cleaned broadly in this normalization task.
- `AI.fiveDa` and `AI.drFiveDa` use `Random`, which the spec allows only for explicitly marked demonstration modes. This is a future algorithm task, not a safe cleanup target.
- `out/production/Gobang2022-2/...` contains tracked `.class` files and copied resources.
- No Maven or Gradle files were found.
- No `src/test` tree or test framework was found.
- Root `.gitignore` was not found; `.idea/.gitignore` exists.
- README content appears as mojibake in the current PowerShell output, while `rg` can display some Java Chinese string literals correctly. README encoding/storage should be verified and repaired during implementation.

## Files Likely To Modify

- `.gitignore`
- `README.md`
- tracked `out/production/...` files, removed from Git
- `src/main/java/com/ztydwz/gobang2022/Model/GamePanel.java`
- `src/main/java/com/ztydwz/gobang2022/Model/GameButton.java`
- `src/main/java/com/ztydwz/gobang2022/View/Option.java`
- `src/main/java/com/ztydwz/gobang2022/Service/ExportRecord.java`
- `src/main/java/com/ztydwz/gobang2022/Controller/ChessController.java`
- Possibly `src/main/java/com/ztydwz/gobang2022/Model/ImageValue.java`
- Possibly `Gobang2022-2.iml` only for minimal metadata alignment

## Risk / Boundary Notes

- Removing generated output is safe only if compile/run instructions and resource copying/classpath behavior are verified.
- Package migration from `main.java.com...` to `com...` is a high-churn refactor and should stay out of this task.
- `Service/Shou.java` contains many issues, but broad cleanup there risks changing AI behavior and should be deferred.
- `GamePanel.paint()` repaint-loop fix is low risk but still needs UI smoke because rendering behavior is visible.
- Listener cleanup must preserve all four modes:
  - free start
  - designated start
  - two-player battle
  - two-AI battle
- Export path cleanup affects local filesystem behavior; assert directory creation or user-facing errors.
- README repair must preserve the historical project story where practical.
- Trellis and agent workflow files are not "useless content"; do not delete them as cleanup.

## Required Tests

Command-line:

```powershell
git status --short
git ls-files out
New-Item -ItemType Directory -Force -Path .tmp\classes | Out-Null
Get-ChildItem -Recurse -Path src -Filter *.java | ForEach-Object { $_.FullName } | Set-Content -Encoding UTF8 .tmp\sources.txt
javac -encoding UTF-8 -d .tmp\classes @.tmp\sources.txt
java -cp .tmp\classes main.java.com.ztydwz.gobang2022.Controller.Start
```

Manual smoke:

- Launch the app.
- Open settings.
- Select each mode touched by changes.
- Start a game.
- Make legal and illegal clicks.
- Use undo before any move and after a move.
- Check stone/pointer images render.
- If export changes, verify successful export or a clear user-facing failure.

Optional future tests:

- Add a small harness for `JudgeIfWin` or future pure board helpers.
- Add deterministic positions before changing AI or forbidden-hand logic.
