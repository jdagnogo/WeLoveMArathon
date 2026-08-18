/**
 * Pure, framework-agnostic transform logic for turning Google Calendar API
 * events into our Firestore event shape, and diffing a full calendar pull
 * against what's already synced. Kept free of any Firestore/Calendar client
 * calls specifically so it can be unit-tested with plain fixture data (see
 * __tests__/eventTransform.test.ts) - no live network or emulator needed.
 */

export interface CalendarApiEvent {
  id: string;
  recurringEventId?: string;
  summary?: string;
  description?: string;
  location?: string;
  status?: string; // "confirmed" | "tentative" | "cancelled"
  colorId?: string;
  updated?: string; // RFC3339
  start?: { date?: string; dateTime?: string; timeZone?: string };
  end?: { date?: string; dateTime?: string; timeZone?: string };
}

export interface ParsedEvent {
  id: string;
  title: string;
  description: string;
  location: string;
  startAtMillis: number;
  endAtMillis: number;
  isAllDay: boolean;
  timeZone: string;
  colorId: string | null;
  category: string | null;
  status: "confirmed" | "cancelled";
  calendarUpdatedAtMillis: number;
}

const DEFAULT_TIME_ZONE = "Europe/Athens";

function partToMillis(part?: { date?: string; dateTime?: string }): number | null {
  if (!part) return null;
  if (part.dateTime) return new Date(part.dateTime).getTime();
  if (part.date) return new Date(`${part.date}T00:00:00`).getTime();
  return null;
}

/**
 * Recurring events are out of scope for v1 (Decision 16 in the US doc):
 * rather than parse RRULEs ourselves, we let the Calendar API expand a
 * series into concrete instances (singleEvents=true) and then keep only the
 * single soonest-upcoming instance per series, discarding the rest - so a
 * weekly event still syncs as one normal one-off event, not many.
 */
export function dedupeRecurringInstances(events: CalendarApiEvent[]): CalendarApiEvent[] {
  const bySeriesKey = new Map<string, CalendarApiEvent>();

  for (const event of events) {
    const key = event.recurringEventId ?? event.id;
    const existing = bySeriesKey.get(key);
    if (!existing) {
      bySeriesKey.set(key, event);
      continue;
    }
    const existingStart = partToMillis(existing.start) ?? Infinity;
    const candidateStart = partToMillis(event.start) ?? Infinity;
    if (candidateStart < existingStart) {
      bySeriesKey.set(key, event);
    }
  }

  return Array.from(bySeriesKey.values());
}

/**
 * Converts one Calendar API event into our internal shape. Returns null for
 * a non-cancelled event missing usable start/end dates - a single malformed
 * event like this must not abort the whole sync batch (US1.3), so the
 * caller is expected to skip nulls and keep going, not throw.
 */
export function parseCalendarEvent(
  event: CalendarApiEvent,
  categoryForDescription: (description: string | null | undefined) => string | null,
): ParsedEvent | null {
  const isCancelled = event.status === "cancelled";
  const startAtMillis = partToMillis(event.start);
  const endAtMillis = partToMillis(event.end);

  if (!isCancelled && (startAtMillis === null || endAtMillis === null)) {
    return null;
  }

  return {
    id: event.id,
    title: event.summary ?? "",
    description: event.description ?? "",
    location: event.location ?? "",
    startAtMillis: startAtMillis ?? 0,
    endAtMillis: endAtMillis ?? 0,
    isAllDay: Boolean(event.start?.date && !event.start?.dateTime),
    timeZone: event.start?.timeZone ?? DEFAULT_TIME_ZONE,
    colorId: event.colorId ?? null,
    category: categoryForDescription(event.description),
    status: isCancelled ? "cancelled" : "confirmed",
    calendarUpdatedAtMillis: event.updated ? new Date(event.updated).getTime() : Date.now(),
  };
}

export interface FullSyncDiff {
  toUpsert: CalendarApiEvent[];
  newEventIds: string[];
  toCancelIds: string[];
}

/**
 * Full-window reconciliation diff (nightly mode): compares everything
 * Google currently reports against everything we have synced for the same
 * window, and decides what to write, what's genuinely new (for
 * notifications), and what quietly disappeared (US1.6 cancellation).
 */
export function diffFullSync(
  calendarEvents: CalendarApiEvent[],
  existing: Map<string, { calendarUpdatedAtMillis: number }>,
): FullSyncDiff {
  const toUpsert: CalendarApiEvent[] = [];
  const newEventIds: string[] = [];
  const seenIds = new Set<string>();

  for (const event of calendarEvents) {
    seenIds.add(event.id);
    const prior = existing.get(event.id);
    if (!prior) {
      toUpsert.push(event);
      newEventIds.push(event.id);
      continue;
    }
    const updatedMillis = event.updated ? new Date(event.updated).getTime() : 0;
    if (updatedMillis > prior.calendarUpdatedAtMillis) {
      toUpsert.push(event);
    }
  }

  const toCancelIds = Array.from(existing.keys()).filter((id) => !seenIds.has(id));

  return { toUpsert, newEventIds, toCancelIds };
}
