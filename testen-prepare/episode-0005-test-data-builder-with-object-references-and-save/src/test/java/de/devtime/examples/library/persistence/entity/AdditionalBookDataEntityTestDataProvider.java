package de.devtime.examples.library.persistence.entity;

public class AdditionalBookDataEntityTestDataProvider
    extends AdditionalBookDataEntityTestDataBuilder<AdditionalBookDataEntityTestDataProvider> {

  public static AdditionalBookDataEntityTestDataProvider create() {
    return new AdditionalBookDataEntityTestDataProvider();
  }

  public AdditionalBookDataEntityTestDataProvider bookDetailsForJustABookByMorrigan() {
    withSummary("Ein lehrreiches Buch über Softwareentwicklung.");
    withRating(5);
    withPageCount(321);
    withLanguageCode("DE");
    withKeywords("Wissen, Softwareentwicklung, Lehrbuch");
    return and();
  }

  public AdditionalBookDataEntityTestDataProvider thrillerDetails() {
    withSummary("Ein spannender Thriller mit vielen Wendungen.");
    withRating(5);
    withPageCount(450);
    withLanguageCode("DE");
    withKeywords("Spannung, Mord, Ermittlung");
    return and();
  }

  public AdditionalBookDataEntityTestDataProvider shortEnglishStory() {
    withSummary("A short story about a lost cat.");
    withRating(3);
    withPageCount(120);
    withLanguageCode("EN");
    withKeywords("Animals, Heartwarming");
    return and();
  }

  public AdditionalBookDataEntityTestDataProvider emptyDetails() {
    withSummary("");
    withRating(0);
    withPageCount(0);
    return and();
  }
}
