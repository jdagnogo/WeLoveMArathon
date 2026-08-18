package com.jdagnogo.welovemarathon.events.presentation

import com.jdagnogo.welovemarathon.events.domain.Event
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

val EVENTS_ZONE: ZoneId = ZoneId.of("Europe/Athens")

// Matches the backend's PAST_WINDOW_DAYS/RETENTION_DAYS (functions/src/config.ts) -
// no point displaying a day range wider than what's actually synced/retained.
private const val PAST_WINDOW_DAYS = 90L

fun Long.toLocalDate(zoneId: ZoneId = EVENTS_ZONE): LocalDate =
    Instant.ofEpochMilli(this).atZone(zoneId).toLocalDate()

/**
 * Expands a multi-day event into one entry per date it spans, so it shows up
 * under every day it's active in the agenda (Decision 17), not just its start day.
 */
fun List<Event>.expandByDay(): List<Pair<LocalDate, Event>> {
    return flatMap { event ->
        val start = event.startAt.toLocalDate()
        // Google Calendar's all-day end date is exclusive (a single-day event
        // on the 13th is stored as start=13th, end=14th) - subtract a day so a
        // 1-day all-day event doesn't get expanded onto the day after it ends.
        // Timed events don't have this off-by-one; their endAt is a real instant.
        val end = event.endAt.toLocalDate().let { if (event.isAllDay) it.minusDays(1) else it }
        if (end.isBefore(start)) {
            listOf(start to event)
        } else {
            generateSequence(start) { it.plusDays(1) }
                .takeWhile { !it.isAfter(end) }
                .map { it to event }
                .toList()
        }
    }
}

/**
 * Groups events by day in plain chronological order (past -> today ->
 * future). The agenda still opens with today/next-event as the first
 * visible item, but that's a scroll-position concern (see EventsScreen's
 * scroll-to-today-on-open), not a reordering of the list itself - scrolling
 * up from today reveals older history, exactly like scrolling a normal
 * calendar. Shows the same window the backend actually syncs and retains -
 * up to 90 days back. A cancelled event still disappears once it falls
 * outside that back window, per US2.8, without needing separate logic - it
 * just ages out the same way any other past event does.
 */
fun groupEventsByDay(events: List<Event>, today: LocalDate = LocalDate.now(EVENTS_ZONE)): Map<LocalDate, List<Event>> {
    val earliestVisibleDay = today.minusDays(PAST_WINDOW_DAYS)
    return events
        .expandByDay()
        .filter { (day, _) -> !day.isBefore(earliestVisibleDay) }
        .sortedBy { (_, event) -> event.startAt }
        .groupBy({ it.first }, { it.second })
        .toSortedMap()
}

/**
 * The flat LazyColumn item index (day headers + event items, in display
 * order) of the first day that's today or later, so the agenda can land on
 * "Today" on open instead of at the top of however far back the 90-day
 * history goes. Falls back to 0 (top) if every synced day is in the past.
 */
fun Map<LocalDate, List<Event>>.indexOfTodayHeader(today: LocalDate = LocalDate.now(EVENTS_ZONE)): Int {
    var index = 0
    for ((day, events) in this) {
        if (!day.isBefore(today)) return index
        index += 1 + events.size
    }
    return 0
}

fun LocalDate.toDisplayLabel(today: LocalDate = LocalDate.now(EVENTS_ZONE)): String {
    return when (this) {
        today -> "Today"
        today.plusDays(1) -> "Tomorrow"
        today.minusDays(1) -> "Yesterday"
        else -> format(DateTimeFormatter.ofPattern("EEEE, MMM d", Locale.ENGLISH))
    }
}

/**
 * A short "Happening now" / "Starts in 45m" badge for events that are
 * imminent or in progress, so users don't have to do the date math
 * themselves. Returns null for anything further out - deliberately quiet
 * for events that aren't soon, so the agenda doesn't get noisy.
 */
fun relativeStartLabel(
    startAtMillis: Long,
    endAtMillis: Long,
    nowMillis: Long = System.currentTimeMillis(),
): String? {
    if (nowMillis in startAtMillis..endAtMillis) return "Happening now"
    if (startAtMillis <= nowMillis) return null

    val diffMinutes = (startAtMillis - nowMillis) / 60_000
    return when {
        diffMinutes < 60 -> "Starts in ${diffMinutes}m"
        diffMinutes < 24 * 60 -> "Starts in ${diffMinutes / 60}h"
        else -> null
    }
}
