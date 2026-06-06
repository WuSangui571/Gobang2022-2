# Quality Guidelines

> Code quality standards for Java game-core, rules, and AI development.

---

## Overview

The current project is a playable Java Swing prototype. Future work should improve it toward a maintainable, testable Gomoku/Renju engine without breaking existing modes.

Core rule: algorithm changes must be verified by positions, not only by manual play.

---

## Forbidden Patterns

- Do not add new global mutable state to `Static` unless a narrow integration fix requires it.
- Do not mix Swing dialogs into pure game-rule or AI search code.
- Do not use `Random` for normal AI move choice.
- Do not use exceptions for expected board-edge scanning.
- Do not duplicate win/forbidden-hand logic in multiple controllers.
- Do not make algorithm behavior depend on mouse movement frequency.
- Do not change board encoding without updating all controllers, rendering, export, and tests.

---

## Required Patterns

- Keep `Map`, `pointers`, and `chessList` synchronized after every move and undo.
- Check `gameFlag` and `winFlag` before applying user or AI moves.
- Keep UI coordinates and board coordinates separate.
- Prefer pure helper methods for rule/evaluation logic so they can be unit tested.
- For search, deep-copy board rows or use explicit apply/undo.
- For future algorithm tasks, record before/after evidence: positions, chosen moves, depth, and elapsed time.

---

## Testing Requirements

Minimum tests or reproducible harness coverage for future core work:

- horizontal, vertical, and both diagonal five-in-a-row;
- board-edge win detection;
- draw/full-board behavior;
- undo after player and AI moves;
- black forbidden-hand cases when enabled;
- AI immediate win and immediate block;
- AI search must not mutate input board;
- deterministic result for equal-score ties.

If no test framework exists yet, introducing one is preferred before large algorithm rewrites.

---

## Code Review Checklist

- Does the change preserve all four modes: free start, designated start, two-player, two-AI?
- Is the board state still synchronized across `Map`, `pointers`, and `chessList`?
- Are UI calls kept out of pure algorithm code?
- Are edge cases handled without broad catch blocks?
- Is AI behavior deterministic and explainable?
- Are forbidden-hand settings respected?
- Is there a manual or automated verification path documented?
