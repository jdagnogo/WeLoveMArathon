import { initializeApp } from "firebase-admin/app";
import * as logger from "firebase-functions/logger";
import { onMessagePublished } from "firebase-functions/v2/pubsub";
import { runFullReconciliation, runIncrementalSync } from "./calendarSync";
import { SERVICE_ACCOUNT_EMAIL } from "./config";

initializeApp();

interface SyncMessagePayload {
  mode?: "incremental" | "full";
}

/**
 * Triggered by two Cloud Scheduler jobs publishing to the same Pub/Sub topic
 * with different payloads (see technical-plan/01-backend-cloud-function.md):
 *   sync-events-frequent -> {"mode":"incremental"}  every 15 minutes
 *   sync-events-nightly  -> {"mode":"full"}          once a night
 *
 * Runs as the `calendar-sync` service account (not the function's default
 * identity) so it only ever needs the read access the owner explicitly
 * granted by sharing her calendar with that address - nothing more.
 */
export const syncCalendarEvents = onMessagePublished(
  {
    topic: "sync-calendar-events",
    region: "europe-west1",
    serviceAccount: SERVICE_ACCOUNT_EMAIL,
  },
  async (event) => {
    const payload = event.data.message.json as SyncMessagePayload | undefined;
    const mode = payload?.mode ?? "incremental";

    logger.info(`syncCalendarEvents starting`, { mode });

    if (mode === "full") {
      await runFullReconciliation();
    } else {
      await runIncrementalSync();
    }

    logger.info(`syncCalendarEvents finished`, { mode });
  },
);
