package de.devtime.examples.library.persistence.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

import org.springframework.context.ApplicationContext;

import de.devtime.examples.library.persistence.repository.AuthorRepository;
import de.devtime.examples.library.test.builder.TestDataBuilder;
import de.devtime.examples.library.test.builder.TestDataBuilderWithSaveSupport;

public class AuthorEntityTestDataBuilder<B extends TestDataBuilder<AuthorEntity>>
    extends AuthorEntity.AuthorEntityBuilder<B>
    implements TestDataBuilderWithSaveSupport<AuthorEntity, AuthorRepository, B> {

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
  public String getUniqueDataSetKey(final AuthorEntity entity) {
    return entity.getArtistName();
  }

  @Override
  public AuthorRepository getRepository(final ApplicationContext appContext) {
    return appContext.getBean(AuthorRepository.class);
  }

  @Override
  public AuthorEntity buildInternally(final boolean withReferences, final boolean save) {
    AuthorEntity entity = build().generateId();
    if (this.useExternalId) {
      entity.setId(this.id);
    }
    entity.setVersion(this.version);

    // Build referenced objects
    if (withReferences) {
      buildBooks(withReferences, save).forEach(entity::addBook);
    }
    if (save) {
      entity = save(entity);
    }
    return entity;
  }

  private List<BookEntity> buildBooks(final boolean withReferences, final boolean save) {
    return this.bookTestDataProviders.stream()
        .map(provider -> provider.buildInternally(withReferences, save))
        .toList();
  }
}
