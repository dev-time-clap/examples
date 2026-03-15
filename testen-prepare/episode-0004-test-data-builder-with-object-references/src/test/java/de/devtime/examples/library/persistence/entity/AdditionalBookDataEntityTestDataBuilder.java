package de.devtime.examples.library.persistence.entity;

import java.util.UUID;
import java.util.function.Consumer;

import de.devtime.examples.library.persistence.entity.AdditionalBookDataEntity.AdditionalBookDataEntityBuilder;
import de.devtime.examples.library.test.builder.TestDataBuilder;

public class AdditionalBookDataEntityTestDataBuilder<B extends TestDataBuilder<AdditionalBookDataEntity>>
    extends AdditionalBookDataEntityBuilder<B>
    implements TestDataBuilder<AdditionalBookDataEntity> {

  // --------------------< Add referenced builder here >--------------------

  private BookEntityTestDataProvider bookTestDataBuilder;

  public B withBook(final Consumer<BookEntityTestDataProvider> consumer) {
    this.bookTestDataBuilder = this.bookTestDataBuilder == null
        ? BookEntityTestDataProvider.create()
        : this.bookTestDataBuilder;
    consumer.accept(this.bookTestDataBuilder);
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
  public AdditionalBookDataEntity buildWithId() {
    AdditionalBookDataEntity entity = super.buildWithId();
    if (this.useExternalId) {
      entity.setId(this.id);
    }
    entity.setVersion(this.version);
    return entity;
  }

  @Override
  public AdditionalBookDataEntity buildWithReferences(final boolean generateId) {
    AdditionalBookDataEntity entity = buildInternally(generateId);
    if (this.useExternalId) {
      entity.setId(this.id);
    }
    entity.setVersion(this.version);

    // Build referenced objects
    entity.setBook(buildBook(generateId));

    return entity;
  }

  private BookEntity buildBook(final boolean generateId) {
    BookEntity referencedEntity = null;
    if (this.bookTestDataBuilder != null) {
      referencedEntity = this.bookTestDataBuilder.buildWithReferences(generateId);
    }
    return referencedEntity;
  }
}