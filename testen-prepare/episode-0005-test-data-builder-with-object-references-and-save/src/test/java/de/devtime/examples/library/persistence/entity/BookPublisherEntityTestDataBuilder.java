package de.devtime.examples.library.persistence.entity;

import java.util.UUID;
import java.util.function.Consumer;

import org.springframework.context.ApplicationContext;

import de.devtime.examples.library.persistence.entity.BookPublisherEntity.BookPublisherEntityBuilder;
import de.devtime.examples.library.persistence.repository.BookPublisherRepository;
import de.devtime.examples.library.test.builder.SaveContext;
import de.devtime.examples.library.test.builder.TestDataBuilder;
import de.devtime.examples.library.test.builder.TestDataBuilderWithSaveSupport;

public class BookPublisherEntityTestDataBuilder<B extends TestDataBuilder<BookPublisherEntity>>
    extends BookPublisherEntityBuilder<B>
    implements TestDataBuilderWithSaveSupport<BookPublisherEntity, BookPublisherRepository, B> {

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
  public String getUniqueDataSetKey(final BookPublisherEntity entity) {
    return entity.getBook().getIsbn() + entity.getPublisher().getName();
  }

  @Override
  public BookPublisherRepository getRepository(final ApplicationContext appContext) {
    return appContext.getBean(BookPublisherRepository.class);
  }

  @Override
  public BookPublisherEntity buildInternally(final boolean withReferences, final boolean save,
      final SaveContext context) {
    BookPublisherEntity entity = build().generateId();
    if (this.useExternalId) {
      entity.setId(this.id);
    }
    entity.setVersion(this.version);

    // Build referenced objects
    if (withReferences) {
      entity.setPublisher(buildPublisher(withReferences, save, context));
      entity.setBook(buildBook(withReferences, save, context));
    }
    if (save) {
      entity = save(entity, context);
    }
    return entity;
  }

  private PublisherEntity buildPublisher(final boolean withReferences, final boolean save, final SaveContext context) {
    PublisherEntity referencedEntity = null;
    if (this.publisherTestDataProvider != null) {
      referencedEntity = this.publisherTestDataProvider.buildInternally(withReferences, save, context);
    }
    return referencedEntity;
  }

  private BookEntity buildBook(final boolean withReferences, final boolean save, final SaveContext context) {
    BookEntity referencedEntity = null;
    if (this.bookTestDataProvider != null) {
      referencedEntity = this.bookTestDataProvider.buildInternally(withReferences, save, context);
    }
    return referencedEntity;
  }
}
