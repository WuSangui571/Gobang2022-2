# Component Guidelines

> How Swing components are built in this project.

---

## Overview

Swing components are created imperatively. The current UI uses fixed-size layout and absolute bounds. Narrow fixes may preserve this style, but future larger UI work should improve layout predictability without changing gameplay scope.

---

## Component Structure

- Constructors may assemble Swing components, but complex action logic should move to controller or service methods.
- `paint(Graphics)` or `paintComponent(Graphics)` should only draw current state. It must not apply game moves or run AI.
- Button actions should validate state before mutating global game flags.
- Dialogs should return user choices or update settings; they should not start long-running AI work.

---

## Props Conventions

There are no React-style props.

For Swing constructors:

- Pass parent `JFrame` or `JPanel` only when the component needs ownership or dialog positioning.
- Avoid passing broad global state objects if the data can be read from a focused method argument.
- Do not pass pixel coordinates into core AI/rule methods.

---

## Styling Patterns

- Current components use absolute bounds. Preserve them for small fixes.
- For substantial UI changes, prefer stable dimensions and avoid controls overlapping the board.
- Keep button labels short enough for the fixed 90px button width, or increase dimensions as part of the same UI task.
- Use existing image assets for black/white stones and pointer rendering unless a task explicitly changes visual design.

---

## Accessibility

- Dialog text must be readable and specific.
- Buttons must remain reachable without overlapping the board.
- Future improvements should support keyboard shortcuts for start, settings, and undo if UI scope allows.
- Do not rely on console output for user-visible game results.

---

## Common Mistakes

- Calling `repaint()` unconditionally from `paint` risks constant repaint loops. Prefer repaint only after state changes.
- `GameButton` currently creates a "game instructions" button without behavior; future UI work should either implement or remove dead controls.
- Do not add multiple duplicate listeners when settings are opened repeatedly.
- Do not run deep AI search from mouse movement handlers on the Event Dispatch Thread.
