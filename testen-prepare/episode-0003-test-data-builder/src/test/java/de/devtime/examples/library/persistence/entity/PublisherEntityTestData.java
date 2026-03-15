package de.devtime.examples.library.persistence.entity;

public class PublisherEntityTestData extends PublisherEntityTestDataBuilder<PublisherEntityTestData> {

  public static PublisherEntityTestData create() {
    return new PublisherEntityTestData();
  }

  public PublisherEntityTestData publisherOReilly() {
    withName("O'Reilly Media");
    return and();
  }

  public PublisherEntityTestData publisherSpringer() {
    withName("Springer Nature");
    return and();
  }

  public PublisherEntityTestData publisherManning() {
    withName("Manning Publications");
    return and();
  }
}