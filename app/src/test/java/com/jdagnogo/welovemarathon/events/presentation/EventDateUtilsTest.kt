package com.jdagnogo.welovemarathon.events.presentation

import com.jdagnogo.welovemarathon.events.domain.Event
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDate

private fun LocalDate.millis(): Long = atStartOfDay(EVENTS_ZONE).toInstant().toEpochMilli()

class EventDateUtilsTest {

    @Test
    fun `a single-day all-day event expands onto exactly one day`() {
        // Google Calendar's all-day end date is exclusive: a 1-day event on
        // the 13th is start=13th, end=14th.
        val event = Event(
            id = "1",
            startAt = LocalDate.of(2026, 8, 13).millis(),
            endAt = LocalDate.of(2026, 8, 14).millis(),
            isAllDay = true,
        )

        val days = listOf(event).expandByDay().map { it.first }

        assertEquals(listOf(LocalDate.of(2026, 8, 13)), days)
    }

    @Test
    fun `a multi-day all-day event expands onto every day it actually spans`() {
        val event = Event(
            id = "1",
            startAt = LocalDate.of(2026, 7, 24).millis(),
            endAt = LocalDate.of(2026, 8, 17).millis(), // exclusive - last real day is Aug 16
            isAllDay = true,
        )

        val days = listOf(event).expandByDay().map { it.first }

        assertEquals(LocalDate.of(2026, 7, 24), days.first())
        assertEquals(LocalDate.of(2026, 8, 16), days.last())
        assertEquals(false, days.contains(LocalDate.of(2026, 8, 17)))
    }

    @Test
    fun `a timed event crossing midnight still spans both days`() {
        val event = Event(
            id = "1",
            startAt = LocalDate.of(2026, 8, 13).atTime(22, 0).atZone(EVENTS_ZONE).toInstant().toEpochMilli(),
            endAt = LocalDate.of(2026, 8, 14).atTime(1, 0).atZone(EVENTS_ZONE).toInstant().toEpochMilli(),
            isAllDay = false,
        )

        val days = listOf(event).expandByDay().map { it.first }

        assertEquals(listOf(LocalDate.of(2026, 8, 13), LocalDate.of(2026, 8, 14)), days)
    }

    @Test
    fun `a same-day timed event does not duplicate`() {
        val event = Event(
            id = "1",
            startAt = LocalDate.of(2026, 8, 13).atTime(9, 0).atZone(EVENTS_ZONE).toInstant().toEpochMilli(),
            endAt = LocalDate.of(2026, 8, 13).atTime(11, 0).atZone(EVENTS_ZONE).toInstant().toEpochMilli(),
            isAllDay = false,
        )

        val days = listOf(event).expandByDay().map { it.first }

        assertEquals(listOf(LocalDate.of(2026, 8, 13)), days)
    }
}
