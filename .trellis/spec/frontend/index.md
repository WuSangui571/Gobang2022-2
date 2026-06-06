# Swing UI Development Guidelines

> Rules for the Java Swing board, controls, rendering, event handling, and user-visible game flow.

---

## Overview

This project has no web frontend. In Trellis, the `frontend` layer means Swing UI code: `GameFrame`, `GamePanel`, `GameDialog`, `GameButton`, `MenuBar`, rendering assets, and mouse/controller interactions that drive visible gameplay.

Future algorithm improvements must still preserve a responsive and understandable UI.

---

## Guidelines Index

| Guide | Description | Status |
|-------|-------------|--------|
| [Directory Structure](./directory-structure.md) | Swing UI package layout and asset placement | Filled |
| [Component Guidelines](./component-guidelines.md) | Swing component construction and rendering rules | Filled |
| [Hook Guidelines](./hook-guidelines.md) | Not React hooks; Swing listener and background-task rules | Filled |
| [State Management](./state-management.md) | UI state, global `Static` state, and board synchronization | Filled |
| [Quality Guidelines](./quality-guidelines.md) | UI review, responsiveness, and accessibility expectations | Filled |
| [Type Safety](./type-safety.md) | Java type conventions for UI and board values | Filled |

---

## How to Fill These Guidelines

For each guideline file:

1. Preserve the current Swing entry path: `Start -> GameFrame -> GamePanel`.
2. Treat UI events as the boundary where pixel coordinates become board coordinates.
3. Keep AI and rule logic out of rendering code.
4. Do not let future deeper AI searches freeze the Event Dispatch Thread.

---

**Language**: All spec documentation is written in English so future sub-agents receive stable project instructions.
