package com.jdagnogo.welovemarathon.events.domain

import com.jdagnogo.welovemarathon.events.data.EventRepository
import javax.inject.Inject

class RefreshEventsUseCase @Inject constructor(private val repository: EventRepository) {
    suspend operator fun invoke() = repository.refresh()
}
