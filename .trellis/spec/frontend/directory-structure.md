# Directory Structure

> How Swing UI code is organized in this project.

---

## Overview

The UI is a Java Swing desktop interface. It is split across `View`, Swing-facing `Model` classes, and controller listeners.

---

## Directory Layout

src/
└── main/java/com/ztydwz/gobang2022/
    ├── View/
    │   ├── GameFrame.java    # Main JFrame
    │   └── Option.java       # JOptionPane helper
    ├── Model/
    │   ├── GamePanel.java    # Board panel and listener wiring
    │   ├── GameDialog.java   # Game settings dialog
    │   ├── GameButton.java   # Start/settings/help/undo buttons
    │   ├── MenuBar.java      # JMenuBar setup
    │   └── image values, chess, pointer, clock models
    ├── Controller/           # Mouse listeners and game-flow controllers
    └── image/                # Black/white chess and pointer assets
```

---

## Module Organization

- Put top-level windows and simple dialog helpers in `View`.
- Put reusable Swing components or visual models in `Model`.
- Put event listeners and mode-specific interaction logic in `Controller`.
- Put drawing operations in `Service/DrawService` only while following the current structure; do not add AI/rule logic there.
- Keep image assets under the existing `image` folder unless a build-system migration changes resource handling.

---

## Naming Conventions

- Use PascalCase for Swing class names: `GameFrame`, `GamePanel`, `GameDialog`.
- Use clear controller names ending with `Controller` for listeners.
- Avoid ambiguous UI names such as `Panel2` or `ButtonUtils`.
- Use board terms consistently: panel pixels are `x/y`; board positions are `row/col`.

---

## Examples

- `View/GameFrame.java`: main frame setup.
- `Model/GamePanel.java`: board panel, button creation, and controller selection by game mode.
- `Model/GameDialog.java`: settings dialog and game-mode configuration.
- `Controller/FreeStartController.java`: example of user-vs-AI interaction flow.
