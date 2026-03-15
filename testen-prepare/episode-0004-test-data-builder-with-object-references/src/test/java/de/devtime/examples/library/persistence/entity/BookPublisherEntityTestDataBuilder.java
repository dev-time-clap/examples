package de.devtime.examples.library.persistence.entity;

import java.util.UUID;
import java.util.function.Consumer;

import de.devtime.examples.library.persistence.entity.BookPublisherEntity.BookPublisherEntityBuilder;
import de.devtime.examples.library.test.builder.TestDataBuilder;

public class BookPublisherEntityTestDataBuilder<B extends TestDataBuilder<BookPublisherEntity>>
    extends BookPublisherEntityBuilder<B>
    implements TestDataBuilder<BookPublisherEntity> {

  //--------------------< Add referenced builder here >--------------------

  private PublisherEntityTestDataProvider publisherTestDataProvider;
  private BookEntityTestDataProvider bookTestDataProvider;

  public B withPublisher(final Consumer<PublisherEntityTestDataProvider> consumer) {
    this.publisherTestDataProvider = this.publisherTestDataProvider == null
        ? PublisherEntityTestDataProvider.create()
        : this.publisherTestDataProvider;
    consumer.accept(this.publisherTestDataProvider);
    return and();
  }

  public B withPublisher(final PublisherEntityTestDataProvider provider) {
    this.publisherTestDataProvider = provider;
    return and();
  }

  public B withBook(final Consumer<BookEntityTestDataProvider> consumer) {
    this.bookTestDataProvider = this.bookTestDataProvider == null
        ? BookEntityTestDataProvider.create()
        : this.bookTestDataProvider;
    consumer.accept(this.bookTestDataProvider);
    return and();
  }

  public B withBook(final BookEntityTestDataProvider provider) {
    this.bookTestDataProvider = provider;
    return and();
  }

  //--------------------< Add super fields here >--------------------

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

  //--------------------< Internal builder logic >--------------------

  @Override
  public BookPublisherEntity buildWithId() {
    BookPublisherEntity entity = super.buildWithId();
    if (this.useExternalId) {
      entity.setId(this.id);
    }
    entity.setVersion(this.version);
    return entity;
  }

  @Override
  public BookPublisherEntity buildWithReferences(final boolean generateId) {
    BookPublisherEntity entity = buildInternally(generateId);
    if (this.useExternalId) {
      entity.setId(this.id);
    }
    entity.setVersion(this.version);

    // Build referenced objects
    entity.setPublisher(buildPublisher(generateId));
    entity.setBook(buildBook(generateId));
    return entity;
  }

  private PublisherEntity buildPublisher(final boolean generateId) {
    PublisherEntity referencedEntity = null;
    if (this.publisherTestDataProvider != null) {
      referencedEntity = this.publisherTestDataProvider.buildWithReferences(generateId);
    }
    return referencedEntity;
  }

  private BookEntity buildBook(final boolean generateId) {
    BookEntity referencedEntity = null;
    if (this.bookTestDataProvider != null) {
      referencedEntity = this.bookTestDataProvider.buildWithReferences(generateId);
    }
    return referencedEntity;
  }
}
