# Android: `events` Feature Module

## Goal
The new `events` Kotlin/Compose feature module — the agenda list, search, category filter, detail screen, and "add to my calendar" action. Implements Epic 2 (US2.1–US2.9) from `us/events-calendar-sync.md`, following this app's existing feature-module + MVI pattern documented in `CLAUDE.md`.

## Depends on
Schema in `00-overview.md`. Does **not** need `01-backend-cloud-function.md` finished — hand-seed a few Firestore documents matching the schema to build and test against, then swap to the real backend later.

## File ownership
Everything under `app/src/main/java/com/jdagnogo/welovemarathon/events/**` is new — no conflicts with other work. This doc also modifies two **shared** files (coordinate if another agent touches these concurrently):
- `common/data/WLMDatabase.kt` — register the new Entity + DAO, bump the Room `version`.
- `app/build.gradle` — add core library desugaring (needed for `java.time` on `minSdk 21`).

## Reused patterns (see `CLAUDE.md`)
- `tips/presentation/` — closest existing list + modal-detail MVI template.
- `common/category/RecommendedCategoryDetailsDialogComponent.kt` — shape for the event-detail popup.
- `food/data/FoodMapper.kt`, `food/di/FoodModule.kt` — shape for the mapper and DI module.
- **Deliberately not reused**: `common/utils/SingleSourceOfTruthStrategy.kt` (`resourceAsFlow`). That pattern is a time-based freshness window ("don't refetch for N minutes"). Events instead refreshes from Firestore every time the Events tab opens (Decision 11 — no manual refresh needed because it's automatic-on-open, not time-gated), which is a different enough shape to not force through the existing helper.

## Data layer (`events/data/`)
- **`EventEntity.kt`** — Room entity mirroring the Firestore schema in `00-overview.md`. Use `Long` epoch millis for `startAt`/`endAt`/`expireAt`/`calendarUpdatedAt` (simpler Room storage than a `Timestamp` type), `String` for the rest, `Boolean` for `isAllDay`, nullable `String?` for `category`/`colorId`.
- **`EventDao.kt`** — `getAll(): Flow<List<EventEntity>>`, `upsertAll(events: List<EventEntity>)`, `replaceAll(events: List<EventEntity>)` (delete-and-reinsert or diff-upsert, implementer's choice), `deleteExpired(cutoffMillis: Long)` (belt-and-suspenders local cleanup mirroring the server TTL, in case a device hasn't opened the app in a while). Recommend doing search/category filtering **in memory** over the collected `Flow<List<EventEntity>>` rather than SQL queries — the dataset is small (a few hundred docs at most, per the cost analysis in the US doc), so this keeps the DAO simple.
- **`EventFirestoreSource.kt`** — thin wrapper: `suspend fun fetchAll(): List<EventDto>`, reading the whole `events` collection. This is a full read, not an incremental client-side diff — deliberately simple, since the collection is small enough that a full read is cheap (see cost analysis in the US doc) and it avoids needing any client-side sync-token bookkeeping.
- **`EventMapper.kt`** — Firestore DTO ↔ Room Entity ↔ domain model, shaped like `food/data/FoodMapper.kt`.
- **`EventRepository.kt`** (interface) + **`EventRepositoryImpl`**:
  ```kotlin
  interface EventRepository {
      val events: Flow<List<Event>>   // sourced from Room — always available offline
      suspend fun refresh()           // pulls Firestore -> replaces Room
  }
  ```

## Domain layer (`events/domain/`)
- **`Event.kt`** — domain model.
- **`GetEventsUseCase.kt`** — exposes `repository.events`.
- **`RefreshEventsUseCase.kt`** — calls `repository.refresh()`.
- "Add to my calendar" is a UI-layer Android `Intent` concern, not business logic — build it directly where the button is (see below) rather than wrapping it in a use case; that matches the app's convention of keeping use cases to actual domain operations.

## DI (`events/di/EventsModule.kt`)
Mirror `food/di/FoodModule.kt` exactly: provide the DAO (from `WLMDatabase`), the Firestore source, mapper, repository, use cases, and reducer — all `@Singleton` in `SingletonComponent`.

## Presentation (`events/presentation/`) — MVI per `IModel`/`IReducer`
- **`EventsState.kt`**:
  ```kotlin
  data class EventsState(
      val events: List<EventUiModel> = emptyList(),
      val searchQuery: String = "",
      val selectedCategory: String? = null,
      val selectedEvent: EventUiModel? = null,
      val isLoading: Boolean = false,
  ) {
      // Filters by searchQuery/selectedCategory, expands multi-day events across
      // every date they span (Decision 17), then groups by day for the agenda.
      val groupedEvents: Map<LocalDate, List<EventUiModel>> get() = ...
  }
  ```
- **`EventUiEvent.kt`**: `OnSearchQueryChanged`, `OnCategorySelected`, `OnEventClicked`, `OnDetailDismissed`, `OnAddToCalendarClicked`.
- **`EventPartialState.kt`** / **`EventReducer.kt`** — standard shape, mirror `FoodReducer.kt`.
- **`EventsViewModel.kt`** — calls `refreshEventsUseCase()` in `init {}`, collects `getEventsUseCase()` into state.
- **`EventsScreen.kt`** / **`EventsContent.kt`** — stateful/stateless split per convention. Day-grouped `LazyColumn` with section headers ("Today", "Tomorrow", formatted date per US2.1), search `TextField` at top (US2.6), category filter chip row (US2.7), empty state (US2.3), renders whatever's in Room regardless of network state (US2.4).
- **`EventDetailDialogComponent.kt`** — modeled on `RecommendedCategoryDetailsDialogComponent.kt`: title, description, location (only if non-empty), date/time, category chip, "Cancelled" badge/strikethrough if `status == cancelled` (US2.8), and an **"Add to my calendar"** button (US2.9):
  ```kotlin
  val intent = Intent(Intent.ACTION_INSERT, CalendarContract.Events.CONTENT_URI).apply {
      putExtra(CalendarContract.Events.TITLE, event.title)
      putExtra(CalendarContract.Events.DESCRIPTION, event.description)
      putExtra(CalendarContract.Events.EVENT_LOCATION, event.location)
      putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, event.startAtMillis)
      putExtra(CalendarContract.EXTRA_EVENT_END_TIME, event.endAtMillis)
      putExtra(CalendarContract.Events.ALL_DAY, event.isAllDay)
  }
  runCatching { context.startActivity(intent) } // no-op / snackbar if no calendar app is installed
  ```
  No extra manifest permission needed — this uses an implicit intent to whatever calendar app is already on the device, not direct `ContentResolver` access.

## Build changes (`app/build.gradle`)
```groovy
android {
    compileOptions {
        coreLibraryDesugaringEnabled true
        // existing sourceCompatibility/targetCompatibility unchanged
    }
}
dependencies {
    coreLibraryDesugaring 'com.android.tools:desugar_jdk_libs:2.0.4' // confirm latest at implementation time
}
```

## Testing
This app has almost no unit tests today (`CLAUDE.md` notes only a default `ExampleUnitTest`). Good, cheap candidates here: `EventReducer` (pure function) and the day-grouping/multi-day-expansion logic in `EventsState.groupedEvents` (pure function, easy to test with fixture data) — both fit the existing `./gradlew testDebugUnitTest` setup with no new tooling needed.

## Open items
- Room vs. in-memory filtering is a judgment call left to the implementer; in-memory is recommended given the small dataset size.
- Exact `desugar_jdk_libs` version should be checked against current AGP/Kotlin compatibility at implementation time rather than trusting the version pinned above.
