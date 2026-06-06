# State Management

> How UI state, board state, and global game state are managed.

---

## Overview

The project currently centralizes game state in `Model/Static.java`. This is the existing integration contract, but future refactors should move toward explicit board/game-state objects.

---

## State Categories

- Global game state: `Static.Map`, `chessList`, `pointers`, `gameFlag`, `winFlag`, `playerType`, `aiType`, `putChess`, `gameMode`.
- UI state: pointer visibility, dialog selections, temporary five-N-da selection lists.
- Derived state: winner, legal move status, forbidden-hand status, current side to move.
- Server state: none.

---

## When to Use Global State

Do not promote new UI-only variables to `Static`.

Use global state only when current controllers, rendering, and services all need the value. For future engine work, prefer an explicit `GameState` or `BoardState` object and adapt existing globals at the boundary.

---

## Server State

There is no server data.

Synchronization requirements are local:

- After every move, update `Map`, `pointers[row][col].hasChess`, and `chessList` together.
- After undo, revert all three.
- After game end, set `winFlag` and `gameFlag` consistently.
- During five-N-da temporary selection, keep temporary pieces separate from committed pieces until the retained point is chosen.

---

## Common Mistakes

- `Map`, `pointers`, and `chessList` can drift if a move path updates only one of them.
- `winFlag` values are implicit. Future code should document or wrap them: no winner, black, white, draw/forbidden result.
- `ifForbiddenHandOpen` and `ifAllowForbiddenHandOpen` are different settings; do not collapse them.
- `aiType` and `playerType` can change during three-hand exchange; algorithm code should read the current side at move time.
