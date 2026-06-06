# Error Handling

> How recoverable game-core and Swing-facing failures are handled.

---

## Overview

This is a desktop Swing application. Errors are either user-correctable UI issues, invalid game-state transitions, or developer/debug failures.

Use user-facing dialogs only at UI boundaries. Core rules and AI should return values or throw clear exceptions that callers translate into UI feedback.

---

## Error Types

No custom error hierarchy exists yet.

Future pure engine code may introduce unchecked exceptions for impossible internal states, for example:

- invalid board size,
- invalid move coordinate,
- applying a move to an occupied point,
- unknown stone value.

For normal user mistakes, prefer validation return values over exceptions.

---

## Error Handling Patterns

- Validate coordinates before indexing arrays.
- Validate numeric dialog input before parsing. `Option.createInputFiveDaNumber()` currently parses directly; future work should reject non-numeric and out-of-range values gracefully.
- Do not use broad `catch (Exception e) {}` in new code.
- Do not use exceptions as normal boundary checks in win detection or pattern scanning. Prefer explicit `isInside(row, col)`.
- Preserve `gameFlag` and `winFlag` consistency when a failure happens during move application.

---

## API Error Responses

There are no API responses.

For UI feedback:

- Use `JOptionPane` for user-actionable messages.
- Keep dialog text short and specific.
- Do not show stack traces in dialogs.

For core methods:

- Return `false` when a click does not map to a legal board point.
- Return a typed result for future move legality checks: legal, occupied, forbidden, out-of-board, game-over.

---

## Common Mistakes

- Silent catch blocks hide rule bugs.
- Empty-board or no-move cases can break undo; check `chessList.isEmpty()` before removing.
- Export can fail if `C:\棋谱` does not exist.
- Input dialogs can return `null` on cancel; handle it before parsing.
