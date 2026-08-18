# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project context

WeLoveMArathon is a native Android app (Kotlin + Jetpack Compose) built for the town of Marathon, Greece, to help promote local businesses (food, shopping, activities, sports, culture, etc.). It is a single-module app built by a solo/volunteer developer learning Compose along the way — favor straightforward, consistent-with-existing-code solutions over cleverness.

## Build & run commands

```bash
# Build debug APK
./gradlew assembleDebug

# Build release APK/AAB (uses the checked-in keystore/release signing config)
./gradlew assembleRelease
./gradlew bundleRelease

# Install debug build on a connected device/emulator
./gradlew installDebug

# Run JVM unit tests (app/src/test)
./gradlew testDebugUnitTest

# Run a single unit test class
./gradlew testDebugUnitTest --tests "com.jdagnogo.welovemarathon.ExampleUnitTest"

# Run instrumented/Compose UI tests (app/src/androidTest) — needs a connected device/emulator
./gradlew connectedDebugAndroidTest

# Lint
./gradlew lint
```

The app has almost no real unit test coverage today (`app/src/test` only has the default `ExampleUnitTest`); Compose UI tests live under `app/src/androidTest` (e.g. `ShoppingMenuContentTest`, `TitleComponentTest`). When adding logic worth testing (reducers, use cases, mappers), prefer plain JUnit unit tests in `app/src/test`.

The debug build type has applicationIdSuffix `.debug` and a 5-minute data-freshness window; release uses a 10080-minute (7-day) window (`BuildConfig.EXPIRATION_IN_MINUTES`, set in `app/build.gradle`).

## Architecture

### Feature-module layout

Code lives under `app/src/main/java/com/jdagnogo/welovemarathon/<feature>/`, one package per feature (e.g. `food`, `shopping`, `beach`, `sport`, `culture`, `activities`, `wine`, `offer`, `tips`, `blog`, `about`, `favorites`, `map`, `home`, `splash`). Each feature package is split into the same four sub-packages, following clean architecture:

- `data/` — Room entities/DAOs, Firestore remote data source, repository implementation, and a mapper between entities and domain models.
- `domain/` — plain domain models and use cases (one small class per use case, e.g. `GetFoodUseCase`).
- `di/` — a Hilt `@Module` (`@InstallIn(SingletonComponent::class)`) that wires DAO → RemoteData → Repository → UseCases → Reducer for that feature.
- `presentation/` — Compose screens plus the MVI trio: `ViewModel`, `State`, `Reducer`.

`common/` holds cross-feature building blocks: shared Compose components (`common/ui/component`), theme (`common/ui/theme`), the Room database (`common/data/WLMDatabase.kt`), favorites (`common/like`), banners (`common/banner`), category widgets (`common/category`), and shared utilities (`common/utils`).

### MVI pattern

Every feature screen follows the same MVI shape:

- `ViewModel` implements `IModel<STATE, EVENT>` (`common/utils/IModel.kt`) which exposes a single `state: StateFlow<STATE>` and one public entry point: `fun dispatchEvent(event: EVENT)`. All user interaction goes through `dispatchEvent`; the ViewModel never exposes other public methods.
- `EVENT` is a sealed class named `<Feature>UiEvent` — this plays the role of "Intent" in classic MVI (named Event to avoid clashing with Android's `Intent`).
- `PartialState` is a sealed class named `<Feature>PartialState` representing one incremental change (loading, success, error, a UI toggle, etc.).
- `Reducer` implements `IReducer<STATE, PARTIAL_STATE>` (`common/utils/IReducer.kt`) with a single `reduce(state, partialState): STATE` function — the only place `STATE` is derived from a partial state. Reducers are pure and side-effect free.
- `State` is a data class named `<Feature>State` with defaults, often exposing derived read-only properties (e.g. `subMenuUiModel`, `categoryTags` on `FoodState`) computed from the raw fields instead of stored separately.

Compose screens are split into a stateful and a stateless component, e.g. `FoodScreen` (stateful — collects `viewModel.state`, calls `dispatchEvent`) and `FoodContent` (stateless — pure function of state/callbacks, easy to preview/test). Keep all decision-making in the ViewModel/Reducer; Compose content functions should just render state and forward events.

### Data layer: SingleSourceOfTruthStrategy

Repositories don't fetch on demand; each `FoodData`/`XxxData`-style class exposes `StateFlow<Resource<...>>` populated in `init {}` via `resourceAsFlow` (`common/utils/SingleSourceOfTruthStrategy.kt`):

1. Emit `Resource.Loading`.
2. Check `DataFreshnessUseCase.isDataFresh(DataType.X)` (backed by `DataFreshnessRepository` / Room, per-`DataType` expiry timestamps).
3. If fresh and not force-fetching, just stream from Room (`fetchFromLocal`) mapped into `Resource.Success`.
4. Otherwise call Firestore (`networkCall`), persist the result via `saveCallResource`, then stream the freshly-saved local data. On network error, fall back to whatever is already in Room, wrapped as `Resource.Error` (so the UI still has data to show).

This means: to add a new fetchable list to a feature, add a `DataType` entry, wire a DAO + remote data source, and call `resourceAsFlow` with the four lambdas — don't hand-roll fetch/cache logic.

`Resource<T>` (`common/utils/Resource.kt`) is the sealed wrapper (`Loading` / `Success` / `Error` / `GenericError.NetworkError` / `GenericError.HttpError`) used everywhere data flows from repository → use case → ViewModel. `handleResource` / `handleResourceWithFav` are helper collectors that turn a `Flow<Resource<List<Entity>>>` into partial states and push them into the reducer in one call — use these instead of writing a new `onEach { }` per screen when the shape fits.

### Dependency injection

Hilt throughout. `WLMApp` is `@HiltAndroidApp`. Each feature's `di/<Feature>Module.kt` provides its DAO (from `WLMDatabase`), mapper, remote data source, repository, use cases, and reducer as `@Singleton`s in `SingletonComponent`. When adding a new feature, mirror an existing module (e.g. `food/di/FoodModule.kt`) rather than inventing a new DI shape.

### Firebase backend

Firestore is the remote data source (see `common/utils/FireStoreUtils.kt` and each feature's `XxxRemoteData.kt`); Firebase Analytics and Crashlytics are also wired in (`app/build.gradle`, `app/google-services.json`). The free Firebase tier is a real constraint — this is why data is cached locally in Room and only refetched per the freshness window rather than on every screen visit.

## Notes

- `keystore/welovemarathon_keystore.jks` is checked into the repo and used for both debug and release signing configs (`app/build.gradle`) — this is intentional for this project, not an oversight to "fix".
- `screens/` contains README screenshots, not app resources.
