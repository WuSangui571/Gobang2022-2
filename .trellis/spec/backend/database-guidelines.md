# Data Persistence Guidelines

> This project has no database. These rules cover in-memory board state and local record export.

---

## Overview

The game state is in memory. The central board representation is `Static.Map`, a `15 x 15` `int[][]` where:

- `0` means empty,
- `1` means black,
- `2` means white.

The visual move list is `Static.chessList`. Any future engine abstraction must preserve this integration contract until all controllers and rendering code are migrated.

---

## Query Patterns

- Treat board reads like queries: use row/column coordinates consistently.
- Do not inspect Swing pixel coordinates inside AI or rule evaluators. Convert pixels to board coordinates at the controller boundary.
- Search/evaluation code must work from a board snapshot, not from mutable UI component state.
- When copying boards, deep-copy rows. `board.clone()` only clones the outer array and is not sufficient for safe search mutation.

---

## Migrations

There are no database migrations.

For future state-model migrations:

1. Add adapter code from existing `Static.Map` and `chessList`.
2. Keep old controllers working until the migration is complete.
3. Add tests that prove old and new board representations agree on occupied points, winner, and legal moves.

---

## Naming Conventions

- Board coordinates should be named `row` and `col`, not `x` and `y`, inside rules and AI.
- Pixel coordinates should be named `x` and `y`, and should only appear in UI/controller/rendering code.
- Move values should use named constants or enums in new code instead of raw `1` and `2`, while preserving external compatibility with `Map`.

---

## Common Mistakes

- Do not hard-code export paths without creating the directory or reporting failure clearly. Current `ExportRecord` writes to `C:\棋谱\...`.
- Do not let search code mutate `Static.Map` without rollback.
- Do not rely on `chessList` alone for rule truth; `Map` and `pointers` must remain synchronized until the state model is refactored.
