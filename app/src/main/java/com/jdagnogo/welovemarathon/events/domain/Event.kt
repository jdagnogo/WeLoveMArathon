package com.jdagnogo.welovemarathon.events.domain

import androidx.annotation.Keep

@Keep
enum class EventStatus(val value: String) {
    CONFIRMED("confirmed"),
    CANCELLED("cancelled");

    companion object {
        fun fromValue(value: String): EventStatus =
            values().firstOrNull { it.value == value } ?: CONFIRMED
    }
}

@Keep
data class Event(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val location: String = "",
    val startAt: Long = 0L,
    val endAt: Long = 0L,
    val isAllDay: Boolean = false,
    val timeZone: String = "Europe/Athens",
    val category: String? = null,
    val displayColor: String? = null,
    val status: EventStatus = EventStatus.CONFIRMED,
) {
    val isCancelled: Boolean get() = status == EventStatus.CANCELLED

    // Ends strictly before now - a live/in-progress or upcoming event isn't "past".
    val isPast: Boolean get() = endAt < System.currentTimeMillis()
}
