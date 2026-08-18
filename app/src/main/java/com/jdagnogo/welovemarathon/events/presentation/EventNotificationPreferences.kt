package com.jdagnogo.welovemarathon.events.presentation

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import com.google.firebase.messaging.FirebaseMessaging
import com.jdagnogo.welovemarathon.common.WLMApp
import kotlinx.coroutines.tasks.await

/**
 * Local (device-only) notification preferences plus the FCM topic
 * subscriptions they control. Centralized here because two different call
 * sites need to stay in sync: the settings screen (explicit toggles) and
 * EventsScreen's first-visit handler (makes sure a fresh install - which
 * defaults to "on" here but starts with no real subscription anywhere,
 * since FCM subscription is a side effect that has to actually run at least
 * once, not just a SharedPreferences default - ends up actually subscribed).
 */
object EventNotificationPreferences {
    private const val PREFS_NAME = "events_notification_prefs"
    private const val KEY_NEW_EVENTS_ENABLED = "new_events_enabled"
    private const val KEY_CANCELLATIONS_ENABLED = "cancellations_enabled"

    fun isNewEventsEnabled(context: Context): Boolean =
        prefs(context).getBoolean(KEY_NEW_EVENTS_ENABLED, true)

    fun isCancellationsEnabled(context: Context): Boolean =
        prefs(context).getBoolean(KEY_CANCELLATIONS_ENABLED, true)

    fun setNewEventsEnabled(context: Context, enabled: Boolean) {
        prefs(context).edit().putBoolean(KEY_NEW_EVENTS_ENABLED, enabled).apply()
    }

    fun setCancellationsEnabled(context: Context, enabled: Boolean) {
        prefs(context).edit().putBoolean(KEY_CANCELLATIONS_ENABLED, enabled).apply()
    }

    /** Makes the device's real FCM topic subscriptions match the persisted
     * preferences. Cheap to call repeatedly (subscribing to a topic you're
     * already subscribed to is a no-op on Firebase's side), so both the
     * settings screen and EventsScreen's first-visit handler call this
     * rather than only reacting to explicit toggle changes. */
    suspend fun syncSubscriptions(context: Context) {
        val messaging = FirebaseMessaging.getInstance()
        runCatching {
            if (isNewEventsEnabled(context)) {
                messaging.subscribeToTopic(WLMApp.EVENTS_NEW_FCM_TOPIC).await()
            } else {
                messaging.unsubscribeFromTopic(WLMApp.EVENTS_NEW_FCM_TOPIC).await()
            }
        }
        runCatching {
            if (isCancellationsEnabled(context)) {
                messaging.subscribeToTopic(WLMApp.EVENTS_CANCELLED_FCM_TOPIC).await()
            } else {
                messaging.unsubscribeFromTopic(WLMApp.EVENTS_CANCELLED_FCM_TOPIC).await()
            }
        }
    }

    /** True when we should launch the system permission dialog: Android 13+
     * and not already granted. Shared by EventsScreen's first-visit handler
     * and the settings screen's toggle, so the same rule applies everywhere
     * we might reasonably ask. */
    fun shouldRequestNotificationPermission(context: Context): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) return false
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.POST_NOTIFICATIONS,
        ) != PackageManager.PERMISSION_GRANTED
    }

    private fun prefs(context: Context) =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
}
