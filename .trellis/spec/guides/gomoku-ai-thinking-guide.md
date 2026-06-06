# Gomoku AI Thinking Guide

> Use this guide before changing rules, board state, alpha-beta search, evaluation, or AI move selection.

---

## Goal

The project should evolve from a playable Swing prototype into a strong, testable Gomoku/Renju engine. Algorithm changes must be grounded in board positions and measurable evidence, not subjective manual play alone.

---

## First Questions

Before editing AI code, answer these:

- What exact weakness is being improved: immediate tactics, search depth, move ordering, forbidden hands, evaluation quality, or performance?
- Which current entry point is affected: `AI.getLocation`, `Shou.getAnswer`, `alpha_beta`, `JudgeIfWin`, or controller move flow?
- Does the change need to preserve all modes: free start, designated start, two-player, and two-AI?
- Is the rule Gomoku-only or Renju-specific?
- Is black forbidden-hand behavior involved?
- How will the improvement be proven?

---

## Position-First Workflow

For algorithm tasks, start from concrete board positions.

1. Write down the board, side to move, and expected best move.
2. Classify the reason: win, block, open four, double threat, forbidden-hand avoidance, positional score.
3. Run or create a deterministic harness.
4. Compare old and new selected moves.
5. Record depth, candidate count, elapsed time, and score if available.

Do not start by only changing weights. Weight tuning without position evidence usually creates regressions elsewhere.

---

## Tactical Priority Order

Before relying on deep search, check tactical facts:

1. If the current side can win immediately, choose that move.
2. If the opponent can win immediately, block it.
3. Prefer forcing threats such as open four.
4. Avoid giving the opponent immediate forcing threats.
5. Only then use general positional evaluation.

This order should be visible in code or tests.

---

## Alpha-Beta Checklist

- Are terminal positions evaluated before heuristic positions?
- Is the side-to-move clear at every depth?
- Are candidate moves sorted before recursion?
- Is alpha-beta pruning using immutable scalar alpha/beta values or a carefully controlled result object?
- Does every simulated move get undone or happen on a deep-copied board?
- Does the search avoid illegal black forbidden-hand moves when the setting requires it?
- Is the result deterministic for equal scores?

---

## Evaluation Checklist

The evaluator should reason about both sides:

- Own five and opponent five.
- Own open four and opponent open four.
- Own blocked four/rush four and opponent blocked four/rush four.
- Own open three/jump three and opponent equivalents.
- Long-term positional value such as center and local density.

Large tactical threats should dominate several small positional bonuses.

---

## Performance Checklist

- Count candidates before and after pruning.
- Measure elapsed time for representative positions.
- Avoid logging every node.
- Do not increase search depth until candidate ordering is reasonable.
- If the UI freezes, move search off the Swing Event Dispatch Thread or add a time budget.

---

## Review Output

Every substantial AI task should report:

- changed algorithm surface,
- benchmark positions used,
- selected moves before and after,
- search depth and elapsed time,
- forbidden-hand behavior if relevant,
- any remaining known weaknesses.
