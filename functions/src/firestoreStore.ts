import { getFirestore, Timestamp } from "firebase-admin/firestore";

const EVENTS_COLLECTION = "events";
const SYNC_STATE_DOC = "syncState/calendarSync";

export interface EventDoc {
  id: string;
  title: string;
  description: string;
  location: string;
  startAt: Timestamp;
  endAt: Timestamp;
  isAllDay: boolean;
  timeZone: string;
  colorId: string | null;
  category: string | null;
  displayColor: string | null;
  status: "confirmed" | "cancelled";
  calendarUpdatedAt: Timestamp;
  expireAt: Timestamp;
  firstSyncedAt: Timestamp;
}

export interface SyncState {
  lastSuccessfulCheckAt?: Timestamp;
  lastFullReconciliationAt?: Timestamp;
  hasCompletedInitialBackfill?: boolean;
}

function db() {
  return getFirestore();
}

export async function getSyncState(): Promise<SyncState | undefined> {
  const snap = await db().doc(SYNC_STATE_DOC).get();
  return snap.data() as SyncState | undefined;
}

export async function updateSyncState(patch: Partial<SyncState>): Promise<void> {
  await db().doc(SYNC_STATE_DOC).set(patch, { merge: true });
}

/** Full doc, not just existence - cancellation notifications need the
 * stored title (Calendar's own "cancelled" payload is often too sparse to
 * trust for this), so this replaces what used to be a plain exists() check. */
export async function getEvent(id: string): Promise<EventDoc | null> {
  const snap = await db().collection(EVENTS_COLLECTION).doc(id).get();
  return snap.exists ? (snap.data() as EventDoc) : null;
}

export async function upsertEvent(doc: EventDoc): Promise<void> {
  await db().collection(EVENTS_COLLECTION).doc(doc.id).set(doc, { merge: true });
}

export async function markCancelled(id: string): Promise<void> {
  await db().collection(EVENTS_COLLECTION).doc(id).update({ status: "cancelled" });
}

/** Everything currently synced whose start date falls within the sync
 * window (from `sinceMillis` onward) - the comparison set for the nightly
 * full-window diff. Must match the same past-window bound the Calendar API
 * fetch itself uses (see fetchFullWindowEvents), or already-synced past
 * events would look "missing" from a Calendar response that was never asked
 * about them in the first place, and get wrongly marked cancelled. */
export async function getWindowEventsMap(sinceMillis: number): Promise<Map<string, EventDoc>> {
  const snap = await db()
    .collection(EVENTS_COLLECTION)
    .where("startAt", ">=", Timestamp.fromMillis(sinceMillis))
    .get();

  const map = new Map<string, EventDoc>();
  snap.forEach((doc) => map.set(doc.id, doc.data() as EventDoc));
  return map;
}
