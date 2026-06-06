# Quality Guidelines

> Code quality standards for Swing UI and interaction development.

---

## Overview

The UI should remain playable, responsive, and consistent while algorithm work evolves. Treat visible behavior as part of the contract.

---

## Forbidden Patterns

- Do not run deep AI search synchronously from `mouseMoved`.
- Do not attach duplicate listeners when reopening settings.
- Do not update game state from drawing methods.
- Do not leave buttons visible with no behavior unless the task explicitly defers them and documents it.
- Do not rely on console output for user-facing instructions or win results.
- Do not create modal dialogs from pure AI/rule code.

---

## Required Patterns

- Create Swing UI on the Event Dispatch Thread, as `Start` currently does with `EventQueue.invokeLater`.
- Check `gameFlag` before accepting moves.
- Keep `paint`/drawing code side-effect free except for rendering.
- Show clear dialogs for settings, exchange, five-N-da prompts, win/loss, and export errors.
- Keep controls visually separated from the board.
- Repaint after state changes, not continuously as a substitute for state events.

---

## Testing Requirements

For UI-affecting tasks, verify manually at minimum:

- start program,
- open settings,
- choose each supported mode touched by the change,
- start game,
- make legal and illegal clicks,
- verify AI response or player turn switch,
- undo after at least one move,
- reach a win state if the task touches rule display.

If adding automated UI tests is practical, keep them focused on component state and controller behavior rather than pixel-perfect rendering.

---

## Code Review Checklist

- Does the program still start from `Start.main`?
- Does the changed mode still attach exactly one listener path?
- Are UI updates on the Event Dispatch Thread?
- Can a long AI move freeze the UI?
- Are button/dialog labels readable with the fixed window size?
- Are temporary five-N-da pieces distinct from committed pieces?
- Does undo remain safe when no moves exist or after game end?
