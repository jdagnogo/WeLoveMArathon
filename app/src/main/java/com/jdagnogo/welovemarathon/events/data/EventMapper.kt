package com.jdagnogo.welovemarathon.events.data

import androidx.annotation.Keep
import com.jdagnogo.welovemarathon.events.domain.Event

@Keep
class EventMapper {
    fun toEvents(entities: List<EventEntity>): List<Event> = entities.map { it.toEvent() }

    fun toEventEntities(dtos: List<EventDto>): List<EventEntity> = dtos.map { it.toEventEntity() }
}

fun EventDto.toEventEntity(): EventEntity {
    return EventEntity(
        id = id,
        title = title,
        description = description,
        location = location,
        startAt = startAt?.toDate()?.time ?: 0L,
        endAt = endAt?.toDate()?.time ?: 0L,
        isAllDay = isAllDay,
        timeZone = timeZone,
        category = category,
        displayColor = displayColor,
        status = status,
    )
}
