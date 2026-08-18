import { GoogleAuth } from "google-auth-library";
import { calendar_v3, google } from "googleapis";
import { CALENDAR_ID } from "./config";
import { CalendarApiEvent } from "./eventTransform";

// No downloaded JSON key - authenticates as whatever service account the
// Cloud Function itself runs as (see SERVICE_ACCOUNT_EMAIL in config.ts and
// the deploy step in technical-plan/02-firebase-infra.md).
const auth = new GoogleAuth({
  scopes: ["https://www.googleapis.com/auth/calendar.readonly"],
});

let cachedClient: calendar_v3.Calendar | undefined;

async function calendarClient(): Promise<calendar_v3.Calendar> {
  if (!cachedClient) {
    const authClient = await auth.getClient();
    cachedClient = google.calendar({ version: "v3", auth: authClient as never });
  }
  return cachedClient;
}

function windowStart(now: Date, pastWindowDays: number): Date {
  return new Date(now.getTime() - pastWindowDays * 24 * 60 * 60 * 1000);
}

function windowEnd(now: Date, windowDays: number): Date {
  return new Date(now.getTime() + windowDays * 24 * 60 * 60 * 1000);
}

/** "What changed since the last check" - the cheap 15-minute poll (US1.1). */
export async function fetchIncrementalEvents(
  updatedMinIso: string,
  now: Date,
  windowDays: number,
  pastWindowDays: number,
): Promise<CalendarApiEvent[]> {
  const calendar = await calendarClient();
  const res = await calendar.events.list({
    calendarId: CALENDAR_ID,
    updatedMin: updatedMinIso,
    singleEvents: true,
    showDeleted: true,
    timeMin: windowStart(now, pastWindowDays).toISOString(),
    timeMax: windowEnd(now, windowDays).toISOString(),
    maxResults: 2500,
  });
  return (res.data.items ?? []) as CalendarApiEvent[];
}

/** Full re-pull of the whole sync window - the nightly reconciliation pass (US1.2). */
export async function fetchFullWindowEvents(
  now: Date,
  windowDays: number,
  pastWindowDays: number,
): Promise<CalendarApiEvent[]> {
  const calendar = await calendarClient();
  const res = await calendar.events.list({
    calendarId: CALENDAR_ID,
    singleEvents: true,
    showDeleted: false,
    timeMin: windowStart(now, pastWindowDays).toISOString(),
    timeMax: windowEnd(now, windowDays).toISOString(),
    maxResults: 2500,
  });
  return (res.data.items ?? []) as CalendarApiEvent[];
}
