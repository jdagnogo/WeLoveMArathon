# Events: Backlog / Future Ideas

Not part of the current events-calendar-sync build (`00-overview.md` through `05-android-navigation.md`).
This is a running list of ideas raised after that feature shipped — for scoping later, not committed work.

## Per-event "Remind me" alert

Opt-in reminder for a specific event, separate from the existing broadcast push notifications
(`functions/src/notify.ts` sends topic-wide "new event" / "cancelled event" messages to everyone —
this would be per-user, per-event, so it doesn't fit that model).

- **Do it entirely on-device** — no backend change needed. A per-user/per-event schedule would
  otherwise require tracking FCM tokens per event and a Cloud Scheduler job, which is real cost on
  the free Firebase tier this app runs on.
- **UI**: bell/toggle on `EventListItem` and `EventDetailScreen`.
- **Storage**: new Room table mirroring `common/like/data/FavoriteEntity.kt` — `eventId -> reminder offset`.
  Open question: is this the same action as favoriting an event (events aren't wired into the existing
  `common/like` Fav system today), or a separate concept?
- **Scheduling**: prefer `WorkManager` (survives reboot, no special permission, ~1 min slop is fine for
  this use case) over `AlarmManager.setExactAndAllowWhileIdle` (exact, but needs `SCHEDULE_EXACT_ALARM`
  on API 31+ and needs manual reschedule-on-boot handling).
- **Must handle**: cancel/reschedule if a later sync shifts the event's `startAt`; cancel outright if the
  event is cancelled; cap total scheduled work requests to something sane.
- **MVI**: `EventsUiEvent.OnReminderToggled(eventId)` -> new `PartialState` -> reducer tracks a
  `remindedEventIds` set in `EventsState` -> ViewModel side-effect schedules/cancels the actual
  `WorkManager` request (side effects don't belong in the reducer itself, per the MVI pattern in
  `CLAUDE.md`).

## Jump to date / calendar strip

The agenda list spans a 90-day window (`PAST_WINDOW_DAYS` in `EventDateUtils.kt`) and only auto-scrolls
to today on open. There's no way to jump to, say, next Saturday without scrolling through everything
in between. A small date-picker or horizontal day strip pinned above the list would fix that.

## "Ending soon" indicator

`relativeStartLabel()` (`EventDateUtils.kt`) already covers "Happening now" and "Starts in Xh/m", but
nothing flags an event that's about to *end* (e.g. last 15 minutes) — matters most for things like a
market stall or activity where "still time to catch it" is the useful signal, not just "it started."

## Category filter chips on the Events list

`EventsPartialState.OnCategorySelected` and `EventsState.selectedCategory` already exist in the reducer
(`EventsReducer.kt`) — need to confirm whether this is actually surfaced as chips in
`EventsContent.kt`/`EventsScreen.kt` or just wired and unused. If unused, likely a quick win since the
state plumbing is already done.

## Empty-day state

Days with zero events currently just don't get a header (confirmed behavior: `groupEventsByDay` only
emits days that have events). Worth double-checking this reads as "nothing scheduled" rather than
looking like the data failed to load, especially for a day sandwiched between busy ones.
