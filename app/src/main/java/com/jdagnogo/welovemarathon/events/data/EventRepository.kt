package com.jdagnogo.welovemarathon.events.data

import com.jdagnogo.welovemarathon.events.domain.Event
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.concurrent.TimeUnit
import javax.inject.Inject

interface EventRepository {
    val events: Flow<List<Event>>
    suspend fun refresh()
}

class EventRepositoryImpl @Inject constructor(
    private val dao: EventDao,
    private val remoteSource: EventFirestoreSource,
    private val mapper: EventMapper,
) : EventRepository {

    override val events: Flow<List<Event>>
        get() = dao.getAll().map { mapper.toEvents(it) }

    override suspend fun refresh() {
        // Belt-and-suspenders local cleanup mirroring the server's 3-month-past TTL
        // (US1.5), in case a device hasn't opened the app in a while. This is
        // deliberately NOT "delete anything already past" - past/cancelled events
        // stay in Room and are only hidden from the agenda by EventsState's own
        // date filtering (US2.5/US2.8), not by being removed here.
        dao.deleteExpired(System.currentTimeMillis() - RETENTION_WINDOW_MILLIS)

        val result = remoteSource.fetchAll()
        val dtos = result.data ?: return // network/parse failure: keep showing last-known-good Room data
        dao.replaceAll(mapper.toEventEntities(dtos))
    }

    private companion object {
        val RETENTION_WINDOW_MILLIS = TimeUnit.DAYS.toMillis(90)
    }
}
