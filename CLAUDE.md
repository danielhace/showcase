# DanielShowcase

Portfolio showcase app — prioritize performance and distinctive/advanced UX (animations, effects) that demonstrate Android platform capabilities.

## Where to find things (lazy-load, don't preload)

- `specs/` — feature requirements & behavior specs, one file per feature/screen. Read the relevant file before implementing or changing a feature. If no spec exists for the feature in question, ask before assuming behavior.
- `specs/design/` — visual/UX design references (style, motion, mockups). Check before making UI or animation decisions.

## Skills to use here

Core skills for this codebase's implementation work — invoke as relevant to the task at hand:
- `android-compose-ui`, `android-presentation-mvi`, `android-navigation`
- `android-data-layer`, `android-di-koin`, `android-error-handling`
- `android-module-structure`, `android-testing`

Also always relevant given this project's goals:
- `frontend-design` — visual/UX polish, pairs with the performance/UX focus above
- `code-review` / `security-review` — run before considering significant work finished (portfolio-quality bar)
- `dataviz` — only if/when a feature involves charts, graphs, or dashboards

Everything else (process skills like `brainstorming`, `systematic-debugging`, `test-driven-development`, etc., and any other skill) — apply normally, inferred per task.

## Verifying changes — never launch the emulator

`./gradlew build`, `assembleDebug`, `test`, and `lint` are fine to run freely. **Never run `installDebug`/`installRelease`, `./gradlew connectedAndroidTest`, or any other task that boots an emulator or deploys to a device** — starting the emulator makes the user's machine very slow. This applies regardless of what a plan or spec says; if a strategy would otherwise require installing/running the app to verify a change, stop at build/lint/unit-test verification instead and tell the user what manual on-device check they should do themselves. Manual install/run is the user's to trigger, not Claude's.

## Git workflow (Gitflow)

- `main` — always production-ready. Never commit directly; only receives merges from `release/*` or `hotfix/*`, tagged on merge (e.g. `v0.1.0`).
- `develop` — integration branch, latest completed work. Base for all `feature/*` branches.
- `feature/<name>` — one feature/fix per branch, cut from `develop`, merged back into `develop` when done.
- `release/<version>` — cut from `develop` to stabilize a release; merges into both `main` and `develop`.
- `hotfix/<name>` — urgent production fix, cut from `main`, merges into both `main` and `develop`.

Default to working on a `feature/*` branch off `develop` unless told otherwise.
