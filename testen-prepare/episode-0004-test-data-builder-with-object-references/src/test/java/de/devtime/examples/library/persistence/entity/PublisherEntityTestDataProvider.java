package de.devtime.examples.library.persistence.entity;

public class PublisherEntityTestDataProvider extends PublisherEntityTestDataBuilder<PublisherEntityTestDataProvider> {

  public static PublisherEntityTestDataProvider create() {
    return new PublisherEntityTestDataProvider();
  }

  public PublisherEntityTestDataProvider publisherOReilly() {
    withName("O'Reilly Media");
    return and();
  }

  public PublisherEntityTestDataProvider publisherSpringer() {
    withName("Springer Nature");
    return and();
  }

  public PublisherEntityTestDataProvider publisherManning() {
    withName("Manning Publications");
    return and();
  }
}