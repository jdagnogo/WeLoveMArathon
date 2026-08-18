import { Timestamp } from "firebase-admin/firestore";
import * as logger from "firebase-functions/logger";
import { fetchFullWindowEvents, fetchIncrementalEvents } from "./calendarClient";
import { categoryForDescription } from "./categoryMap";
import { PAST_WINDOW_DAYS, RETENTION_DAYS, SYNC_WINDOW_DAYS } from "./config";
import {
  CalendarApiEvent,
  dedupeRecurringInstances,
  diffFullSync,
  ParsedEvent,
  parseCalendarEvent,
} from "./eventTransform";
import {
  EventDoc,
  getEvent,
  getSyncState,
  getWindowEventsMap,
  markCancelled,
  updateSyncState,
  upsertEvent,
} from "./firestoreStore";
import { CancelledEventInfo, notifyCancelledEvents, notifyNewEvents } from "./notify";

function toEventDoc(parsed: ParsedEvent, now: Date): EventDoc {
  return {
    id: parsed.id,
    title: parsed.title,
    description: parsed.description,
    location: parsed.location,
    startAt: Timestamp.fromMillis(parsed.startAtMillis),
    endAt: Timestamp.fromMillis(parsed.endAtMillis),
    isAllDay: parsed.isAllDay,
    timeZone: parsed.timeZone,
    colorId: parsed.colorId,
    category: parsed.category,
    // Category now comes from a #hashtag in the description (categoryMap.ts),
    // not from the event color - Calendar's color picker isn't readable via
    // the API. displayColor is kept only for schema continuity; the app
    // derives its badge color from the category label instead of this field.
    displayColor: null,
    status: parsed.status,
    calendarUpdatedAt: Timestamp.fromMillis(parsed.calendarUpdatedAtMillis),
    expireAt: Timestamp.fromMillis(
      parsed.endAtMillis + RETENTION_DAYS * 24 * 60 * 60 * 1000,
    ),
    firstSyncedAt: Timestamp.fromMillis(now.getTime()),
  };
}

/** A cancellation is only worth notifying about if the event hasn't already
 * happened - nobody needs a push about a meeting that was cancelled last week. */
function toCancelledEventInfo(doc: EventDoc, now: Date): CancelledEventInfo | null {
  const startAtMillis = doc.startAt.toMillis();
  if (startAtMillis < now.getTime()) return null;
  return { id: doc.id, title: doc.title, startAtMillis, timeZone: doc.timeZone };
}

/**
 * Every 15 minutes (US1.1): ask Google what changed since the last
 * successful check, and only touch Firestore for what actually did.
 */
export async function runIncrementalSync(now: Date = new Date()): Promise<void> {
  const state = await getSyncState();

  if (!state?.lastSuccessfulCheckAt) {
    // No prior checkpoint yet. Google Calendar's API rejects an `updatedMin`
    // that's too far in the past (e.g. epoch 0), and bootstrapping is the
    // nightly full reconciliation's job anyway (US1.2/US1.8) - so just record
    // a checkpoint for future incremental runs to diff from, and stop here.
    await updateSyncState({ lastSuccessfulCheckAt: Timestamp.fromDate(now) });
    return;
  }

  const updatedMinIso = state.lastSuccessfulCheckAt.toDate().toISOString();
  const rawEvents = await fetchIncrementalEvents(updatedMinIso, now, SYNC_WINDOW_DAYS, PAST_WINDOW_DAYS);
  const events = dedupeRecurringInstances(rawEvents);

  if (events.length === 0) {
    // Nothing changed - cheapest possible run (US1.7: only advance the
    // checkpoint after a successful run).
    await updateSyncState({ lastSuccessfulCheckAt: Timestamp.fromDate(now) });
    return;
  }

  const newEvents: ParsedEvent[] = [];
  const cancelledEvents: CancelledEventInfo[] = [];

  for (const event of events) {
    try {
      const existingDoc = await getEvent(event.id);
      const isNew = existingDoc === null;

      if (event.status === "cancelled") {
        // Never existed - nothing to cancel (US1.6).
        if (existingDoc) {
          await markCancelled(event.id);
          const info = toCancelledEventInfo(existingDoc, now);
          if (info) cancelledEvents.push(info);
        }
        continue;
      }

      const parsed = parseCalendarEvent(event, categoryForDescription);
      if (!parsed) {
        logger.warn(`Skipping malformed event ${event.id} (missing start/end)`);
        continue;
      }

      await upsertEvent(toEventDoc(parsed, now));
      if (isNew) newEvents.push(parsed);
    } catch (err) {
      // One bad event must not abort the rest of the batch (US1.3).
      logger.error(`Failed to sync event ${event.id}`, err);
    }
  }

  if (state?.hasCompletedInitialBackfill) {
    if (newEvents.length > 0) await notifyNewEvents(newEvents);
    if (cancelledEvents.length > 0) await notifyCancelledEvents(cancelledEvents);
  }

  await updateSyncState({ lastSuccessfulCheckAt: Timestamp.fromDate(now) });
}

/**
 * Once a night (US1.2): full re-pull and diff of the whole sync window,
 * self-healing anything the frequent cheap checks could have missed. The
 * very first time this ever runs, it silently backfills with no
 * notifications (US1.8) - every event in a brand new Firestore looks "new,"
 * and that's not something real users should be spammed about.
 */
export async function runFullReconciliation(now: Date = new Date()): Promise<void> {
  const state = await getSyncState();
  const isFirstEverRun = !state?.hasCompletedInitialBackfill;

  const rawEvents = await fetchFullWindowEvents(now, SYNC_WINDOW_DAYS, PAST_WINDOW_DAYS);
  const events = dedupeRecurringInstances(rawEvents);

  const windowStartMillis = now.getTime() - PAST_WINDOW_DAYS * 24 * 60 * 60 * 1000;
  const existingDocs = await getWindowEventsMap(windowStartMillis);
  const existingForDiff = new Map(
    Array.from(existingDocs.entries()).map(([id, doc]) => [
      id,
      { calendarUpdatedAtMillis: doc.calendarUpdatedAt.toMillis() },
    ]),
  );

  const { toUpsert, newEventIds, toCancelIds } = diffFullSync(events, existingForDiff);
  const newEventIdSet = new Set(newEventIds);
  const newEvents: ParsedEvent[] = [];

  for (const event of toUpsert) {
    try {
      const parsed = parseCalendarEvent(event, categoryForDescription);
      if (!parsed) {
        logger.warn(`Skipping malformed event ${event.id} (missing start/end)`);
        continue;
      }
      await upsertEvent(toEventDoc(parsed, now));
      if (newEventIdSet.has(event.id)) newEvents.push(parsed);
    } catch (err) {
      logger.error(`Failed to sync event ${event.id}`, err);
    }
  }

  const cancelledEvents: CancelledEventInfo[] = [];
  for (const id of toCancelIds) {
    try {
      await markCancelled(id);
      const existingDoc = existingDocs.get(id);
      if (existingDoc) {
        const info = toCancelledEventInfo(existingDoc, now);
        if (info) cancelledEvents.push(info);
      }
    } catch (err) {
      logger.error(`Failed to mark event ${id} as cancelled`, err);
    }
  }

  if (!isFirstEverRun) {
    if (newEvents.length > 0) await notifyNewEvents(newEvents);
    if (cancelledEvents.length > 0) await notifyCancelledEvents(cancelledEvents);
  }

  await updateSyncState({
    lastFullReconciliationAt: Timestamp.fromDate(now),
    lastSuccessfulCheckAt: Timestamp.fromDate(now),
    hasCompletedInitialBackfill: true,
  });
}
