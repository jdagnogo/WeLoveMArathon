import { getMessaging } from "firebase-admin/messaging";
import {
  CANCELLED_EVENTS_NOTIFICATION_CHANNEL_ID,
  FCM_CANCELLED_EVENTS_TOPIC,
  FCM_NEW_EVENTS_TOPIC,
  NEW_EVENTS_NOTIFICATION_CHANNEL_ID,
} from "./config";
import { ParsedEvent } from "./eventTransform";

/**
 * Friendlier-than-default copy (US3.6). Single new event deep-links straight
 * to that event (eventId in the data payload); a multi-event digest omits
 * eventId so the Android client opens the Events list instead (US3.2).
 */
export async function notifyNewEvents(newEvents: ParsedEvent[]): Promise<void> {
  if (newEvents.length === 0) return;

  const { title, body, data } =
    newEvents.length === 1 ? singleNewEventMessage(newEvents[0]) : newEventsDigestMessage(newEvents.length);

  await getMessaging().send({
    topic: FCM_NEW_EVENTS_TOPIC,
    notification: { title, body },
    data,
    android: { notification: { channelId: NEW_EVENTS_NOTIFICATION_CHANNEL_ID } },
  });
}

/**
 * A separate topic/channel from new-event notifications (US - cancellation
 * notifications), so a user can independently mute one without the other.
 * Only ever called with events whose start date is still upcoming - the
 * caller (calendarSync.ts) filters out cancellations of events that already
 * happened, since notifying about those isn't actionable for anyone.
 */
export interface CancelledEventInfo {
  id: string;
  title: string;
  startAtMillis: number;
  timeZone: string;
}

export async function notifyCancelledEvents(cancelledEvents: CancelledEventInfo[]): Promise<void> {
  if (cancelledEvents.length === 0) return;

  const { title, body, data } =
    cancelledEvents.length === 1
      ? singleCancelledEventMessage(cancelledEvents[0])
      : cancelledEventsDigestMessage(cancelledEvents.length);

  await getMessaging().send({
    topic: FCM_CANCELLED_EVENTS_TOPIC,
    notification: { title, body },
    data,
    android: { notification: { channelId: CANCELLED_EVENTS_NOTIFICATION_CHANNEL_ID } },
  });
}

function singleNewEventMessage(event: ParsedEvent) {
  return {
    title: "🎉 New event in Marathon!",
    body: `${event.title} — ${formatDate(event.startAtMillis, event.timeZone)}`,
    data: { eventId: event.id },
  };
}

function newEventsDigestMessage(count: number) {
  return {
    title: `🎉 ${count} new events in Marathon!`,
    body: "Tap to see what's new",
    // No eventId (nothing single to deep-link to), but an explicit "screen"
    // marker so the Android client can tell "tapped a digest notification"
    // apart from "launched normally with no extras" and open the Events
    // list instead of just landing on Home.
    data: { screen: "events" },
  };
}

function singleCancelledEventMessage(event: CancelledEventInfo) {
  return {
    title: "⚠️ Event cancelled",
    body: `${event.title} (${formatDate(event.startAtMillis, event.timeZone)}) has been cancelled`,
    data: { eventId: event.id },
  };
}

function cancelledEventsDigestMessage(count: number) {
  return {
    title: `⚠️ ${count} events cancelled`,
    body: "Tap to see what changed",
    data: { screen: "events" },
  };
}

function formatDate(millis: number, timeZone: string): string {
  return new Intl.DateTimeFormat("en-GB", {
    timeZone,
    weekday: "short",
    day: "numeric",
    month: "short",
  }).format(new Date(millis));
}
