package com.jdagnogo.welovemarathon.common.notifications

import android.os.Bundle
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

private const val EXTRA_EVENT_ID = "eventId"
private const val EXTRA_SCREEN = "screen"
private const val SCREEN_EVENTS = "events"

sealed class NotificationTarget {
    data class EventDetail(val eventId: String) : NotificationTarget()
    object EventsList : NotificationTarget()
}

/**
 * Bridges an FCM notification tap to the Compose nav graph. MainActivity reads the
 * tapped notification's data payload straight off the launching Intent's extras (no
 * custom FirebaseMessagingService is needed for that - the system adds the "data"
 * payload as intent extras itself), but the NavController that actually owns the
 * Events/EventDetail routes lives several composables below MainActivity, inside
 * MenuContent. This holds the pending target in between.
 */
object PendingNotificationNavigation {
    private val _target = MutableStateFlow<NotificationTarget?>(null)
    val target: StateFlow<NotificationTarget?> = _target

    fun setFromExtras(extras: Bundle?) {
        extras ?: return
        val eventId = extras.getString(EXTRA_EVENT_ID)
        _target.value = when {
            !eventId.isNullOrEmpty() -> NotificationTarget.EventDetail(eventId)
            extras.getString(EXTRA_SCREEN) == SCREEN_EVENTS -> NotificationTarget.EventsList
            else -> return
        }
    }

    fun consume() {
        _target.value = null
    }
}
