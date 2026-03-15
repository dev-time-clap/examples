package de.devtime.examples.library.persistence.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

import org.springframework.context.ApplicationContext;

import de.devtime.examples.library.persistence.repository.PublisherRepository;
import de.devtime.examples.library.test.builder.SaveContext;
import de.devtime.examples.library.test.builder.TestDataBuilder;
import de.devtime.examples.library.test.builder.TestDataBuilderWithSaveSupport;

public class PublisherEntityTestDataBuilder<B extends TestDataBuilder<PublisherEntity>>
    extends PublisherEntity.PublisherEntityBuilder<B>
    implements TestDataBuilderWithSaveSupport<PublisherEntity, PublisherRepository, B> {

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
  public String getUniqueDataSetKey(final PublisherEntity entity) {
    return entity.getName();
  }

  @Override
  public PublisherRepository getRepository(final ApplicationContext appContext) {
    return appContext.getBean(PublisherRepository.class);
  }

  @Override
  public PublisherEntity buildInternally(final boolean withReferences, final boolean save, final SaveContext context) {
    PublisherEntity entity = build().generateId();
    if (this.useExternalId) {
      entity.setId(this.id);
    }
    entity.setVersion(this.version);

    // Build referenced objects
    if (withReferences) {
      buildBookPublishers(withReferences, save, context).forEach(entity::addBookPublisher);
    }
    if (save) {
      entity = save(entity, context);
    }
    return entity;
  }

  private List<BookPublisherEntity> buildBookPublishers(final boolean withReferences, final boolean save,
      final SaveContext context) {
    return this.bookPublisherEntityTestDataProviders.stream()
        .map(provider -> provider.buildInternally(withReferences, save, context))
        .toList();
  }
}