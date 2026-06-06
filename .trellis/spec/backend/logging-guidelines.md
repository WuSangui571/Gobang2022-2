# Logging Guidelines

> Console/debug output rules for game flow, AI search, and diagnostics.

---

## Overview

The project currently uses direct `System.out.println` and stack traces. There is no logging framework.

For narrow fixes, follow the existing style only when needed. For algorithm work, keep diagnostics controlled and removable because AI search loops can evaluate many positions.

---

## Log Levels

Without a logging framework, use these conventions:

- Normal user flow: prefer UI messages, not console logs.
- Debug diagnostics: `System.out.println` is acceptable only outside tight loops.
- Recoverable failures: print a concise message and keep the UI stable.
- Unexpected exceptions: include enough context to reproduce the board state, then fail clearly.

---

## Structured Logging

When logging AI decisions, include:

- side to move,
- selected row/col,
- score,
- depth,
- candidate count,
- elapsed milliseconds.

Example format:

```text
AI move side=BLACK row=7 col=7 score=10000 depth=4 candidates=28 elapsedMs=42
```

---

## What to Log

- Algorithm benchmark runs.
- AI selected move summaries during debugging.
- Export failures and target path.
- Invalid state detected during tests or development.

---

## What NOT to Log

- Do not print every searched node in alpha-beta.
- Do not print huge board dumps repeatedly in UI event handlers.
- Do not leave temporary debug spam in committed algorithm changes.
- Do not use logs as the only proof of correctness; add tests or benchmark records.
