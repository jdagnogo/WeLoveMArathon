# Firebase / GCP Infrastructure Setup

**Status: mostly done.** Calendar API enabled, `calendar-sync` service account created, Cloud Function deployed (europe-west1) and Cloud Scheduler jobs created, Firestore rules/indexes/TTL deployed. Verified end-to-end against a personal test calendar (empty calendar, but the sync ran clean and wrote a correct `syncState/calendarSync` doc — confirming the service account really can read a shared calendar). Still open: swap `functions/.env`'s `CALENDAR_ID` to the owner's real calendar once she's done her sharing steps, and fill in the real category legend.

### Deployment gotchas found during the actual deploy (not in the original plan)
Creating the service account and enabling the Calendar API (steps 1-2 below) is not sufficient on its own for a working deployment — three additional IAM grants were needed, discovered by watching the function actually fail at each stage in Cloud Logging:
1. **`roles/run.invoker` on the `synccalendarevents` Cloud Run service**, granted to `calendar-sync@...`. Without this, Eventarc's Pub/Sub push subscription can't even invoke the function — requests are rejected before any application code runs, and this failure is silent unless you check Cloud Logging with `resource.type="cloud_run_revision"` (not `gcloud functions logs read`, which showed nothing).
   ```
   gcloud run services add-iam-policy-binding synccalendarevents \
     --region=europe-west1 --project=welovemarathon-71ff6 \
     --member="serviceAccount:calendar-sync@welovemarathon-71ff6.iam.gserviceaccount.com" \
     --role="roles/run.invoker"
   ```
2. **`roles/datastore.user` on the project**, granted to `calendar-sync@...` — a freshly created service account has zero Firestore access by default.
3. **`roles/firebasecloudmessaging.admin` on the project**, granted to `calendar-sync@...` — needed for the notification-sending step, not yet exercised live but granted proactively to avoid a second round-trip.
   ```
   gcloud projects add-iam-policy-binding welovemarathon-71ff6 \
     --member="serviceAccount:calendar-sync@welovemarathon-71ff6.iam.gserviceaccount.com" \
     --role="roles/datastore.user"
   gcloud projects add-iam-policy-binding welovemarathon-71ff6 \
     --member="serviceAccount:calendar-sync@welovemarathon-71ff6.iam.gserviceaccount.com" \
     --role="roles/firebasecloudmessaging.admin"
   ```
IAM changes took roughly 1-2 minutes to actually propagate before retrying worked — a same-second retry after granting a role showed the identical error.

## Goal
All the non-application-code setup: GCP/Firebase console & CLI steps needed before the Cloud Function (`01`) can run for real, and before the Android app (`03`/`04`) can talk to a real backend. Implements the "Manual setup checklist" section of `us/events-calendar-sync.md`.

## Depends on
Nothing — **start this immediately**, in parallel with everything else. It has real lead time (waiting on the owner) so it's the top of the critical path.

## Steps

### 1. Enable the Calendar API
```
gcloud services enable calendar-json.googleapis.com --project=welovemarathon-71ff6
```

### 2. Create the dedicated service account
```
gcloud iam service-accounts create calendar-sync \
  --project=welovemarathon-71ff6 \
  --display-name="Calendar Sync"
```
Its email — `calendar-sync@welovemarathon-71ff6.iam.gserviceaccount.com` — is what gets sent to the owner in step 3. No JSON key needs to be manually downloaded/managed: the Cloud Function runs as a GCP-native identity, so grant this service account as the Cloud Function's *runtime* service account at deploy time rather than embedding a key file.

### 3. Owner-side (human task, not us — track separately)
- Owner creates a **new, dedicated** Google Calendar (e.g. "Marathon Events – Public"), separate from her personal calendar (Decision 15 in the US doc).
- She shares that calendar with `calendar-sync@welovemarathon-71ff6.iam.gserviceaccount.com`, permission **"See all event details"** (read-only is sufficient — nothing ever writes back to her calendar).
- **Blocking dependency**: get her the calendar's ID (visible in Calendar's "Settings and sharing" for that calendar) — the Cloud Function needs this to know which calendar to query.

### 4. Deploy the Cloud Function (once `01-backend-cloud-function.md` is implemented)
```
firebase deploy --only functions:syncCalendarEvents
```

### 5. Create the Cloud Scheduler jobs (after the function is deployed and has a Pub/Sub trigger topic)
```
gcloud scheduler jobs create pubsub sync-events-frequent \
  --schedule="*/15 * * * *" \
  --topic=<pubsub-topic-from-function-deploy> \
  --message-body='{"mode":"incremental"}' \
  --time-zone="Europe/Athens" \
  --project=welovemarathon-71ff6

gcloud scheduler jobs create pubsub sync-events-nightly \
  --schedule="0 3 * * *" \
  --topic=<pubsub-topic-from-function-deploy> \
  --message-body='{"mode":"full"}' \
  --time-zone="Europe/Athens" \
  --project=welovemarathon-71ff6
```

### 6. Firestore security rules
New file at repo root, `firestore.rules` (referenced from `firebase.json`).

**Incident, caught during live testing**: the first deployed version of this file only allowlisted `/events` and `/syncState`. Deploying a rules file *replaces the entire ruleset* rather than adding to it, so this silently denied read access to every other collection in the app (Tips, Food, Blog, Shopping, Beach, ...) that was previously working under whatever permissive rules existed before this project had a source-controlled rules file — the app crashed on launch with `FirebaseFirestoreException: PERMISSION_DENIED`. Fixed by adding an explicit `match /{document=**}` read-only catch-all (confirmed safe: grepping the whole Android app for `.set()`/`.add()`/`.update()` against Firestore found zero client-side writes anywhere, so "read: true, write: false" for everything is a correct, not just convenient, default).

```
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    match /syncState/{docId} {
      allow read, write: if false; // internal bookkeeping only, never client-facing
    }
    match /events/{eventId} {
      allow read: if true;
      allow write: if false; // only the Cloud Function (Admin SDK) writes; Admin SDK bypasses rules entirely
    }
    // Catch-all for every other pre-existing collection - see incident note above.
    match /{document=**} {
      allow read: if true;
      allow write: if false;
    }
  }
}
```
Deploy:
```
firebase deploy --only firestore:rules
```
Implements US5.1.

### 7. Firestore TTL policy
```
gcloud firestore fields ttls update expireAt \
  --collection-group=events \
  --enable-ttl \
  --project=welovemarathon-71ff6
```
Implements US1.5. Note: deletion isn't instant — Google documents TTL cleanup as "usually within 24 hours" of the field's timestamp passing, not immediate.

### 8. Enable Cloud Messaging + refresh `google-services.json`
- Firebase Console → Project Settings → Cloud Messaging tab → confirm the API is enabled (currently it isn't — the existing `google-services.json` has no `cloud_messaging` section).
- Re-download `google-services.json` afterward and replace it in **all three** places it currently exists in this repo:
  - `app/google-services.json`
  - `app/src/main/google-services.json`
  - `app/src/debug/google-services.json`

### 9. Confirm Blaze plan
Already active on `welovemarathon-71ff6` — nothing to do.

## Open items
- The exact Pub/Sub topic name only exists after the function in `01` is first deployed — steps 4 and 5 are sequential for that reason, everything else here can happen anytime.
