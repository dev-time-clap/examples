package de.devtime.examples.library.persistence.entity;

import com.fasterxml.uuid.Generators;

public interface BuilderWithId<E extends AbstractEntity> {

  E build();

  default E buildWithId() {
    return buildInternally(true);
  }

  default E buildInternally(final boolean generateId) {
    E entity = build();
    if (generateId) {
      entity.setId(Generators.timeBasedEpochRandomGenerator().generate());
    }
    return entity;
  }
}