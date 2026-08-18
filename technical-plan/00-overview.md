# Technical Implementation Plan — Overview & Shared Schema

Implements the requirements in `us/events-calendar-sync.md`. Split into independent files so separate engineers/agents can build each piece with minimal coordination. **Read this file first** — it's the canonical source for the data schema and conventions every other file assumes and repeats relevant excerpts of.

## Files in this plan

| File | Covers | Depends on |
|---|---|---|
| `00-overview.md` (this file) | Schema, conventions, sequencing | — |
| `01-backend-cloud-function.md` | Calendar → Firestore sync job, notification sending | Schema below; service account from `02` |
| `02-firebase-infra.md` | GCP/Firebase console & CLI setup (accounts, rules, TTL, scheduler) | Nothing — start immediately |
| `03-android-events-feature.md` | New `events` Kotlin/Compose feature module + Room | Schema below |
| `04-android-notifications.md` | FCM client integration, permission flow, deep links | `03` (needs `EventsScreen` to exist as a mount point) |
| `05-android-navigation.md` | Swap "Offers" bottom-nav tab for "Events" | `03` (needs `EventsScreen` to route to) |

## Suggested build order

1. **Start `02-firebase-infra.md` immediately.** It's mostly account/console setup with real lead time — the owner needs to create and share her calendar — so kicking it off first saves the most calendar time, and nothing else needs it *finished*, only *in progress* (specifically, the service account's email needs to exist early since it's what gets sent to the owner).
2. **`01-backend-cloud-function.md`** can be built and tested in isolation — manual invocation, inspecting Firestore in the console — without the Android app existing.
3. **`03`, `04`, `05` (Android)** don't need `01` finished, only the schema below fixed. Hand-seed a few Firestore documents matching the schema to build/test the UI against, then swap to the real backend later. `03` should land first since `04`/`05` both need `EventsScreen` to exist, but `04` and `05` touch different files from each other (see "File ownership" in each doc) and can proceed in parallel once `03`'s screen exists.
4. Final integration/end-to-end test needs `01` and `02` actually deployed, plus `03`/`04`/`05` done.

## Assumed technical choices (not yet re-confirmed with the user — flagged, not blocking)

- **Cloud Function runtime: Node.js / TypeScript** (2nd gen), using `firebase-admin` (Firestore + FCM) and `googleapis` (Calendar API). Most common/best-documented combination for Firebase; swapping to Python doesn't change this plan's structure much if preferred later.
- **Android date/time uses `java.time`**, not the legacy `SimpleDateFormat` pattern used elsewhere in this app (`offer/domain/*`), because Events needs real date arithmetic (day-grouping, "is this today/tomorrow", multi-day spanning) that other features don't need. Since `minSdk = 21`, this requires enabling **core library desugaring** — see `03-android-events-feature.md`.

## Firestore schema (canonical)

### Collection: `events`
Document ID = the Google Calendar event `id` (recurring events are out of scope per Decision 16 in the US doc, so this is always a stable, plain ID — no instance-ID handling needed).

| Field | Type | Notes |
|---|---|---|
| `id` | string | **Also stored as a plain field, duplicating the document ID.** This app's existing Firestore read helper (`common/utils/FireStoreUtils.kt`'s `fetchList<T>()`, used by every other feature) deserializes via `DocumentSnapshot.toObject(T::class.java)`, which does **not** capture the document ID automatically (no `@DocumentId` usage exists anywhere in this codebase — confirmed by grep). Every existing collection (`Tips`, `Blog`, `Food`, ...) works around this by storing `id` as an ordinary field inside the document body, and the Cloud Function must follow the same convention so the Android client's existing fetch pattern keeps working unmodified. |
| `title` | string | from `summary` |
| `description` | string | from `description`, `""` if absent |
| `location` | string | from `location`, `""` if absent — client shows the location section only when non-empty |
| `startAt` | Firestore Timestamp | from `start.dateTime` or `start.date` (all-day, midnight local) |
| `endAt` | Firestore Timestamp | from `end.dateTime` or `end.date` |
| `isAllDay` | boolean | true if Calendar gave a `date` instead of `dateTime` |
| `timeZone` | string | IANA tz id from `start.timeZone`; default `Europe/Athens` if absent |
| `colorId` | string \| null | raw Calendar `colorId`, kept for debugging/remapping |
| `category` | string \| null | derived from `colorId` via the fixed legend (see `01-backend-cloud-function.md`); `null` if unmapped |
| `displayColor` | string | hex color (e.g. `"#4ECDC4"`), deterministically hashed from the event `id` (`functions/src/displayColor.ts`) - purely a card-UI accent, unrelated to `colorId`/`category`. Stable per event, not re-randomized on re-sync. Only set on events written/touched after this field was added - older untouched events fall back to a default color client-side until next synced. |
| `status` | `"confirmed"` \| `"cancelled"` | `"cancelled"` when Calendar reports it cancelled, or when a previously-synced event disappears from a nightly full pull |
| `calendarUpdatedAt` | Firestore Timestamp | Calendar's own `updated` field — used to detect edits |
| `expireAt` | Firestore Timestamp | `endAt + 90 days`, computed at write time — the field the Firestore TTL policy targets |
| `firstSyncedAt` | Firestore Timestamp | server time this doc was first created (debugging aid, not used by app logic) |

### Collection: `syncState` (single doc, not user-facing, not readable by clients)
Doc ID: `calendarSync`

| Field | Type | Notes |
|---|---|---|
| `lastSuccessfulCheckAt` | Firestore Timestamp | used as `updatedMin` for the next 15-min check; **only advances after a run succeeds** (US1.7) |
| `lastFullReconciliationAt` | Firestore Timestamp | informational — last time the nightly full pass ran |
| `hasCompletedInitialBackfill` | boolean | flips to `true` after the very first run ever completes — gates notification sending (US1.8) |

## Shared conventions

- Firebase project: `welovemarathon-71ff6` (Blaze plan, already active).
- Android package root: `com.jdagnogo.welovemarathon` — new feature lives at `com.jdagnogo.welovemarathon.events`, matching the existing per-feature package convention documented in `CLAUDE.md`.
- FCM topic name: `events`.
- Notification channel id: `events_channel`, display name **"Local Events"** (US3.3).
- Category legend: **not yet finalized** with the owner. `01-backend-cloud-function.md` defines the color→category mapping as a single, easily-edited constant so it can be filled in later without touching any other logic.
- Still-open product decisions not blocking implementation: final Events tab icon + brand color (`05-android-navigation.md`), the actual category legend content (`01-backend-cloud-function.md`).
