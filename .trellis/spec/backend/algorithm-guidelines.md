# Algorithm Guidelines

> Rules for improving the Gomoku/Renju AI into a strong, testable game engine.

---

## Current State

The AI entry path is:

- `Controller/ChessController.java` calls `new AI().getLocation()`.
- `Service/AI.java` delegates to `new Shou().getAnswer(Map, ROW, COL, enemyColor, aiColor)`.
- `Service/Shou.java` contains board scoring, forbidden-hand helpers, candidate exclusion, and an `alpha_beta` search.

The current implementation already has an alpha-beta-shaped search and five-tuple scoring, but it is still experimental. Future algorithm work must keep the game playable while making each improvement deterministic and measurable.

---

## Target Direction

The long-term target is a first-class Gomoku/Renju engine:

1. A pure board model that can apply/undo moves without Swing dependencies.
2. A legal-move generator that understands occupied points, neighborhood pruning, and black forbidden hands.
3. A tactical pre-check layer for immediate win/block/forced threats.
4. A deterministic alpha-beta or negamax search with move ordering.
5. A board evaluator based on Gomoku/Renju patterns, not only raw five-tuples.
6. A benchmark suite with known positions and expected best moves.

---

## Required Search Rules

- Prefer negamax with alpha-beta pruning for new search work. If keeping max/min style, make the maximizing and minimizing player roles explicit.
- Search functions must not mutate global `Static.Map` directly. Use a copied board, or an apply/undo API with guaranteed rollback.
- Return a structured result conceptually equivalent to `{score, row, col}`. Avoid passing `alpha` and `beta` as mutable arrays unless refactoring is out of scope.
- Always evaluate terminal states before depth exhaustion: current player win, opponent win, draw/full board, illegal black move.
- Make search depth configurable for experiments. Do not hard-code one depth unless the task is only a narrow bug fix.
- Use deterministic tie-breaking. If two moves have the same score, prefer the move ordering result rather than random selection.

---

## Move Generation

Candidate generation determines alpha-beta quality.

- Never search all 225 points once stones exist unless this is a deliberate baseline benchmark.
- Generate candidates near existing stones, usually within a distance of 1 or 2.
- Always include immediate tactical points even if a neighborhood filter would exclude them.
- Sort candidates before search:
  1. own immediate win,
  2. opponent immediate win block,
  3. own open four / forcing four,
  4. opponent open four / forcing four block,
  5. own open three,
  6. opponent open three block,
  7. center and last-move proximity.
- Do not exclude all edge moves blindly. Edge points can be bad early, but they can be legal and decisive later.

---

## Evaluation Rules

Scoring should recognize board patterns in four directions:

- five in a row,
- open four,
- blocked four / rush four,
- open three,
- jump three,
- blocked three,
- open two,
- opponent equivalents.

The evaluator must score both attack and defense. Blocking an opponent forced win must outrank creating a low-value own threat.

Use large separated weights so the search does not prefer several weak patterns over a forced tactical result. Example ordering:

```text
WIN > open four > blocked four > open three > blocked three > open two
opponent WIN block is mandatory before non-winning attack
```

---

## Renju / Forbidden-Hand Rules

This project exposes forbidden-hand options in `GameDialog` and checks them through `AI.isForbiddenHand`.

- Black-only forbidden-hand rules must not be applied to white.
- If forbidden hands are disabled, the AI may consider otherwise illegal black moves.
- If forbidden hands are enabled and disallowed, the move generator must filter illegal black moves before search.
- Forbidden-hand detection must be covered by position tests before refactoring. At minimum: long six, double-three, double-four, legal five.

---

## Performance Expectations

- AI move selection must remain responsive in the Swing UI. Long searches must not freeze repaint/event handling.
- For expensive searches, introduce time budgets or iterative deepening before increasing depth.
- Record benchmark evidence for algorithm PRs: position count, depth, elapsed time, chosen move, and score.
- Do not add broad console output inside tight search loops. It distorts performance and makes tests noisy.

---

## Testing Requirements

Every algorithm change should add or update deterministic tests or a local benchmark note covering:

- immediate win detection,
- immediate block detection,
- obvious attack preference,
- forbidden-hand legality when enabled,
- no mutation of the input board after search,
- stable best move for at least one mid-game position.

If the project still lacks a test framework, create simple Java tests or documented reproducible harnesses before large algorithm rewrites.

---

## Forbidden Patterns

- Do not choose AI moves with `Random` except for explicitly marked demonstration modes such as current five-N-da placeholder behavior.
- Do not mix Swing `JOptionPane` calls into pure AI search or evaluation code.
- Do not use exceptions as normal board-boundary control in new algorithm code.
- Do not print every evaluated node during search.
- Do not change `Map` encoding (`0` empty, `1` black, `2` white) without a migration plan and full integration update.

---

## Review Checklist

- Does the algorithm preserve current supported game modes?
- Does it avoid mutating global state unexpectedly?
- Are legal moves and forbidden hands handled before scoring?
- Is candidate generation sorted and deterministic?
- Are terminal states checked before heuristic evaluation?
- Is the chosen move explainable from score/threat evidence?
- Are tests or benchmark positions included?
