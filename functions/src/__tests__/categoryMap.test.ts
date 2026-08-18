import { categoryForDescription } from "../categoryMap";

describe("categoryForDescription", () => {
  it("matches a hashtag regardless of case", () => {
    expect(categoryForDescription("Come join us! #Music tonight.")).toBe("Music");
    expect(categoryForDescription("#MUSIC")).toBe("Music");
    expect(categoryForDescription("#music")).toBe("Music");
  });

  it("matches a multi-word category label via its single-word hashtag", () => {
    expect(categoryForDescription("Free entry #charity event")).toBe("Charity & Volunteering");
    expect(categoryForDescription("Panel discussion #Talks")).toBe("Talks & Seminars");
  });

  it("matches a plural or singular variant of a hashtag", () => {
    expect(categoryForDescription("#Musics tonight")).toBe("Music");
    expect(categoryForDescription("#Theater downtown")).toBe("Theaters");
  });

  it("returns null when no recognized hashtag is present", () => {
    expect(categoryForDescription("Just a regular description, no tags")).toBeNull();
    expect(categoryForDescription("#notacategory")).toBeNull();
  });

  it("returns null for empty or missing descriptions", () => {
    expect(categoryForDescription("")).toBeNull();
    expect(categoryForDescription(null)).toBeNull();
    expect(categoryForDescription(undefined)).toBeNull();
  });

  it("returns the first recognized hashtag when multiple are present", () => {
    expect(categoryForDescription("#sports and also #music today")).toBe("Sports");
  });
});
