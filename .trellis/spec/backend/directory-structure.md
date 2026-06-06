# Directory Structure

> How Java game-core, rule, AI, and persistence-related code is organized.

---

## Overview

The repository is an IntelliJ-style Java project. `Gobang2022-2.iml` marks `src` as the source root, so Java packages currently include the prefix `main.java.com.ztydwz.gobang2022`.

There is no server backend. Treat this layer as the core game engine and non-UI services.

---

## Directory Layout

src/
└── main/java/com/ztydwz/gobang2022/
    ├── Controller/   # Mouse/game-flow controllers and move application entry points
    ├── Model/        # Swing models, board globals, chess/pointer/button/window objects
    ├── Service/      # AI, rules, drawing service, clock, record export
    ├── View/         # Top-level Swing frame/dialog helpers
    ├── image/        # Chess and pointer image assets
    └── config/       # Version.properties
```

---

## Module Organization

- Keep UI event handling in `Controller` or Swing-facing `Model` classes.
- Keep rule and AI logic in `Service` until a dedicated engine package is introduced.
- Prefer adding future pure engine code under a clear package such as `Service/engine` or `Model/engine` rather than expanding `Static`.
- Keep record export separate from AI/rule logic. `ExportRecord` should remain a boundary service.
- Do not place algorithm code in `View` or Swing component classes.

---

## Naming Conventions

- Existing classes use PascalCase: `ChessController`, `JudgeIfWin`, `GamePanel`.
- Existing packages use singular top-level names: `Controller`, `Model`, `Service`, `View`.
- Preserve existing names for narrow fixes. For new engine work, prefer precise names such as `BoardState`, `MoveGenerator`, `PositionEvaluator`, `AlphaBetaSearch`.
- Method names should describe intent. Future code should prefer Java lowerCamelCase; preserve existing method names only when changing them would broaden scope.

---

## Examples

- `Controller/ChessController.java`: current integration point for player and AI moves.
- `Service/AI.java`: current AI facade used by controllers.
- `Service/Shou.java`: current AI scoring/search implementation; future refactors should extract from here, not duplicate its behavior elsewhere.
- `Service/JudgeIfWin.java`: current win-detection integration point.
