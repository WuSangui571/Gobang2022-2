# Core Game Development Guidelines

> Rules for Java game-core, Gomoku/Renju rules, AI search, persistence-free data handling, and verification.

---

## Overview

This project is a Java Swing desktop Gomoku/Renju program, not a web backend. In Trellis, the `backend` layer means the non-UI core: board state, move application, game rules, AI search, scoring, record export, diagnostics, and tests.

Current code keeps most shared state in `Static`, uses `int[][] Map` as the board, and places AI/rule logic under `src/main/java/com/ztydwz/gobang2022/Service`. Future work should improve this area toward a first-class board-game engine while preserving playable behavior.

---

## Guidelines Index

| Guide | Description | Status |
|-------|-------------|--------|
| [Directory Structure](./directory-structure.md) | Java package layout and where core/AI/rule code belongs | Filled |
| [Algorithm Guidelines](./algorithm-guidelines.md) | Alpha-beta, evaluation, move generation, and Gomoku AI improvement rules | Filled |
| [Database Guidelines](./database-guidelines.md) | No database policy; board/record state persistence rules | Filled |
| [Error Handling](./error-handling.md) | Swing-safe validation and recoverable failure handling | Filled |
| [Quality Guidelines](./quality-guidelines.md) | Code standards, forbidden patterns, and test expectations | Filled |
| [Logging Guidelines](./logging-guidelines.md) | Console/debug output rules for AI and game flow diagnostics | Filled |

---

## How to Fill These Guidelines

For each guideline file:

1. Preserve the current playable Swing application unless a task explicitly replaces it.
2. Treat `Static.Map`, `ChessController`, `JudgeIfWin`, `AI`, and `Shou` as the existing integration surface.
3. Make algorithm changes measurable: every search/evaluation improvement must include deterministic positions, expected moves, or performance evidence.
4. Keep future architecture moving toward a pure, testable game engine separated from Swing event handling.

---

**Language**: All spec documentation is written in English so future sub-agents receive stable project instructions.
