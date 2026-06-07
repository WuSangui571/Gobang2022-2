# Journal - sangui (Part 1)

> AI development session journal
> Started: 2026-06-06

---



## Session 1: Project cleanup and Swing smoke acceptance

**Date**: 2026-06-06
**Task**: Project cleanup and Swing smoke acceptance
**Branch**: `chore/project-cleanup`

### Summary

Archived project-cleanup after manual acceptance; recorded repository hygiene, README build path, Swing repaint/input/export fixes, compile/resource verification, and retained boundaries.

### Main Changes

## Work Commit

- `93fc322 chore:整理项目清理与构建说明`

## Main Modules Changed

- Repository hygiene: added root ignore rules and removed tracked generated output under `out/production`.
- Build and run documentation: updated README with Java requirements, PowerShell compile/run commands, generated output path, and resource copying.
- Swing rendering and interaction: fixed repaint responsibility after removing the old paint-loop repaint behavior.
- Swing controls and dialogs: implemented the game instructions button, hardened five-N-da input validation, and made empty undo safe.
- Record export: created missing export directories, showed user-facing export errors, cleared repeated export state, and handled empty records.

## Updated Files

- `.gitignore`
- `README.md`
- `.trellis/tasks/archive/2026-06/06-06-project-cleanup/**`
- `src/main/java/com/ztydwz/gobang2022/Controller/ChessController.java`
- `src/main/java/com/ztydwz/gobang2022/Controller/DesignatedStart.java`
- `src/main/java/com/ztydwz/gobang2022/Controller/DesignatedStartController.java`
- `src/main/java/com/ztydwz/gobang2022/Controller/PointerController.java`
- `src/main/java/com/ztydwz/gobang2022/Controller/TwoAiBattleController.java`
- `src/main/java/com/ztydwz/gobang2022/Model/GameButton.java`
- `src/main/java/com/ztydwz/gobang2022/Model/GamePanel.java`
- `src/main/java/com/ztydwz/gobang2022/Service/ClockThread.java`
- `src/main/java/com/ztydwz/gobang2022/Service/ExportRecord.java`
- `src/main/java/com/ztydwz/gobang2022/View/Option.java`
- tracked generated files under `out/production/**` removed from version control

## Verification

- `git diff --check` passed; only normal CRLF conversion warnings were printed.
- `git check-ignore -v out/production/Gobang2022-2/main/java/com/ztydwz/gobang2022/Controller/Start.class .tmp/classes/main/java/com/ztydwz/gobang2022/image/BlackChess.png build/example.class` confirmed generated paths are ignored.
- `Get-ChildItem -Recurse -Path src -Filter *.java | ForEach-Object { $_.FullName } | Set-Content -Encoding ASCII .tmp\sources.txt` succeeded.
- `javac -encoding UTF-8 -d .tmp\classes "@.tmp\sources.txt"` passed.
- `Copy-Item -Recurse -Force src\main\java\com\ztydwz\gobang2022\image .tmp\classes\main\java\com\ztydwz\gobang2022\` succeeded.
- `Copy-Item -Recurse -Force src\main\java\com\ztydwz\gobang2022\config .tmp\classes\main\java\com\ztydwz\gobang2022\` succeeded.
- `Test-Path .tmp\classes\main\java\com\ztydwz\gobang2022\image\BlackChess.png` returned `True`.
- `Test-Path .tmp\classes\main\java\com\ztydwz\gobang2022\config\Version.properties` returned `True`.
- Manual Swing smoke testing was reported by the user as passed.

## Result And Boundaries

- The cleanup task is complete and was archived to `.trellis/tasks/archive/2026-06/06-06-project-cleanup`.
- The project still intentionally has no Maven or Gradle build file and no automated test framework.
- Package names remain `main.java.com.ztydwz.gobang2022...`; no package migration was done.
- AI strength, Renju rule semantics, board encoding, and search behavior were not intentionally changed.
- `00-bootstrap-guidelines` remains active because it was not part of this cleanup task.


### Git Commits

| Hash | Message |
|------|---------|
| `93fc322` | (see git log) |

### Testing

- [OK] (Add test results)

### Status

[OK] **Completed**

### Next Steps

- None - task complete


## Session 2: Alpha-beta AI search

**Date**: 2026-06-07
**Task**: Alpha-beta AI search
**Branch**: `feature/alpha-beta-ai`

### Summary

Implemented configurable alpha-beta AI search, completed Codex quality fixes, passed javac/harness checks, and user manual Swing smoke passed.

### Main Changes

## Work Commit

- `556cd78 feat:增强五子棋AI搜索`

## Main Modules Changed

- AI search core: `Service/Shou.java`, `Service/AiSearchConfig.java`, `Service/SearchResult.java`
- Swing turn flow: `Controller/ChessController.java`, `Controller/FreeStartController.java`, `Controller/DesignatedStartController.java`, `Controller/TwoAiBattleController.java`
- Settings/global state: `Model/GameDialog.java`, `Model/Static.java`
- Deterministic validation harness: `src/test/java/com/ztydwz/gobang2022/Service/AiSearchHarness.java`
- Trellis task records: `.trellis/tasks/06-06-alpha-beta-ai/`

## Verification

- `javac -encoding UTF-8 -d .tmp\classes "@.tmp\sources.txt"` passed.
- `Copy-Item -Recurse -Force src\main\java\com\ztydwz\gobang2022\image .tmp\classes\main\java\com\ztydwz\gobang2022\` passed.
- `Copy-Item -Recurse -Force src\main\java\com\ztydwz\gobang2022\config .tmp\classes\main\java\com\ztydwz\gobang2022\` passed.
- `java -cp .tmp\classes com.ztydwz.gobang2022.Service.AiSearchHarness` passed with `24 passed, 0 failed`.
- Manual Swing smoke was reported by the user as passed on 2026-06-07.

## Result And Boundaries

- Implemented configurable Low / Medium / High deterministic alpha-beta AI with early-game depth reduction and time caps.
- Preserved supported Swing modes and added AI in-progress guards for long search paths.
- Added legal move handling for no-candidate and full-board cases.
- Codex check fixed negamax terminal scoring, forbidden-hand filtering for immediate wins, invalid AI move guards, and stronger harness assertions.
- No database, API, infra, package-root, or build-system migration was introduced.


### Git Commits

| Hash | Message |
|------|---------|
| `556cd78` | (see git log) |

### Testing

- [OK] (Add test results)

### Status

[OK] **Completed**

### Next Steps

- None - task complete


## Session 3: Record Gobang AI tactical scoring closeout

**Date**: 2026-06-07
**Task**: Record Gobang AI tactical scoring closeout
**Branch**: `feature/ai-scoring-pruning`

### Summary

Archived the completed Gobang AI scoring/pruning task after commit and manual acceptance.

### Main Changes

Commit: 038026e2ef8d0297dc62d126c445c301dfed9684
Branch: feature/ai-scoring-pruning
Task: 06-07-ai-scoring-pruning-plan

Main modules changed:
- AI search and scoring: src/main/java/com/ztydwz/gobang2022/Service/Shou.java
- Deterministic AI harness: src/test/java/com/ztydwz/gobang2022/Service/AiSearchHarness.java
- Trellis task context/archive metadata under .trellis/tasks/

Updated files:
- src/main/java/com/ztydwz/gobang2022/Service/Shou.java
- src/test/java/com/ztydwz/gobang2022/Service/AiSearchHarness.java
- .trellis/tasks/archive/2026-06/06-07-ai-scoring-pruning-plan/*

Verification completed:
- javac -encoding UTF-8 -d .tmp\classes "@.tmp\sources.txt" passed
- java -cp .tmp\classes com.ztydwz.gobang2022.Service.AiSearchHarness passed: 36 passed, 0 failed
- git diff --check passed with only LF/CRLF warnings
- python ./.trellis/scripts/task.py validate 06-07-ai-scoring-pruning-plan passed before archive
- Manual Swing acceptance was confirmed by the user after Codex check/finish-work

Result:
- DeepSeek implemented tactical ordering and scoring changes for Gobang AI.
- Codex added a narrow immediate-block fast path before negamax and a harness regression case.
- Codex removed legacy tight-loop debug output from Shou.java.
- The implementation preserves AI.getLocation/getAnswer contracts, board encoding, forbidden-hand coverage, deterministic search evidence, and no input-board mutation.
- The completed task was archived after commit and manual acceptance.

Boundaries and follow-up:
- No Maven/Gradle migration, API/DB/infra change, Swing controller rewrite, or full Renju engine rewrite was done.
- MEDIUM tactical search can still take several seconds in the harness, so future work may target candidate caps, tactical pre-check expansion, or moving long AI work off the Swing EDT.


### Git Commits

| Hash | Message |
|------|---------|
| `038026e2ef8d0297dc62d126c445c301dfed9684` | (see git log) |

### Testing

- [OK] `javac -encoding UTF-8 -d .tmp\classes "@.tmp\sources.txt"` passed.
- [OK] `java -cp .tmp\classes com.ztydwz.gobang2022.Service.AiSearchHarness` passed: 36 passed, 0 failed.
- [OK] `git diff --check` passed with only LF/CRLF warnings.
- [OK] `python ./.trellis/scripts/task.py validate 06-07-ai-scoring-pruning-plan` passed before archive.
- [OK] Manual Swing acceptance was confirmed by the user.

### Status

[OK] **Completed**

### Next Steps

- None - task complete
