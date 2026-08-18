# Android: FCM Notifications

## Goal
Client-side push notification integration: receiving, displaying, permission handling, muting, and tap-to-navigate. Implements Epic 3 (US3.1–US3.6) from `us/events-calendar-sync.md`.

## Depends on
`03-android-events-feature.md` — needs `EventsScreen` to exist as the permission-prompt trigger point and the deep-link navigation target. Nothing else in this app has FCM today (`CLAUDE.md` confirms no `FirebaseMessagingService`, no notification channel, no `POST_NOTIFICATIONS` handling exists anywhere in the repo) — this is entirely new infrastructure.

## File ownership
New `common/notifications/` package (`FirebaseMessagingService`, permission helper, topic-subscription helper). Also modifies these **shared** files (coordinate with `05` if worked concurrently — `05` touches nav files, not these):
- `app/build.gradle` — add `firebase-messaging` dependency.
- `AndroidManifest.xml` — register the service; add `POST_NOTIFICATIONS` (normal runtime permission, no special manifest flag beyond declaring it).
- `common/ui/MainActivity.kt` — handle notification-tap intent extras (`onNewIntent` + cold-start `onCreate` case).
- `common/WLMApp.kt` — create the notification channel on app startup.

## Steps

### 1. Dependency
```groovy
implementation 'com.google.firebase:firebase-messaging-ktx:24.0.0' // verify against current firebase-bom at implementation time — the existing bom (29.0.0 in app/build.gradle) is from 2021 and likely needs bumping alongside this
```

### 2. Notification channel (`WLMApp.kt`, gated to API 26+)
```kotlin
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
    val channel = NotificationChannel(
        "events_channel",
        "Local Events",
        NotificationManager.IMPORTANCE_DEFAULT
    ).apply { description = "Get notified when new events are added to the calendar." }
    getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
}
```
Matches the channel id/name/description fixed in `00-overview.md`/US3.3.

### 3. `EventsMessagingService : FirebaseMessagingService()`
- `onMessageReceived(message)`: build a `NotificationCompat.Builder` using `events_channel`, with title/body taken directly from the payload composed server-side in `01-backend-cloud-function.md`'s `notify.ts` (don't re-derive copy client-side — the server already decides single-vs-digest wording). Attach a `PendingIntent` targeting `MainActivity` carrying the `eventId` extra if present in the message's data payload, or no extra for a digest.
- `onNewToken`: no-op beyond the default — this app uses **topic** messaging (`events`), not per-device token targeting, so there's no server-side token registry to update.

### 4. Permission flow (triggered from `EventsScreen`, US3.4)
- On `EventsScreen`'s first composition, check a "have we asked before" flag (DataStore/SharedPreferences).
- If not asked yet and API ≥ 33: launch `ActivityResultContracts.RequestPermission()` for `Manifest.permission.POST_NOTIFICATIONS`.
- Regardless of API level, once permission is granted (or is implicit pre-33): call `FirebaseMessaging.getInstance().subscribeToTopic("events")`.
- If denied: do nothing further — the Events list still works fully without push (graceful degradation, US3.4).

### 5. Mute toggle / notification settings screen (US3.3)
- Small settings screen reachable from an icon at the top of `EventsScreen`, one toggle bound to a `Boolean` in DataStore.
- Toggled **off** → `FirebaseMessaging.getInstance().unsubscribeFromTopic("events")`.
- Toggled **on** → `subscribeToTopic("events")`; if the OS permission was previously denied, this is where re-requesting matters — if permanently denied, deep-link to the app's system notification settings screen (`Settings.ACTION_APP_NOTIFICATION_SETTINGS`) rather than silently failing, since Android won't show the permission dialog again after a hard denial.
- Structure this screen as a list (even with one item today) so more channels can be added later without a redesign, per Decision 9.

### 6. Deep-link handling (`MainActivity.kt`)
- `onNewIntent(intent)` (running app tapped from background) and the `onCreate` cold-start case (app launched fresh from a notification tap) both check for an `eventId` string extra.
- If present → set a one-shot navigation event (e.g. a `SharedFlow` the `NavGraph`/`EventsViewModel` observes) that opens that event's detail dialog once `EventsScreen` is composed.
- If absent (digest notification) → just navigate to the Events tab.

## Testing
Manual: send a test message via Firebase Console's Cloud Messaging test panel to topic `events`, confirm it arrives on a device, confirm tapping it opens the right screen (single-event detail vs. Events list depending on payload).

## Open items
- Exact `firebase-messaging` + `firebase-bom` versions need checking against current AGP/Kotlin compatibility at implementation time — don't trust the version numbers above without verifying.
- Confirm the data-payload key name (`eventId`) matches exactly what `01-backend-cloud-function.md`'s `notify.ts` sends — keep both files in sync if either changes.
