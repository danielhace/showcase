# Home screen

## Purpose

The app's entry point and navigation hub. Lists the platform-capability demos the app showcases; tapping one navigates to that demo.

## Behavior

- Displays a single-column, scrollable list of demos. Each item has a title and a one-line description.
- The list is hardcoded in `HomeViewModel` (`feature/home/presentation`) — no repository, persistence, or network call. Only demos that actually exist are listed (the earlier placeholder items and "coming soon" screen were removed).
- Current items, in order:
  1. **Live face detection** → [`face-detection.md`](face-detection.md)
- Each demo lives in its own feature module. Home emits a navigation event per item; `HomeRoot`/`homeGraph` expose one callback per demo, which `:app` wires to that feature's route. Unknown item ids are ignored.

## Non-goals (for now)

- No search, filtering, or reordering of the list.
- No persistence of scroll position or "last viewed" state beyond normal `SavedStateHandle`/process-death survival already provided by the entrance-animation flag.

## Visual/UX

See [`design/blueprint-theme.md`](design/blueprint-theme.md) for the palette, typography, and motion decisions.
