# Backend: Cloud Function Sync Job

**Status: implemented** in `functions/` (TypeScript, `firebase-admin` + `googleapis`). `npm run build` (tsc) and `npm test` (Jest, 12 tests on the pure diff/mapping logic) both pass. Not yet deployed — that's a live action requiring explicit go-ahead, see `02-firebase-infra.md`.

## Goal
A scheduled Cloud Function that keeps Firestore's `events` collection in sync with the owner's shared Google Calendar, and sends push notifications for brand-new events. Implements US1.1–US1.8 and US3.1/US3.6 from `us/events-calendar-sync.md`.

## Recurring events: how `singleEvents=true` was reconciled with Decision 16
This doc originally called for `singleEvents=true` without addressing that Google expands a recurring series into one item per date instance — which on its own would contradict Decision 16 ("recurring events sync only as a single occurrence"). Resolved in `eventTransform.ts`'s `dedupeRecurringInstances()`: still fetch with `singleEvents=true` (so Google hands us concrete start/end dates and we never have to parse RRULEs ourselves), then collapse every series (grouped by `recurringEventId`) down to just its single soonest-upcoming instance before any other processing happens. A weekly event therefore syncs as one ordinary one-off event, matching Decision 16, without us reimplementing recurrence math.

## Depends on
- Schema in `00-overview.md`.
- The `calendar-sync` service account existing and having been shared the owner's dedicated calendar (see `02-firebase-infra.md`) — needed for live testing, not for writing the code.

## Trigger design
One function, `syncCalendarEvents`, triggered by Pub/Sub (not HTTP — simpler IAM story, no OIDC token needed between Cloud Scheduler and the function). Two Cloud Scheduler jobs publish to it with different payloads:
- `sync-events-frequent`: cron `*/15 * * * *`, message `{"mode":"incremental"}`.
- `sync-events-nightly`: cron `0 3 * * *` (03:00 Europe/Athens), message `{"mode":"full"}`.

## Files
- `functions/src/index.ts` — exports `syncCalendarEvents`, reads `mode` from the Pub/Sub message, dispatches to the two code paths below.
- `functions/src/calendarSync.ts` — orchestration for both modes (I/O: reads/writes Firestore, calls the Calendar API, sends notifications).
- `functions/src/eventTransform.ts` — pure, framework-free diff/mapping logic (`dedupeRecurringInstances`, `parseCalendarEvent`, `diffFullSync`) — unit-tested in `src/__tests__/eventTransform.test.ts`.
- `functions/src/calendarClient.ts` — Calendar API `events.list` wrapper (incremental + full-window variants).
- `functions/src/firestoreStore.ts` — small helpers for reading/writing `events` and `syncState`.
- `functions/src/categoryMap.ts` — the `colorId → category` constant (currently all `null` — placeholder pending the real legend).
- `functions/src/notify.ts` — notification composition + FCM send.
- `functions/src/config.ts` — `CALENDAR_ID` (from `.env`), sync window/retention constants, the service account email.
- `functions/package.json`, `functions/tsconfig.json`, `functions/jest.config.js` — standard TypeScript + Jest scaffold.
- `functions/.env` — holds `CALENDAR_ID`; currently pointed at a test calendar (not the owner's), swap this one line + redeploy when ready (see "testing before go-live" in `02-firebase-infra.md`).
- Root `firebase.json`, `.firebaserc`, `firestore.rules`, `firestore.indexes.json` — added alongside this, since none existed before.

## Incremental mode (`mode: "incremental"`, every 15 min)
1. Read `syncState/calendarSync`.
2. Call Calendar API `events.list`:
   `calendarId=<owner's dedicated calendar id>`, `updatedMin=lastSuccessfulCheckAt`, `singleEvents=true`, `timeMin=now`, `timeMax=now+3months`, `showDeleted=true` (surfaces cancellations even in an incremental query — Calendar returns them with `status: "cancelled"`).
3. If empty → set `lastSuccessfulCheckAt = now`, done (no Firestore writes, no notification).
4. If non-empty, process each returned event **independently** (wrap each in its own try/catch — one malformed event must not abort the batch, per US1.3):
   - `isNew = !existsInFirestore(event.id)` (targeted `get` by doc ID, not a full collection read).
   - Derive `category` from `event.colorId` via `categoryMap.ts`.
   - If `event.status === "cancelled"`: if it previously existed, upsert `status: "cancelled"` (keep the doc, per US1.6). If it never existed, skip — nothing to cancel.
   - Otherwise: upsert the full document per the schema in `00-overview.md`, including `expireAt = endAt + 90 days` **and `id: event.id` as a plain field, not just the document ID** — required by the Android client's existing Firestore fetch helper, which doesn't capture document IDs (see the `id` row in `00-overview.md`'s schema table).
   - Track new (non-cancelled) events in a local list for the notification step.
5. If `syncState.hasCompletedInitialBackfill === true` and the new-events list is non-empty → send a notification (see `notify.ts` below, US1.8 gate).
6. **Only after all writes succeed**, set `lastSuccessfulCheckAt = now`. If anything unrecoverable throws, log it and leave the checkpoint untouched so the next run retries from the same point (US1.3/US1.7).

## Full mode (`mode: "full"`, nightly)
1. Call Calendar API `events.list`: `timeMin=now`, `timeMax=now+3months`, `singleEvents=true`, **no `updatedMin`** (full pull of the whole window), `showDeleted=false`.
2. Query Firestore for all `events` docs where `startAt >= now` into a map keyed by ID.
3. Diff:
   - In Calendar's response but not in Firestore, or `updated` timestamp changed → same per-event upsert logic as incremental (new → notify only if `hasCompletedInitialBackfill`; changed-but-existing → update, never notify per US3.5).
   - In Firestore's map but missing from Calendar's response → mark `status: "cancelled"` (this is the primary mechanism that catches real deletions, since a plain `events.list` call without `showDeleted=true` simply omits deleted events rather than flagging them).
4. **First-ever run** (`hasCompletedInitialBackfill` was `false` before this run): after finishing, set it to `true` and send **no notification** for this run's "new" events, however many there are (US1.8). Every subsequent run behaves normally.
5. Set `lastFullReconciliationAt = now` and `lastSuccessfulCheckAt = now` (a full pull supersedes the incremental checkpoint).

## Category mapping (`categoryMap.ts`)
Before filling in real values, call the Calendar API `colors.get` endpoint once (a scratch script or `curl`, not part of the function) to confirm the authoritative `colorId → name` table — don't hardcode guessed names. Then define:

```ts
// Placeholder — colorId keys confirmed via colors.get, category values TBD with the owner.
export const CATEGORY_BY_COLOR_ID: Record<string, string | null> = {
  "1": null, "2": null, "3": null, "4": null, "5": null,
  "6": null, "7": null, "8": null, "9": null, "10": null, "11": null,
};
```
This isolates the still-open "what's the actual legend" product decision to one small, obviously-labeled spot — everything else in the pipeline works correctly with every color mapped to `null` (events just show as uncategorized) until the real legend is filled in.

## Notification composition (`notify.ts`)
**Revised beyond the original US3.5 scope**: cancellation notifications were added after initial ship, once it became clear a cancellation is often more actionable than a new event (someone could otherwise show up for nothing). New-event and cancellation notifications are two fully independent tracks - separate FCM topic, separate Android notification channel, separate settings toggle - so a user can mute one without the other.

- **New event, single**: title `"🎉 New event in Marathon!"`, body `"{title} — {formatted date}"`, data payload `{ eventId: event.id }` (client deep-links straight to that event's detail — US3.2). Topic `events`, channel `events_channel`.
- **New events, digest**: title `"🎉 {count} new events in Marathon!"`, body `"Tap to see what's new"`, no `eventId` in the payload (client opens the Events list when `eventId` is absent).
- **Cancellation, single**: title `"⚠️ Event cancelled"`, body `"{title} ({formatted date}) has been cancelled"`, data payload `{ eventId }`. Topic `events-cancelled`, channel `events_cancelled_channel`.
- **Cancellations, digest**: title `"⚠️ {count} events cancelled"`, body `"Tap to see what changed"`.
- **Cancellation notifications are only sent for events whose start date is still upcoming** (`calendarSync.ts`'s `toCancelledEventInfo`) - nobody needs a push about something that already happened. This filter doesn't apply to new-event notifications.
- Cancellation detection happens in two separate code paths (an explicit `status: "cancelled"` in the incremental fetch, and "missing from the calendar" in the nightly full-reconciliation diff) - both now feed the same `notifyCancelledEvents()`, using the event's own stored title from Firestore (`getEvent()`) rather than trusting Calendar's often-sparse cancelled-event payload for the notification's wording.
- Send via `firebase-admin` messaging `send()` targeting the relevant **topic** — per-device muting is handled entirely client-side via topic subscribe/unsubscribe (US3.3), not by any server-side per-user logic.

## Testing
- Unit-test the diff/mapping logic as pure functions with fixture Calendar API responses — no live network needed. Good first real test coverage for this repo's backend (the Android app itself has almost none today per `CLAUDE.md`).
- Manual integration test: invoke the deployed function via the Cloud Console "Test function" button or `gcloud pubsub topics publish`, then inspect the `events` collection in the Firestore console.

## Open items
- Real category legend values (placeholder in place, safe to ship without it).
- Exact `firebase-messaging`-side payload shape should be double-checked against whatever `04-android-notifications.md` ends up parsing in `onMessageReceived` — keep the two in sync if either changes.
