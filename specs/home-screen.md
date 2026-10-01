# Home screen

## Purpose

The app's entry point and navigation hub. Lists the platform-capability demos the app showcases; tapping one navigates to that demo. No demos are built yet, so the list currently links to a placeholder.

## Behavior

- Displays a single-column, scrollable list of 15 items. Each item has a title and a one-line description.
- The list is hardcoded in `HomeViewModel` (`feature/home/presentation`) — no repository, persistence, or network call. Content describes plausible future demo features (motion, gestures, shaders, etc.), all unimplemented.
- Tapping an item navigates to a placeholder detail screen showing "`<item title>` — coming soon" with a way back to the list.
- As real features are built, each item's destination is expected to be replaced with actual cross-feature navigation (a callback into that feature's own nav graph), one at a time — the list itself doesn't change shape.

## Non-goals (for now)

- No search, filtering, or reordering of the list.
- No persistence of scroll position or "last viewed" state beyond normal `SavedStateHandle`/process-death survival already provided by the entrance-animation flag.

## Visual/UX

See [`design/blueprint-theme.md`](design/blueprint-theme.md) for the palette, typography, and motion decisions.
