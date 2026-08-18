# Android: Navigation Change (Offers → Events)

## Goal
Replace the "Offers" bottom-nav tab with "Events", without deleting the `offer` feature's code. Implements Epic 4 (US4.1–US4.2) from `us/events-calendar-sync.md`.

## Depends on
`03-android-events-feature.md` — needs `EventsScreen` to exist as the route target.

## File ownership
- `common/ui/component/NavGrah.kt`
- `common/ui/component/bottombar/BottomNavItem.kt` (only if it needs changes beyond what already generically renders `HomeSections.values()` — check first, per the existing research it may already scale automatically)
- `common/ui/theme/Color.kt` (add `EventsColor`)

Does not touch anything under `events/**`, `offer/**`, or the notification files in `04` — safe to work in parallel with those.

## Steps

### 1. Verify no orphaned links to Offers first (US4.2)
Before removing anything, grep the codebase for other references to `HomeSections.OFFERS` or a hardcoded navigation route to Offers (e.g. a banner CTA — this app has a `common/banner` system used by other features to deep-link into a specific tab). If something else links there, decide whether it should be updated or left pointing at the now-unlisted screen before proceeding.

### 2. Update `HomeSections` enum in `NavGrah.kt`
Replace (not rename in place — treat as swap) the `OFFERS` entry with an `EVENTS` entry: new route pointing at `EventsScreen` (from `03`), title "Events", and an icon (placeholder — final icon/art still an open product decision, use `Icons.Filled.Event` from Material icons as a placeholder so the build isn't blocked on final design).

### 3. Update the `NavHost` builder in `homeGraph()`
Replace the `composable(HomeSections.OFFERS.route) { OfferScreen() }` block with `composable(HomeSections.EVENTS.route) { EventsScreen() }`. **Fully remove** the Offers `composable` registration from the graph (per US4.1 — its code/module stays in the repo untouched, it's just unreachable from navigation; a dangling, unrouted composable block serves no purpose).

### 4. Add `EventsColor` to `common/ui/theme/Color.kt`
Follow the existing per-feature color convention (`FoodColor`, `ShoppingColor`, etc.). Value is a placeholder pending final design (see Open items).

## Open items
- Final Events tab icon and `EventsColor` value — not yet decided with the user, placeholders above unblock implementation in the meantime.
- Whether `BottomNavItem.kt` needs any changes at all — prior research suggests it already generically renders whatever's in `HomeSections.values()`, so this may be a zero-change file; confirm at implementation time rather than assuming edits are needed.
