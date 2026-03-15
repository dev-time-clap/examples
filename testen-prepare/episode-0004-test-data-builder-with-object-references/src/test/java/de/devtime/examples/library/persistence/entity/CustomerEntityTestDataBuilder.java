package de.devtime.examples.library.persistence.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

import de.devtime.examples.library.persistence.entity.CustomerEntity.CustomerEntityBuilder;
import de.devtime.examples.library.test.builder.TestDataBuilder;

public class CustomerEntityTestDataBuilder<B extends TestDataBuilder<CustomerEntity>>
    extends CustomerEntityBuilder<B>
    implements TestDataBuilder<CustomerEntity> {

  // --------------------< Add referenced builder here >--------------------

  private List<BookEntityTestDataProvider> bookTestDataProviders = new ArrayList<BookEntityTestDataProvider>();

  public B withLoanedBook(final Consumer<BookEntityTestDataProvider> consumer) {
    BookEntityTestDataProvider builder = BookEntityTestDataProvider.create();
    consumer.accept(builder);
    this.bookTestDataProviders.add(builder);
    return and();
  }

  public B withLoanedBook(final BookEntityTestDataProvider bookTestDataBuilder) {
    this.bookTestDataProviders.add(bookTestDataBuilder);
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
  public CustomerEntity buildWithId() {
    CustomerEntity entity = super.buildWithId();
    if (this.useExternalId) {
      entity.setId(this.id);
    }
    entity.setVersion(this.version);
    return entity;
  }

  @Override
  public CustomerEntity buildWithReferences(final boolean generateId) {
    CustomerEntity entity = buildInternally(generateId);

    buildLoanedBooks(generateId).forEach(entity::addLoanedBook);

    return entity;
  }

  private List<BookEntity> buildLoanedBooks(final boolean generateId) {
    return this.bookTestDataProviders.stream()
        .map(provider -> provider.buildWithReferences(generateId))
        .toList();
  }
}
