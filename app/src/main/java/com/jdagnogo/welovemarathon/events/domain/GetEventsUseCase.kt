package com.jdagnogo.welovemarathon.events.domain

import com.jdagnogo.welovemarathon.events.data.EventRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEventsUseCase @Inject constructor(private val repository: EventRepository) {
    operator fun invoke(): Flow<List<Event>> = repository.events
}
