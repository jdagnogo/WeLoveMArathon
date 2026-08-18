package com.jdagnogo.welovemarathon.events.data

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jdagnogo.welovemarathon.events.data.EventEntity.Companion.TABLE
import com.jdagnogo.welovemarathon.events.domain.Event
import com.jdagnogo.welovemarathon.events.domain.EventStatus

@Keep
@Entity(tableName = TABLE)
data class EventEntity(
    @PrimaryKey var id: String = "",
    var title: String = "",
    var description: String = "",
    var location: String = "",
    var startAt: Long = 0L,
    var endAt: Long = 0L,
    var isAllDay: Boolean = false,
    var timeZone: String = "Europe/Athens",
    var category: String? = null,
    var displayColor: String? = null,
    var status: String = EventStatus.CONFIRMED.value,
) {
    fun toEvent(): Event {
        return Event(
            id = id,
            title = title,
            description = description,
            location = location,
            startAt = startAt,
            endAt = endAt,
            isAllDay = isAllDay,
            timeZone = timeZone,
            category = category,
            displayColor = displayColor,
            status = EventStatus.fromValue(status),
        )
    }

    companion object {
        const val TABLE = "events"
    }
}
