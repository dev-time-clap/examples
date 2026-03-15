package de.devtime.examples.library.persistence.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

import org.springframework.context.ApplicationContext;

import de.devtime.examples.library.persistence.repository.BookRepository;
import de.devtime.examples.library.test.builder.TestDataBuilder;
import de.devtime.examples.library.test.builder.TestDataBuilderWithSaveSupport;

public class BookEntityTestDataBuilder<B extends TestDataBuilder<BookEntity>>
    extends BookEntity.BookEntityBuilder<B>
    implements TestDataBuilderWithSaveSupport<BookEntity, BookRepository, B> {

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
  public String getUniqueDataSetKey(final BookEntity entity) {
    return entity.getIsbn();
  }

  @Override
  public BookRepository getRepository(final ApplicationContext appContext) {
    return appContext.getBean(BookRepository.class);
  }

  @Override
  public BookEntity buildInternally(final boolean withReferences, final boolean save) {
    BookEntity entity = build().generateId();
    if (this.useExternalId) {
      entity.setId(this.id);
    }
    entity.setVersion(this.version);

    // Build referenced objects
    if (withReferences) {
      entity.setAdditionalData(buildAdditionalData(withReferences, save));
      entity.setCustomer(buildCustomer(withReferences, save));
      buildBookPublishers(withReferences, save).forEach(entity::addBookPublisher);
      buildAuthors(withReferences, save).forEach(entity::addAuthor);
    }
    if (save) {
      entity = save(entity);
    }
    return entity;
  }

  private AdditionalBookDataEntity buildAdditionalData(final boolean withReferences, final boolean save) {
    AdditionalBookDataEntity referencedEntity = null;
    if (this.additionalDataTestDataProvider != null) {
      referencedEntity = this.additionalDataTestDataProvider.buildInternally(withReferences, save);
    }
    return referencedEntity;
  }

  private CustomerEntity buildCustomer(final boolean withReferences, final boolean save) {
    CustomerEntity referencedEntity = null;
    if (this.customerTestDataProvider != null) {
      referencedEntity = this.customerTestDataProvider.buildInternally(withReferences, save);
    }
    return referencedEntity;
  }

  private List<BookPublisherEntity> buildBookPublishers(final boolean withReferences, final boolean save) {
    return this.bookPublisherEntityTestDataProviders.stream()
        .map(provider -> provider.buildInternally(withReferences, save))
        .toList();
  }

  private List<AuthorEntity> buildAuthors(final boolean withReferences, final boolean save) {
    return this.authorTestDataProviders.stream()
        .map(provider -> provider.buildInternally(withReferences, save))
        .toList();
  }
}
