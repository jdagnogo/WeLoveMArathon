/**
 * Categories are sourced from a hashtag the owner types into the event's
 * Google Calendar description (e.g. "#Music" anywhere in the text) - not
 * from the event's color. Calendar's color picker turned out to be
 * unreadable via the API for events colored through its current UI (colorId
 * comes back empty even right after setting a color - see the 2026-08-13
 * investigation), so this replaces that approach entirely.
 *
 * Matching is case-insensitive (#Music, #MUSIC, #music all match) and
 * singular/plural-tolerant (#Music and #Musics both match "music", #Theater
 * and #Theaters both match "theaters"). [label] must match the Android app's
 * EventCategory.label exactly.
 */
interface CategoryDefinition {
  hashtag: string; // lowercase, without the leading #
  label: string;
}

const CATEGORY_DEFINITIONS: CategoryDefinition[] = [
  { hashtag: "theaters", label: "Theaters" },
  { hashtag: "music", label: "Music" },
  { hashtag: "festivals", label: "Festivals" },
  { hashtag: "cinema", label: "Cinema" },
  { hashtag: "sports", label: "Sports" },
  { hashtag: "culture", label: "Culture" },
  { hashtag: "talks", label: "Talks & Seminars" },
  { hashtag: "charity", label: "Charity & Volunteering" },
];

const BY_HASHTAG = new Map(CATEGORY_DEFINITIONS.map((def) => [def.hashtag, def.label]));

const HASHTAG_PATTERN = /#(\w+)/g;

/** A hashtag and its singular/plural counterpart (e.g. "music" <-> "musics"). */
function pluralVariants(tag: string): string[] {
  return tag.endsWith("s") ? [tag, tag.slice(0, -1)] : [tag, `${tag}s`];
}

/** The first recognized category hashtag found in the description, or null. */
export function categoryForDescription(description: string | null | undefined): string | null {
  if (!description) return null;
  for (const match of description.matchAll(HASHTAG_PATTERN)) {
    const tag = match[1].toLowerCase();
    for (const variant of pluralVariants(tag)) {
      const label = BY_HASHTAG.get(variant);
      if (label) return label;
    }
  }
  return null;
}
