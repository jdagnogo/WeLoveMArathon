package com.jdagnogo.welovemarathon.common

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WLMApp : Application() {

    override fun onCreate() {
        super.onCreate()
        createEventsNotificationChannels()
    }

    private fun createEventsNotificationChannels() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return

        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(
            NotificationChannel(
                EVENTS_NEW_NOTIFICATION_CHANNEL_ID,
                "Local Events",
                NotificationManager.IMPORTANCE_DEFAULT,
            ).apply {
                description = "Get notified when new events are added to the calendar."
            },
        )
        manager.createNotificationChannel(
            NotificationChannel(
                EVENTS_CANCELLED_NOTIFICATION_CHANNEL_ID,
                "Event Cancellations",
                NotificationManager.IMPORTANCE_DEFAULT,
            ).apply {
                description = "Get notified when an event is cancelled."
            },
        )
    }

    companion object {
        const val EVENTS_NEW_NOTIFICATION_CHANNEL_ID = "events_channel"
        const val EVENTS_NEW_FCM_TOPIC = "events"

        const val EVENTS_CANCELLED_NOTIFICATION_CHANNEL_ID = "events_cancelled_channel"
        const val EVENTS_CANCELLED_FCM_TOPIC = "events-cancelled"
    }
}
