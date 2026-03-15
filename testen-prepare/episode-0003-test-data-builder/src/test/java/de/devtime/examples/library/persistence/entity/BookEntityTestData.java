package de.devtime.examples.library.persistence.entity;

public class BookEntityTestData extends BookEntityTestDataBuilder<BookEntityTestData> {

  public static BookEntityTestData create() {
    return new BookEntityTestData();
  }

  public BookEntityTestData bookJustABookByMorrigan() {
    withIsbn("ISBN-0815");
    withIsOnLoan(false);
    withTitle("Just a book");
    return and();
  }
}
