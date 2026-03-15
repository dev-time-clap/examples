package de.devtime.examples.library.test.builder;

public interface TestDataBuilder<E> {

  E build();

  E buildWithReferences(boolean generateId);

  default E buildWithReferences() {
    return buildWithReferences(false);
  }
}
