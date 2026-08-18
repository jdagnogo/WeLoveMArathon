export const CALENDAR_ID = process.env.CALENDAR_ID ?? "";

// Forward sync window (Decision 3 / US doc): how far ahead we pull events.
export const SYNC_WINDOW_DAYS = 90;

// Backward sync window: how far back we also pull events, so recently-past
// events (e.g. "what happened last week") stay visible instead of only ever
// syncing what's upcoming. Kept equal to RETENTION_DAYS on purpose - there's
// no point fetching something Firestore's TTL would delete before it's ever
// shown.
export const PAST_WINDOW_DAYS = 90;

// How long past its end date an event stays in Firestore before the TTL
// policy removes it (Decision 6 / US1.5).
export const RETENTION_DAYS = 90;

export const FCM_NEW_EVENTS_TOPIC = "events";
export const NEW_EVENTS_NOTIFICATION_CHANNEL_ID = "events_channel";

export const FCM_CANCELLED_EVENTS_TOPIC = "events-cancelled";
export const CANCELLED_EVENTS_NOTIFICATION_CHANNEL_ID = "events_cancelled_channel";

// The service account this function runs as (created via `gcloud iam
// service-accounts create calendar-sync`, see technical-plan/02-firebase-infra.md).
// No downloaded key is used - this identity is granted to the function at
// deploy time, and the owner shares her calendar with this exact address.
export const SERVICE_ACCOUNT_EMAIL =
  "calendar-sync@welovemarathon-71ff6.iam.gserviceaccount.com";
