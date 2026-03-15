package de.devtime.examples.library.persistence.entity;

import java.util.HashSet;
import java.util.Set;

public class CustomerEntityTestDataProvider extends CustomerEntityTestDataBuilder<CustomerEntityTestDataProvider> {

  public static CustomerEntityTestDataProvider create() {
    return new CustomerEntityTestDataProvider();
  }

  public CustomerEntityTestDataProvider customerMaxMustermann() {
    withFirstName("Max");
    withLastName("Mustermann");
    withNumber("knd-0001");
    withLoanedBooks(new HashSet<>());
    return and();
  }

  public CustomerEntityTestDataProvider customerErikaMustermann() {
    withFirstName("Erika");
    withLastName("Mustermann");
    withNumber("knd-0002");
    return and();
  }

  public CustomerEntityTestDataProvider customerWithBooks(final BookEntity... books) {
    withFirstName("Viel-Leser");
    withLastName("Schmidt");
    withNumber("knd-0003");
    withLoanedBooks(Set.of(books));
    return and();
  }
}