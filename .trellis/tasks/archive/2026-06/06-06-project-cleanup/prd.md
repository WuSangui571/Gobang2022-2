# Project Cleanup and Modernization Planning

## Goal

Normalize this four-year-old Java Swing Gomoku/Renju project so future work can safely revive it without mixing repository hygiene, build setup, UI fixes, and AI algorithm rewrites into one uncontrolled change. This task prepares a conservative implementation plan for DeepSeek: clean generated/IDE noise, document a repeatable build and verification path, preserve playable behavior, and identify obvious low-risk cleanup targets.

## Task Scope Classification

Complex Task.

Reasons:

- The request is broad: "organize the project", "normalize", "remove useless content", and "give it a second spring".
- It spans repository hygiene, Java project structure, Swing UI behavior, game-core state, resources, README documentation, and verification commands.
- The repo currently has no Maven or Gradle build file, no automated tests, and tracked compiled output under `out/production`.
- Several obvious cleanup targets have behavior risk, so they need staged boundaries rather than a single large rewrite.

## Current Project State

- Branch at planning time: `main`.
- Working tree before creating this task: clean.
- Trellis current task: none bound to this session.
- Existing active Trellis task: `.trellis/tasks/00-bootstrap-guidelines/`, status `in_progress`, checklist complete but not archived.
- Workspace journal: `.trellis/workspace/sangui/journal-1.md` exists but contains only the initialized journal header; no previous substantive session summary was recorded there.
- Project type: IntelliJ-style Java Swing desktop project.
- Entry point: `src/main/java/com/ztydwz/gobang2022/Controller/Start.java`.
- Source packages currently use `package main.java.com.ztydwz.gobang2022...`; the `.iml` marks `src` as source root.
- No Maven/Gradle build file found.
- No automated test framework found.
- `out/production/...` compiled `.class` files and copied resources are tracked by Git.

## Requirements

1. Repository hygiene
   - Add or update repository ignore rules so generated build output is not tracked going forward.
   - Remove tracked compiled output from the repository index as part of the cleanup branch, while preserving source files and assets.
   - Keep `.trellis/`, `.agents/`, `.codex/`, and `AGENTS.md` because they are project workflow infrastructure, not useless project junk.
   - Do not delete source code, image assets, README, license, or IntelliJ project files without a clear reason.

2. Build and run normalization
   - Provide a repeatable local compile command or lightweight build script/documentation that works without relying on existing `out/production` artifacts.
   - Preserve `Start.main` as the user-facing entry path unless a later task explicitly migrates the build/package layout.
   - Keep Java 8+ compatibility unless implementation proves a higher baseline is already required.

3. Documentation cleanup
   - Refresh README enough that a new maintainer can understand the project, run it, and know the current limitations.
   - Fix obvious encoding/mojibake in docs if the file is actually stored with broken text, but do not rewrite the project story into a marketing page.
   - Document that the project is a Swing Gomoku/Renju prototype with multiple modes and known technical debt.

4. Low-risk code hygiene only
   - Fix narrow, high-confidence cleanup issues that are already called out by spec and code research, such as:
     - `GamePanel.paint()` calling `repaint()` unconditionally.
     - duplicate `addMouseMotionListener` calls for some game modes.
     - undo on empty `chessList` causing an index error.
     - input parsing in `Option.createInputFiveDaNumber()` lacking cancel/non-numeric/range handling.
     - `ExportRecord` hard-coding `C:\棋谱\...` without directory creation or clear error handling.
     - dead "game instructions" button with no behavior, either implement a simple dialog or remove/disable with clear scope.
   - Keep fixes behavior-preserving where possible and avoid broad algorithm restructuring.

5. Verification baseline
   - At minimum, the implementation must compile from clean source after generated output is removed.
   - Manual Swing smoke testing must verify startup, settings dialog, each touched game mode, legal/illegal clicks, undo, and export if export changes.
   - If adding automated tests is feasible in this cleanup branch, prefer a small Java harness or lightweight test setup for pure board/rule behavior before any deep AI work. Do not block repository hygiene on full algorithm test coverage.

## Acceptance Criteria

- [ ] `git status` after implementation shows only intentional cleanup changes.
- [ ] Generated output under `out/` is ignored and not required for source compilation.
- [ ] A clean checkout can compile the Java sources with the documented command.
- [ ] README documents project purpose, run/compile instructions, Java requirement, known limitations, and development workflow.
- [ ] Source/image assets required by the Swing UI remain present and loadable.
- [ ] Startup path remains `Start.main`.
- [ ] The game window still opens from the documented command or IDE entry point.
- [ ] Settings dialog can still select free start, designated start, two-player battle, and two-AI battle modes if those paths are touched.
- [ ] Undo no longer crashes when invoked with no moves if this low-risk fix is included.
- [ ] Export no longer silently fails only because the target directory is absent if export handling is included.
- [ ] No AI strength, Renju rule semantics, board encoding, or search-depth behavior is intentionally changed in this task.

## API / Command / Payload Fields

There is no network API, database payload, DTO, or frontend type contract.

Command-level contract for this task:

- Compile command must be documented in README or a small script.
- Expected entry class: `main.java.com.ztydwz.gobang2022.Controller.Start`.
- Expected source root for current package layout: `src`.
- Expected generated output: an ignored directory such as `out/`, `build/`, or another documented local build folder.
- Expected resources: `src/main/java/com/ztydwz/gobang2022/image/BlackChess.png`, `WhiteChess.png`, and `pointer.gif` must remain available on runtime classpath.

## Validation / Error Matrix

| Area | Good Case | Base Case | Bad Case | Required Assertion |
|---|---|---|---|---|
| Repository hygiene | Clean source compiles without tracked `.class` files | Existing IntelliJ project still imports | `out/production` deletion removes needed source/resource | `git ls-files out` empty after cleanup; compile still succeeds |
| Ignore rules | New compile outputs remain untracked | Existing workflow files remain tracked | `.trellis/` or source files accidentally ignored | `git status --ignored` spot-check and `git check-ignore` for expected paths |
| Compile | `javac` or documented build path succeeds | Warnings are acceptable if inherited | Compile fails due package/source-root mismatch | Run documented compile command from repo root |
| Runtime startup | `Start.main` opens Swing UI | Console debug output may still exist | UI crashes before window opens | Manual launch smoke |
| Image assets | Stones and pointer render | Existing image path layout retained | images fail to load after output/resource cleanup | Manual UI smoke or classpath resource check |
| Settings dialog | Selecting modes still attaches expected controller path | Existing fixed layout retained | repeated settings creates duplicate listener effects | Manual mode smoke; inspect listener changes if touched |
| Undo | Undo after moves updates board/pointers/list | Undo button remains visible | Undo on empty list throws | Manual empty-undo smoke; optional focused test/harness |
| Export | Export creates directory or reports failure | Existing record format preserved | hard-coded missing path causes stack trace only | Manual export smoke; inspect output/error path |
| README encoding | README renders readable Chinese/English | Some historic terminology preserved | mojibake remains or history is lost | Open README as UTF-8 after edit |

## Good / Base / Bad Cases

Good cases:

- Fresh checkout on `main` plus cleanup branch can compile without relying on tracked `out/production`.
- A developer can run the app from IntelliJ or the documented command.
- README explains the project in readable text and names the current entry point.
- Low-risk UI hygiene fixes reduce obvious crashes without changing game rules.

Base cases:

- Existing IntelliJ `.iml` and `.idea` files may remain tracked if the implementation chooses minimal disruption.
- No Maven/Gradle migration is required in this task.
- Existing package name `main.java.com...` may remain unchanged to avoid a repository-wide migration.
- AI search and Renju rule behavior may remain technically weak; this task only prepares the project for later work.

Bad cases:

- Removing `out/production` breaks runtime because resources are no longer copied or documented.
- Renaming packages or moving source roots causes widespread import churn without tests.
- Cleanup silently changes AI move selection, forbidden-hand behavior, or mode flow.
- README is rewritten but compile/run instructions are still absent.
- Broad deletion removes Trellis workflow files or project assets.

## Technical Approach

Recommended implementation direction: staged conservative cleanup.

1. Repository hygiene first:
   - Add root `.gitignore`.
   - Remove tracked generated `out/production` files from version control.
   - Keep workflow and source files.

2. Compile/run baseline second:
   - Add README compile/run command for the current source-root/package layout.
   - If adding a build script, keep it small and transparent; avoid a full Maven/Gradle migration unless DeepSeek judges it necessary and still preserves behavior.

3. Documentation third:
   - Repair README readability and document known limitations.

4. Low-risk behavior cleanup last:
   - Only touch the small issues listed in this PRD.
   - Avoid editing `Service/Shou.java` unless the change is purely mechanical and directly tied to compile or hygiene; algorithm cleanup deserves its own future task.

## Decision (ADR-lite)

Context: The project needs renewal, but it has no automated safety net and contains intertwined Swing UI, global state, rule logic, and AI search.

Decision: Treat this first cleanup task as repository normalization plus narrowly scoped hygiene. Do not perform a package migration, AI rewrite, rule redesign, or UI redesign in the same branch.

Consequences: This leaves many known weaknesses in place, especially in `Shou.java`, forbidden-hand logic, and global state. That is intentional. The value of this task is to make future branches safer by removing generated artifacts, documenting a build path, and fixing only obvious low-risk issues.

## Out of Scope

- Full Maven/Gradle migration unless explicitly chosen as a small build-normalization step with compile proof.
- Package rename from `main.java.com...` to `com...`.
- AI strength improvement, move ordering redesign, alpha-beta rewrite, or benchmark suite.
- Renju forbidden-hand correctness overhaul.
- Swing UI redesign or layout modernization beyond narrow bug fixes.
- Replacing global `Static` state with a full engine model.
- Changing board encoding values `0`, `1`, `2`.
- Deleting Trellis, Codex, Kiro, or agent workflow infrastructure.

## Files Likely To Modify

- `.gitignore` (likely new root file)
- `README.md`
- `Gobang2022-2.iml` only if source/output metadata needs minimal correction
- tracked files under `out/production/...` removal from Git
- `src/main/java/com/ztydwz/gobang2022/Model/GamePanel.java`
- `src/main/java/com/ztydwz/gobang2022/Model/GameButton.java`
- `src/main/java/com/ztydwz/gobang2022/View/Option.java`
- `src/main/java/com/ztydwz/gobang2022/Service/ExportRecord.java`
- `src/main/java/com/ztydwz/gobang2022/Controller/ChessController.java`
- Possibly `src/main/java/com/ztydwz/gobang2022/Model/ImageValue.java` if resource-loading verification shows cleanup breaks image loading

## Required Tests And Assertion Points

Automated or command-line:

- Compile all Java sources from repo root after generated output removal.
- Verify generated output is ignored and no `.class` files are tracked under `out/`.
- Verify README instructions match actual command behavior.

Manual Swing smoke:

- Launch `Start.main`.
- Confirm game window opens.
- Open settings dialog.
- Select each supported mode touched by changes.
- Start game.
- Make a legal move.
- Make an illegal/outside-board click.
- Use undo before any move and after at least one move.
- If export changes, reach or simulate a game-end state and trigger export; verify directory/output or user-facing failure.
- Confirm image assets render.

Suggested PowerShell 5.1 compile baseline for DeepSeek to validate/adapt:

```powershell
New-Item -ItemType Directory -Force -Path .tmp\classes | Out-Null
Get-ChildItem -Recurse -Path src -Filter *.java | ForEach-Object { $_.FullName } | Set-Content -Encoding UTF8 .tmp\sources.txt
javac -encoding UTF-8 -d .tmp\classes @.tmp\sources.txt
```

Suggested run command after compile:

```powershell
java -cp .tmp\classes main.java.com.ztydwz.gobang2022.Controller.Start
```

If DeepSeek adds another build path, it must update README and run that path instead.

## Planning Self-Check

- Acceptance criteria clear: yes.
- Forbidden scope clear: yes; AI rewrite, package migration, broad UI redesign, rule redesign, and workflow-file deletion are out of scope.
- Expected modified files listed: yes.
- Required tests listed: yes, including compile and Swing manual smoke.
- Concrete guidelines read, not just index: yes; backend/frontend directory, quality, state, algorithm, database, error, logging, component, listener, type-safety guides and shared thinking guides were read.
- Need user confirmation before implementation: no blocking question for the conservative MVP. If DeepSeek wants to broaden into package migration, build-system migration, AI cleanup, or UI redesign, it must stop and ask first.
- API / DB / frontend DTO alignment: not applicable; this is a desktop Swing app with no API, DB, DTO, or web frontend.
