package de.devtime.examples.library.persistence.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

import de.devtime.examples.library.test.builder.TestDataBuilder;

public class BookEntityTestDataBuilder<B extends TestDataBuilder<BookEntity>>
    extends BookEntity.BookEntityBuilder<B>
    implements TestDataBuilder<BookEntity> {

  // --------------------< Add referenced builder here >--------------------

  private AdditionalBookDataEntityTestDataProvider additionalDataTestDataProvider;
  private CustomerEntityTestDataProvider customerTestDataProvider;
  private List<BookPublisherEntityTestDataProvider> bookPublisherEntityTestDataProviders = new ArrayList<>();
  private List<AuthorEntityTestDataProvider> authorTestDataProviders = new ArrayList<>();

  // Additional Data
  public B withAdditionalData(final Consumer<AdditionalBookDataEntityTestDataProvider> consumer) {
    this.additionalDataTestDataProvider = this.additionalDataTestDataProvider == null
        ? AdditionalBookDataEntityTestDataProvider.create()
        : this.additionalDataTestDataProvider;
    consumer.accept(this.additionalDataTestDataProvider);
    return and();
  }

  public B withAdditionalData(final AdditionalBookDataEntityTestDataProvider additionalDataTestDataProvider) {
    this.additionalDataTestDataProvider = additionalDataTestDataProvider;
    return and();
  }

  // Book-Publisher relations
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

  // Author
  public B withAuthor(final Consumer<AuthorEntityTestDataProvider> consumer) {
    AuthorEntityTestDataProvider builder = AuthorEntityTestDataProvider.create();
    consumer.accept(builder);
    this.authorTestDataProviders.add(builder);
    return and();
  }

  public B withAuthor(final AuthorEntityTestDataProvider authorTestDataBuilder) {
    this.authorTestDataProviders.add(authorTestDataBuilder);
    return and();
  }

  // Customer
  public B withCustomer(final Consumer<CustomerEntityTestDataProvider> consumer) {
    this.customerTestDataProvider = this.customerTestDataProvider == null
        ? CustomerEntityTestDataProvider.create()
        : this.customerTestDataProvider;
    consumer.accept(this.customerTestDataProvider);
    return and();
  }

  public B withCustomer(final CustomerEntityTestDataProvider customerTestDataBuilder) {
    this.customerTestDataProvider = customerTestDataBuilder;
    return and();
  }

  // --------------------< Add super fields here >--------------------

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
  public BookEntity buildWithId() {
    BookEntity entity = buildInternally(true);
    if (this.useExternalId) {
      entity.setId(this.id);
    }
    entity.setVersion(this.version);
    return entity;
  }

  @Override
  public BookEntity buildWithReferences(final boolean generateId) {
    BookEntity entity = buildInternally(generateId);
    if (this.useExternalId) {
      entity.setId(this.id);
    }
    entity.setVersion(this.version);

    // Build referenced objects
    entity.setAdditionalData(buildAdditionalData(generateId));
    entity.setCustomer(buildCustomer(generateId));
    buildBookPublishers(generateId).forEach(entity::addBookPublisher);
    buildAuthors(generateId).forEach(entity::addAuthor);
    return entity;
  }

  private AdditionalBookDataEntity buildAdditionalData(final boolean generateId) {
    AdditionalBookDataEntity referencedEntity = null;
    if (this.additionalDataTestDataProvider != null) {
      referencedEntity = this.additionalDataTestDataProvider.buildWithReferences(generateId);
    }
    return referencedEntity;
  }

  private CustomerEntity buildCustomer(final boolean generateId) {
    CustomerEntity referencedEntity = null;
    if (this.customerTestDataProvider != null) {
      referencedEntity = this.customerTestDataProvider.buildWithReferences(generateId);
    }
    return referencedEntity;
  }

  private List<BookPublisherEntity> buildBookPublishers(final boolean generateId) {
    return this.bookPublisherEntityTestDataProviders.stream()
        .map(provider -> provider.buildWithReferences(generateId))
        .toList();
  }

  private List<AuthorEntity> buildAuthors(final boolean generateId) {
    return this.authorTestDataProviders.stream()
        .map(provider -> provider.buildWithReferences(generateId))
        .toList();
  }
}
