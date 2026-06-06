# Listener Guidelines

> This project does not use React hooks. These rules cover Swing listeners and background tasks.

---

## Overview

There is no web data fetching and no React hook system. The equivalent concept is Swing event listeners, especially `MouseAdapter` subclasses under `Controller`.

---

## Custom Hook Patterns

- Use mode-specific listener classes: free start, designated start, two-player, two-AI.
- Listener methods should orchestrate UI-to-core calls, not implement scoring or rule scanning.
- `mouseClicked` should handle deliberate user moves.
- `mouseMoved` may update pointer display, but should not trigger repeated expensive work.

---

## Data Fetching

There is no server state.

For future long AI searches:

- Do not block the Event Dispatch Thread.
- Use `SwingWorker`, a bounded executor, or a clearly scoped background thread.
- Publish the resulting move back to the UI safely.
- Disable relevant controls while an AI move is in progress.

---

## Naming Conventions

- Listener/controller classes should end with `Controller`.
- Methods should name the action they handle: `aiPutChess`, `chooseFiveDa`, `exchange`.
- Avoid names that encode temporary implementation details.

---

## Common Mistakes

- Triggering AI from `mouseMoved` can make behavior depend on user cursor movement.
- Opening settings repeatedly can attach additional listeners if existing ones are not managed.
- Long-running alpha-beta search in event handlers will freeze the UI.
- Listener logic must check `gameFlag` before applying moves.
