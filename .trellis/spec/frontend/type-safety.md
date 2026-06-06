# Type Safety

> Java type-safety rules for UI, board values, and game state.

---

## Overview

The project uses Java. There is no TypeScript or runtime validation library.

Existing type safety relies on enums such as `ChessType`, `Static.whoFirst`, `Static.whoPutChess`, and `Static.GameMode`, plus integer board values in `Map`.

---

## Type Organization

- Shared game enums live in `Model/Static.java`.
- Chess color information is represented by `Model/ChessType`.
- Board values are currently raw integers in `Map`.
- Future engine work should introduce typed move/point/state objects before broad algorithm rewrites.

---

## Validation

- Validate dialog input before parsing to `int`.
- Validate board coordinates before array access.
- Validate occupied status before applying a move.
- Validate game mode and player type before attaching listeners or triggering AI.

---

## Common Patterns

Preferred future typed concepts:

```java
record Move(int row, int col, ChessType color) {}
record SearchResult(int score, int row, int col) {}
enum MoveLegality { LEGAL, OCCUPIED, OUT_OF_BOARD, FORBIDDEN, GAME_OVER }
```

Use these concepts when a task includes architecture cleanup. For narrow fixes, do not introduce large type refactors unless they are necessary.

---

## Forbidden Patterns

- Do not use magic integers in new public APIs when an enum or small value object is clearer.
- Do not pass array positions as unlabeled `int[]` across many layers in new code. Prefer a named type or at least document `[row, col]`.
- Do not use `null` to mean a failed move if a specific result type can be introduced.
- Do not compare chess colors by UI image or label text.
