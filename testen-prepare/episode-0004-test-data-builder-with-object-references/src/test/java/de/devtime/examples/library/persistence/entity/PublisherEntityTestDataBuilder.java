package de.devtime.examples.library.persistence.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

import de.devtime.examples.library.test.builder.TestDataBuilder;

public class PublisherEntityTestDataBuilder<B extends TestDataBuilder<PublisherEntity>>
    extends PublisherEntity.PublisherEntityBuilder<B>
    implements TestDataBuilder<PublisherEntity> {

  // --------------------< Add referenced builder here >--------------------

  private List<BookPublisherEntityTestDataProvider> bookPublisherEntityTestDataProviders = new ArrayList<>();

  public B withBookPublisher(final Consumer<BookPublisherEntityTestDataProvider> consumer) {
    BookPublisherEntityTestDataProvider builder = BookPublisherEntityTestDataProvider.create();
    consumer.accept(builder);
    this.bookPublisherEntityTestDataProviders.add(builder);
    return and();
  }

  public B withBookPublisher(final BookPublisherEntityTestDataProvider bookPublisherEntityTestDataProvider) {
    this.bookPublisherEntityTestDataProviders.add(bookPublisherEntityTestDataProvider);
    return and();
  }

  // --------------------< Add super fields here >----

  private UUID id;
  private int version;
  private boolean useExternalId = false;

  public B withId(final UUID id) {
    this.id = id;
    this.useExternalId = true;
    return and();
  }

  public B withVersion(final int version) {
    this.version = version;
    return and();
  }

  // --------------------< Internal builder logic >--------------------

  @Override
  public PublisherEntity buildWithId() {
    PublisherEntity entity = super.buildWithId();
    if (this.useExternalId) {
      entity.setId(this.id);
    }
    entity.setVersion(this.version);
    return entity;
  }

  @Override
  public PublisherEntity buildWithReferences(final boolean generateId) {
    PublisherEntity entity = buildInternally(generateId);

    // Build referenced objects
    buildBookPublishers(generateId).forEach(entity::addBookPublisher);

    return entity;
  }

  private List<BookPublisherEntity> buildBookPublishers(final boolean generateId) {
    return this.bookPublisherEntityTestDataProviders.stream()
        .map(provider -> provider.buildWithReferences(generateId))
        .toList();
  }
}