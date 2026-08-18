import {
  CalendarApiEvent,
  dedupeRecurringInstances,
  diffFullSync,
  parseCalendarEvent,
} from "../eventTransform";

const noCategoryMapping = () => null;

describe("dedupeRecurringInstances", () => {
  it("keeps only the earliest-starting instance of a recurring series", () => {
    const series: CalendarApiEvent[] = [
      { id: "master_3", recurringEventId: "master", start: { dateTime: "2026-08-15T09:00:00Z" } },
      { id: "master_1", recurringEventId: "master", start: { dateTime: "2026-08-01T09:00:00Z" } },
      { id: "master_2", recurringEventId: "master", start: { dateTime: "2026-08-08T09:00:00Z" } },
    ];

    const result = dedupeRecurringInstances(series);

    expect(result).toHaveLength(1);
    expect(result[0].id).toBe("master_1");
  });

  it("leaves standalone (non-recurring) events untouched", () => {
    const events: CalendarApiEvent[] = [
      { id: "standalone-1", start: { dateTime: "2026-08-01T09:00:00Z" } },
      { id: "standalone-2", start: { dateTime: "2026-08-02T09:00:00Z" } },
    ];

    const result = dedupeRecurringInstances(events);

    expect(result).toHaveLength(2);
  });

  it("handles a mix of a recurring series and standalone events", () => {
    const events: CalendarApiEvent[] = [
      { id: "master_2", recurringEventId: "master", start: { dateTime: "2026-08-08T09:00:00Z" } },
      { id: "master_1", recurringEventId: "master", start: { dateTime: "2026-08-01T09:00:00Z" } },
      { id: "standalone", start: { dateTime: "2026-08-03T09:00:00Z" } },
    ];

    const result = dedupeRecurringInstances(events);

    expect(result.map((e) => e.id).sort()).toEqual(["master_1", "standalone"]);
  });
});

describe("parseCalendarEvent", () => {
  it("parses a normal timed event", () => {
    const event: CalendarApiEvent = {
      id: "abc",
      summary: "Farmers Market",
      description: "Weekly market",
      location: "Main Square",
      colorId: "5",
      updated: "2026-07-01T12:00:00Z",
      start: { dateTime: "2026-08-01T09:00:00Z", timeZone: "Europe/Athens" },
      end: { dateTime: "2026-08-01T13:00:00Z", timeZone: "Europe/Athens" },
    };

    const parsed = parseCalendarEvent(event, (description) => (description === "Weekly market" ? "Food" : null));

    expect(parsed).toMatchObject({
      id: "abc",
      title: "Farmers Market",
      description: "Weekly market",
      location: "Main Square",
      isAllDay: false,
      timeZone: "Europe/Athens",
      category: "Food",
      status: "confirmed",
    });
    expect(parsed?.startAtMillis).toBe(new Date("2026-08-01T09:00:00Z").getTime());
  });

  it("marks an all-day event correctly", () => {
    const event: CalendarApiEvent = {
      id: "allday",
      summary: "Festival",
      start: { date: "2026-08-15" },
      end: { date: "2026-08-16" },
    };

    const parsed = parseCalendarEvent(event, noCategoryMapping);

    expect(parsed?.isAllDay).toBe(true);
  });

  it("returns null for a non-cancelled event missing start/end (malformed)", () => {
    const event: CalendarApiEvent = { id: "broken", summary: "No dates" };

    const parsed = parseCalendarEvent(event, noCategoryMapping);

    expect(parsed).toBeNull();
  });

  it("still parses a cancelled event even without start/end", () => {
    const event: CalendarApiEvent = { id: "cancelled-1", status: "cancelled" };

    const parsed = parseCalendarEvent(event, noCategoryMapping);

    expect(parsed?.status).toBe("cancelled");
  });

  it("defaults missing text fields to empty strings, not undefined", () => {
    const event: CalendarApiEvent = {
      id: "sparse",
      start: { dateTime: "2026-08-01T09:00:00Z" },
      end: { dateTime: "2026-08-01T10:00:00Z" },
    };

    const parsed = parseCalendarEvent(event, noCategoryMapping);

    expect(parsed?.title).toBe("");
    expect(parsed?.description).toBe("");
    expect(parsed?.location).toBe("");
  });
});

describe("diffFullSync", () => {
  it("flags an event with no prior record as new", () => {
    const calendarEvents: CalendarApiEvent[] = [{ id: "new-1", updated: "2026-07-01T00:00:00Z" }];
    const existing = new Map<string, { calendarUpdatedAtMillis: number }>();

    const diff = diffFullSync(calendarEvents, existing);

    expect(diff.newEventIds).toEqual(["new-1"]);
    expect(diff.toUpsert.map((e) => e.id)).toEqual(["new-1"]);
    expect(diff.toCancelIds).toEqual([]);
  });

  it("upserts a changed event but does not treat it as new", () => {
    const calendarEvents: CalendarApiEvent[] = [
      { id: "existing-1", updated: "2026-07-05T00:00:00Z" },
    ];
    const existing = new Map([
      ["existing-1", { calendarUpdatedAtMillis: new Date("2026-07-01T00:00:00Z").getTime() }],
    ]);

    const diff = diffFullSync(calendarEvents, existing);

    expect(diff.toUpsert.map((e) => e.id)).toEqual(["existing-1"]);
    expect(diff.newEventIds).toEqual([]);
  });

  it("does not upsert an unchanged event", () => {
    const updated = "2026-07-01T00:00:00Z";
    const calendarEvents: CalendarApiEvent[] = [{ id: "unchanged-1", updated }];
    const existing = new Map([
      ["unchanged-1", { calendarUpdatedAtMillis: new Date(updated).getTime() }],
    ]);

    const diff = diffFullSync(calendarEvents, existing);

    expect(diff.toUpsert).toEqual([]);
  });

  it("flags a previously-synced event missing from the calendar as cancelled", () => {
    const calendarEvents: CalendarApiEvent[] = [];
    const existing = new Map([
      ["gone-1", { calendarUpdatedAtMillis: new Date("2026-07-01T00:00:00Z").getTime() }],
    ]);

    const diff = diffFullSync(calendarEvents, existing);

    expect(diff.toCancelIds).toEqual(["gone-1"]);
  });
});
