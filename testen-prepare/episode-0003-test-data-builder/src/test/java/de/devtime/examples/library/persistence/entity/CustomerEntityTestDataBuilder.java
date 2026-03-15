package de.devtime.examples.library.persistence.entity;

import java.util.UUID;

public class CustomerEntityTestDataBuilder<B> extends CustomerEntity.CustomerEntityBuilder<B> {

  // --------------------< Add super fields here >--------------------

  private UUID id;
  private int version;

  public B withId(final UUID id) {
    this.id = id;
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
    if (this.id != null) {
      entity.setId(this.id);
    }
    entity.setVersion(this.version);
    return entity;
  }
}