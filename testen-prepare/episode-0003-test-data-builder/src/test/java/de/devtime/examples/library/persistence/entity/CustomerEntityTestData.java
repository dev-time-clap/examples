package de.devtime.examples.library.persistence.entity;

import java.util.HashSet;
import java.util.Set;

public class CustomerEntityTestData extends CustomerEntityTestDataBuilder<CustomerEntityTestData> {

  public static CustomerEntityTestData create() {
    return new CustomerEntityTestData();
  }

  public CustomerEntityTestData customerMaxMustermann() {
    withFirstName("Max");
    withLastName("Mustermann");
    withLoanedBooks(new HashSet<>());
    return and();
  }

  public CustomerEntityTestData customerErikaMustermann() {
    withFirstName("Erika");
    withLastName("Mustermann");
    return and();
  }

  public CustomerEntityTestData customerWithBooks(final BookEntity... books) {
    withFirstName("Viel-Leser");
    withLastName("Schmidt");
    withLoanedBooks(Set.of(books));
    return and();
  }
}