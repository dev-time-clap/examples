package de.devtime.examples.library.persistence.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

import de.devtime.examples.library.test.builder.TestDataBuilder;

public class AuthorEntityTestDataBuilder<B extends TestDataBuilder<AuthorEntity>>
    extends AuthorEntity.AuthorEntityBuilder<B>
    implements TestDataBuilder<AuthorEntity> {

  // --------------------< Add referenced builder here >--------------------

  private List<BookEntityTestDataProvider> bookTestDataProviders = new ArrayList<>();

  public B withBook(final Consumer<BookEntityTestDataProvider> consumer) {
    BookEntityTestDataProvider builder = BookEntityTestDataProvider.create();
    consumer.accept(builder);
    this.bookTestDataProviders.add(builder);
    return and();
  }

  public B withBook(final BookEntityTestDataProvider bookTestDataBuilder) {
    this.bookTestDataProviders.add(bookTestDataBuilder);
    return and();
  }

  public B withBooks(final List<BookEntityTestDataProvider> bookTestDataBuilders) {
    this.bookTestDataProviders.addAll(bookTestDataBuilders);
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
  public AuthorEntity buildWithId() {
    AuthorEntity entity = super.buildWithId();
    if (this.useExternalId) {
      entity.setId(this.id);
    }
    entity.setVersion(this.version);
    return entity;
  }

  @Override
  public AuthorEntity buildWithReferences(final boolean generateId) {
    AuthorEntity entity = buildInternally(generateId);
    if (this.useExternalId) {
      entity.setId(this.id);
    }
    entity.setVersion(this.version);

    // Build referenced objects
    buildBooks(generateId).forEach(entity::addBook);
    return entity;
  }

  private List<BookEntity> buildBooks(final boolean generateId) {
    return this.bookTestDataProviders.stream()
        .map(provider -> provider.buildWithReferences(generateId))
        .toList();
  }
}
