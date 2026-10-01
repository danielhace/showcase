# Blueprint theme

The app's visual identity, established with the Home screen and intended to carry forward to every feature. Grounded in the app's actual subject matter — an index of engineering/platform-capability demos — styled like a technical spec sheet rather than a generic app UI. Deliberately avoids common AI-generated-design tropes: no cream+terracotta, no near-black+acid-accent, no rounded SaaS card grid, no ALL-CAPS eyebrow labels, no numbered 01/02/03 step markers (the list isn't a process sequence), no gratuitous monospace.

## Color

Hand-authored light/dark palette, always active — **not** Material You dynamic color (which would replace this identity with a wallpaper-derived one). Defined in `core/design-system/.../theme/Color.kt` and `Theme.kt`.

| Role | Light | Dark |
|---|---|---|
| Background ("paper") | `#F5F6F3` | `#14181D` |
| Text / primary ("ink") | `#1B2A4A` | `#EDEFF2` |
| Secondary text ("graphite") | `#6B7280` | `#9AA3AE` |
| Accent ("clay") — sparing use only: pressed/active state, thin rule accents. Never a large fill. | `#C1432A` | `#D9694C` |

## Type

**IBM Plex Sans**, sourced as a single variable font (weight axis 100–700, `core/design-system/src/main/res/font/ibm_plex_sans_variable.ttf`, OFL-licensed — see `OFL-ibm-plex-sans.txt` in the same module). IBM Plex was designed by IBM for engineering/technical documentation, which grounds the choice in the app's subject matter rather than a generic default. Weights (Regular/Medium/SemiBold) are selected via `FontVariation` at the single file, avoiding separate static font files per cut. No monospace or condensed cut is bundled — nothing in the app yet has code/id-like content that would justify one.

## Layout

Single-column, edge-to-edge lists. Rows are full-width with generous padding and a hairline `HorizontalDivider` between them — never shadowed/rounded cards. Plain-case screen titles (no ALL CAPS, no eyebrow label above them).

## Motion

One deliberate choreography per screen, not scattered effects: a staggered fade+slide-in of list rows on first appearance only, animated via `graphicsLayer` (not triggering recomposition), guarded so it plays exactly once — it does not replay on scroll (item leaving/re-entering the lazy viewport) or on configuration change (survives rotation via `rememberSaveable`). Standard ripple on row press, tinted with the clay accent. Android has no direct system "reduce motion" flag analogous to iOS; this hasn't been wired up.
