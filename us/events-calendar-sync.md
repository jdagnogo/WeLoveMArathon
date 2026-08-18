# Feature: Google Calendar Events → In-App Notifications

## Context

The app owner (non-technical) wants a way to publicize local events without learning a new tool. She already keeps events in her personal Google Calendar app. We want any event she adds/edits/deletes there to automatically show up in the WeLoveMArathon app and push a notification to every installed user, with no login required on the user side.

This document defines **what to build** (User Stories with acceptance criteria) and the **product/architecture decisions already made**, based on:
- The architecture summary the user got from another agent (service account + Calendar API + Firestore + FCM).
- Research into this codebase (see "Reused patterns" below) confirming there is currently **no** push-notification infrastructure, **no** calendar/date-picker UI, and **no** Cloud Functions/Firebase Functions setup in this repo — this is net-new work end to end.
- Iterative answers the user gave on display style, sync strategy/cost, navigation placement, notification control, event fields, search/categories, and cancellation handling (see "Decisions" below). A UI mockup ("ChronosSync", built with Stitch) was used as inspiration for search and category-pill ideas — its live-sync badge and 3-tab nav were **not** adopted since they don't match this app's chosen architecture (scheduled sync, existing Home/Events/About nav).

This is **not** a code implementation plan — no code has been written from this document. It's the requirements deliverable to align on before a separate technical implementation plan is scoped.

## Decisions confirmed with the user

1. **Display style: Agenda / list view**, grouped by day (Today, Tomorrow, Aug 2...) — not a month-grid calendar widget.
2. **Sync strategy: scheduled polling, not a real-time webhook.** A Cloud Function, triggered by Cloud Scheduler **every 15 minutes**, asks the Calendar API "what changed since the last check" (`updatedMin` filter) and only touches Firestore when something actually changed. Once a **night**, the same job instead does a **full re-pull and diff** of the whole sync window as a reconciliation safety net (self-healing, and the very first run doubles as the initial backfill).
3. **Sync window: forward-looking 3 months.** Bounded, so cost doesn't grow with total calendar history.
4. **Notifications fire only on brand-new events**, not edits/cancellations. Multiple new events found in one check are batched into a single digest notification. **The very first-ever sync run (initial backfill) never sends notifications** — it just populates Firestore silently, since nothing in it is actually "new" from a user's perspective.
5. **Cancelled events are shown, not silently removed.** When the owner cancels/deletes an event that was already synced, it's marked `cancelled` rather than deleted, and stays visible in the agenda with a "Cancelled" badge/strikethrough until its original date passes — then it's cleaned up like any other past event.
6. **Data retention: 3 months in the past.** Once an event's date is more than 3 months old, it's auto-deleted from Firestore via a native TTL policy — no manual cleanup needed.
7. **Firestore security rules**: the `events` collection is public **read-only** for clients — all writes come from the Cloud Function (Admin SDK, bypasses rules) only.
8. **Navigation: replace the "Offers" bottom-nav tab with "Events."** The `offer` feature code, screens, and DI module are **not deleted** — only the bottom-nav entry is repointed. Need to verify no other in-app entry point (e.g. a banner CTA) still deep-links to Offers.
9. **Notification permission & settings**: the Android notification-permission prompt is triggered the first time the user opens the Events tab (not on cold start). A settings icon at the top of the Events screen opens a "notification channels" screen where users can mute/unmute event notifications specifically — structured to support more channels later without a redesign.
10. **Hosting: Cloud Scheduler + Cloud Function** on the Firebase project (`welovemarathon-71ff6`), which is **already on the Blaze plan** — no billing setup blocker.
11. **Local client architecture: Room-backed local cache**, matching the pattern every other feature in this app already uses (see `CLAUDE.md`). On opening the app / the Events tab, the client pulls whatever changed in Firestore since last time into Room — no live Firestore listener, and no manual pull-to-refresh needed. Search and category filtering run entirely against this local data, not against the network.
12. **Search**: a search bar filters the local agenda by keyword across title, description, and location — instant, no network call.
13. **Categories are sourced from the event's Google Calendar color.** Google Calendar has no native "tag" field, so the owner assigns one of Calendar's ~11 preset event colors per event, and sync maps `colorId` → a fixed category name (e.g. red = Sports, green = Food) we define together with her. Category filtering runs locally, like search. Events with no/unmapped color are shown uncategorized. **The owner will need a one-time color→category legend/cheat sheet** to follow when creating events.
14. **Skipping for v1**: the "View in Google Calendar" link (`htmlLink` field) — cheap to add later if wanted, not needed now.
15. **The owner uses a dedicated, separate Google Calendar for public events** (e.g. "Marathon Events – Public"), not her main personal calendar. Only that dedicated calendar is shared with the service account — keeps anything personal she schedules elsewhere from ever being exposed, even accidentally.
16. **Recurring events (Calendar's "Repeat" feature) are out of scope for v1.** The sync will not expand a recurring series into multiple occurrences — if the owner sets an event to repeat, it will sync only as a single occurrence (its first upcoming instance) and won't reappear on later dates in the app. Until this is built, if she wants something to show up on multiple dates (e.g. a weekly market), she should create a separate one-off event each time rather than using Calendar's repeat button.
17. **Multi-day events appear under every day they span** in the agenda (e.g. a 3-day festival shows up whenever a user checks any of those 3 days), not just under its start day.
18. **UI language: English only.** Applies to the app's own chrome (labels, badges, notification copy, channel name) — separate from event *content*, which is already shown verbatim as the owner typed it (Decision content assumption, unaffected).
19. **No analytics tracking for this feature in v1** — considered and declined, not a gap; see "Deferred" section.
20. **Event detail screen includes an "Add to my calendar" action** that hands off to the user's own calendar app via Android's standard insert intent — no extra permission needed, no server involvement.

- `tips/presentation/` (`TipsScreen.kt`, `TipsContent.kt`, `TipsDialogComponent.kt`, `TipsReducer.kt`, `TipsViewModel.kt`) — closest existing list + modal-detail MVI template; a new `events` feature should mirror this shape (list screen, `EventUiEvent`/`EventPartialState`/`EventReducer`/`EventState` per the `IModel`/`IReducer` pattern documented in `CLAUDE.md`).
- `common/category/RecommendedCategoryDetailsDialogComponent.kt` — reusable shape for an event-detail popup (swap the image carousel for date/time/location info).
- `common/ui/component/NavGrah.kt` (`HomeSections` enum) + `BottomNavItem.kt` — currently defines `HOME`, `OFFERS`, `ABOUT`. Repointing the Offers entry to Events happens here.
- `common/utils/SingleSourceOfTruthStrategy.kt` / Room — **confirmed** as the pattern Events will follow, same as every other feature: a local Room table is the client's source of truth, refreshed from Firestore on open rather than via a live snapshot listener or a fixed time-based freshness window (the "freshness" here is naturally driven by the backend's own 15-min/nightly sync into Firestore, and the client just pulls the latest Firestore state into Room each time the Events tab is opened).
- No existing FCM, `FirebaseMessagingService`, notification channel, `POST_NOTIFICATIONS` permission handling, or Cloud Functions/`firebase.json` exist anywhere in the repo — all of this is new.
- `app/google-services.json` confirms the Firebase project is `welovemarathon-71ff6`; Cloud Messaging is not yet enabled on it.

## Epics & User Stories

### Epic 1 — Calendar → Firestore sync (backend, invisible to users)
- **US1.1**: As the system, every 15 minutes, a Cloud Function checks the Google Calendar (via the shared service account) for anything modified since the last check and updates Firestore to match, including deriving each event's category from its Calendar color.
- **US1.2**: As the system, once a night the same job does a full re-pull and diff of the entire 3-month sync window, so any drift or missed change from the frequent checks is automatically corrected.
- **US1.3**: As the system, if a sync attempt fails (API error, malformed/incomplete event), it logs the failure and retries on the next scheduled run without corrupting existing data — and a single malformed event within an otherwise-successful batch doesn't block processing the rest of that batch.
- **US1.4**: As the system, only events on the owner's dedicated public-events calendar are synced — her personal/private calendar is never shared with the service account and is never touched.
- **US1.5**: As the system, events more than 3 months in the past are automatically removed from Firestore via a TTL policy.
- **US1.6**: As the system, when a previously-synced event disappears from Google Calendar (deleted or cancelled), it's marked `cancelled` in Firestore rather than deleted outright, so it can still be shown to users who'd already seen it.
- **US1.7**: As the system, the timestamp used to ask Google "what changed since last check" is stored durably and only advances after a run completes successfully — so a failed run can't cause a permanently missed change.
- **US1.8**: As the system, the very first sync run populates Firestore without sending any notifications.

### Epic 2 — Browsing events (in-app)
- **US2.1**: As a user, I open the "Events" tab (replacing "Offers" in the bottom nav) and see upcoming events in a chronological list grouped by day, soonest first. A multi-day event (e.g. a 3-day festival) appears repeated under every day it's active, not just its start day, so it shows up whenever I check "today."
- **US2.2**: As a user, I tap an event to see its full details — title, description, date/time, and location (shown only when present) — in a detail view/dialog consistent with how Tips does it.
- **US2.3**: As a user, if there are no upcoming events, I see a friendly empty state instead of a blank screen.
- **US2.4**: As a user without an internet connection, I still see the last-synced events list from the local Room cache, so the tab isn't blank when offline.
- **US2.5**: As a user, past events drop off the list automatically once their date has passed, and are fully deleted after 3 months.
- **US2.6**: As a user, I can search the agenda by keyword (title, description, or location) using a search bar, with results filtered instantly from local data.
- **US2.7**: As a user, I can filter the agenda by category (e.g. Sports, Food), filtered instantly from local data.
- **US2.8**: As a user, a cancelled event I'd previously seen stays visible with a clear "Cancelled" badge/strikethrough instead of silently disappearing, until its original date passes.
- **US2.9**: As a user, on an event's detail screen I can tap "Add to my calendar" to save it into my own phone's calendar app, pre-filled with title, description, location, and date/time. This uses Android's standard calendar-insert intent (`ACTION_INSERT` on `CalendarContract.Events`), so it requires no extra app permission — it hands off to whatever calendar app is already on the device. If no calendar app is installed (rare), the button degrades gracefully instead of crashing.

### Epic 3 — Notifications
- **US3.1**: As a user who hasn't muted event notifications, I get a push notification when a new event is added to the calendar — within about 15 minutes of the owner saving it.
- **US3.2**: As a user, tapping an event notification opens the app directly to that event's detail when the notification covers exactly one new event, or the Events list when it's a digest covering several.
- **US3.3**: As a user, I can open a "notification channels" settings screen from an icon at the top of the Events screen and mute/unmute event notifications specifically. The Android notification channel is named **"Local Events"** with the description "Get notified when new events are added to the calendar," so it reads clearly in system settings too.
- **US3.4**: As a first-time user, I'm asked for notification permission the first time I open the Events tab (Android 13+ requirement; implicit on older versions). Denying it degrades gracefully — the Events list still works, just without push.
- **US3.5 (resolved)**: Edits and cancellations do **not** trigger a notification in v1 — only brand-new events do (cancellations are instead shown via US2.8/US1.6).
- **US3.6**: Notification copy is warm/friendly rather than plain system text:
  - Single new event — title: *"🎉 New event in Marathon!"*, body: *"{event title} — {date}"*.
  - Digest (multiple new events found in one check) — title: *"🎉 {count} new events in Marathon!"*, body: *"Tap to see what's new"*.
  (Exact wording can be refined later — this is the working default so the feature isn't blocked on final copy.)

### Epic 4 — Navigation change
- **US4.1**: As a user, the bottom nav shows Home / Events / About — Offers is removed from the bar, but its code/screens remain in the repo untouched for potential future reuse.
- **US4.2**: As a maintainer, before removing the Offers tab, we verify there's no other in-app entry point that still deep-links to Offers.

### Epic 5 — Data & security
- **US5.1**: As a maintainer, the `events` Firestore collection is readable by anyone (no login required) but writable only by the backend sync job — enforced explicitly via Firestore security rules.

### Epic 6 — Owner experience
- **US6.1**: As the owner, I keep using my normal Google Calendar app exactly as I do today — the only one-time setup is sharing that calendar with a service account email, and learning a simple color→category legend (e.g. red = Sports) if I want events to be filterable by category. No new tool to learn, no ongoing maintenance.
- **US6.2**: As the owner, until recurring-event support is built, if I want something to appear on more than one date (e.g. a weekly market), I create a separate event for each date rather than using Calendar's repeat feature — otherwise only the first date will show up in the app.

## Deferred / intentionally out of scope for v1

- **Monitoring/alerting for silent sync failures** — explicitly deferred. If the sync job breaks (e.g. calendar access revoked), nobody will be automatically notified in v1. Worth revisiting once the feature has real usage.
- **Full "Alerts/Activity" feed screen** (à la the ChronosSync mockup's second screen) — considered, not chosen; cancellations are instead shown in place (US2.8) rather than via a separate history screen.
- **"View in Google Calendar" link** (`htmlLink`) — cheap to add later, skipped for now.
- **Photo/attachment support, event-color-as-anything-other-than-category** — not pursued; adds Drive API complexity disproportionate to the need.
- **Recurring event expansion** — a recurring series syncs only as its first occurrence for now; full repeat/multi-instance support deferred until there's real demand for it.
- **Analytics on Events usage** (event views, notification taps, etc.) — the app already has Firebase Analytics wired up and this would be cheap to add, but explicitly declined for v1.

## Cost & feasibility notes

- **Cloud Functions require the Blaze (pay-as-you-go) plan** — confirmed the project is already on it, so no billing blocker.
- **Why 15-minute polling is still effectively free:**
  - **Calendar API**: default quota is 1,000,000 queries/day; checking every 15 min is 96 calls/day — negligible.
  - **Cloud Scheduler**: free tier covers 3 scheduled jobs per project regardless of how often each fires; we only need 1.
  - **Cloud Functions invocations**: 2,000,000 free/month; 96 runs/day (~2,900/month) is a tiny fraction.
  - **Firestore reads**: the potential cost driver, but only if every run re-reads the whole 3-month window. Because each 15-minute run first asks Google "what changed since last time" and only touches Firestore when the answer is "something," the vast majority of the 96 daily runs cost 1 cheap Calendar API call and 0 Firestore reads. The one nightly full-window reconciliation run is the only one that reads the whole window.
- **Worked example sized to reported real-world volume**: the owner estimates ~10 events/week. Using **20 events/week (2x, as a safety margin)** and a 3-month (~13-week) sync window:

  | Service | Usage at 20 events/week | Free tier | Headroom |
  |---|---|---|---|
  | Calendar API calls | ~98/day (96 cheap 15-min checks + 1-2 nightly full pulls) | 1,000,000/day | ~0.01% used |
  | Cloud Scheduler | 1 job (frequency doesn't affect the free tier) | 3 jobs/project | trivial |
  | Cloud Function invocations | ~2,940/month | 2,000,000/month | ~0.15% used |
  | Firestore writes | ~3-10/day (new/changed events, generous burst allowance) | 20,000/day | <0.1% used |
  | Firestore deletes | ~3-4/day (TTL cleanup + cancellations) | 20,000/day | <0.1% used |
  | Firestore reads | ~260/day (dominated by the one nightly full-window pass; frequent 15-min checks add only a handful of targeted reads when something changed) | 50,000/day | ~0.5% used |

  The only number that scales with volume is the nightly reconciliation's Firestore reads. Working backward, the owner would need to post roughly **~3,800 events/week** before that alone would approach the free daily read ceiling — ~190x headroom over the doubled estimate.
- **Search, category filtering, and the local Room cache add no additional backend cost** — they operate entirely on data already synced to the device.
- **Category derivation from `colorId` adds no extra API calls** — the color is already part of the event data fetched during normal sync.
- **FCM (push notifications) is free** at any scale.
- **No webhook, so no domain-verification risk and no watch-channel renewal job.**

## Manual setup checklist (prerequisites — accounts/permissions, not app code)

These are one-time administrative tasks that need to happen outside the codebase before/alongside implementation. None of this is code.

**Google Cloud Console (one-time, before backend work starts)**
1. Enable the Google Calendar API on the project backing `welovemarathon-71ff6`.
2. Create a dedicated service account for this sync job (e.g. `calendar-sync@welovemarathon-71ff6.iam.gserviceaccount.com`) — its email is what the owner shares her calendar with.
3. Once the Cloud Function exists, create the Cloud Scheduler job(s) (15-min cadence, plus however the nightly full-reconciliation run is triggered) and grant Cloud Scheduler's service account permission to invoke the function (`roles/cloudfunctions.invoker` or `roles/run.invoker`).

**Firebase Console (one-time)**
4. Confirm/enable Cloud Messaging (FCM) for the project — `google-services.json` currently has no `cloud_messaging` section, so this likely needs enabling, followed by re-downloading `google-services.json` and replacing it in all 3 places it exists in this repo (`app/`, `app/src/main/`, `app/src/debug/`).
5. Deploy Firestore security rules for the `events` collection (public read, server-only write).
6. Configure a Firestore TTL policy on the `events` collection's expiry field (Console or CLI) — note deletion isn't instant, Firestore documents this as "usually within 24 hours" of expiry, not immediate.
7. Blaze plan — already confirmed active, nothing to do here.

**Owner-side (one-time, a human task for her, not us)**
8. **Confirmed**: she creates a new, dedicated Google Calendar (e.g. "Marathon Events – Public") rather than sharing her main personal calendar — keeps anything personal she schedules from ever being exposed, even accidentally.
9. Share that dedicated calendar with the service account's email address, with "See all event details" permission (read-only access is sufficient — we never write to her calendar).
10. Receive and start using the one-page color→category legend (e.g. red = Sports, green = Food) — this needs to be produced and handed to her before category filtering is meaningful.
11. Be told: don't use Calendar's "Repeat" button for now — create a separate one-off event per date instead, until recurring support is built (Decision 15).

**Android app / release (per-release, normal existing process)**
12. Normal version bump (`versionCode`/`versionName` in `app/build.gradle`) when this ships — no special process beyond what's already used for every release.
13. No Play Console-side action needed specifically for this feature — Android's notification-permission prompt is handled entirely at the OS/app level.

**Validation before go-live (manual, one-time)**
14. Manually verify the service account can actually read the shared calendar (a quick isolated API call) before wiring up the full pipeline — catches sharing/permission mistakes early.
15. End-to-end manual test once built: add a test event to the calendar → confirm it lands in Firestore → confirm a test device receives the push → confirm tapping it opens the right screen.

## Open questions / assumptions still to confirm before implementation

None remaining — the last open item (recurring events) was resolved: out of scope for v1 (see Decision 15).

## Out of scope for this document

Concrete data models, Cloud Function code, Firestore security rules text, the color→category legend itself, and the Compose screen/file-level implementation plan — these belong in a follow-up technical implementation plan once these User Stories are approved.
