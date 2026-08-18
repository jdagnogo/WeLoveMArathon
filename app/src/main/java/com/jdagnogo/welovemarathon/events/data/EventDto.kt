package com.jdagnogo.welovemarathon.events.data

import androidx.annotation.Keep
import com.google.firebase.Timestamp

/**
 * Mirrors the `events` Firestore document shape written by the sync Cloud Function
 * (see technical-plan/00-overview.md). Firestore's `toObject()` matches fields by name,
 * so property names here must stay in sync with the document field names.
 */
@Keep
data class EventDto(
    val id: String = "",
    val title: String = "",
    val description: String = "",
    val location: String = "",
    val startAt: Timestamp? = null,
    val endAt: Timestamp? = null,
    val isAllDay: Boolean = false,
    val timeZone: String = "Europe/Athens",
    val category: String? = null,
    val displayColor: String? = null,
    val status: String = "confirmed",
)
