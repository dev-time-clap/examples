package de.devtime.examples.library.persistence.entity;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.UUID;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * White-Box Tests für die BookEntity.
 * Testet alle bi-direktionalen Linkmethoden und ihre Pfade.
 */
class BookEntityTest {

  //--------------------< setAdditionalData() Tests >--------------------

  @Nested
  @DisplayName("Tests für setAdditionalData()")
  class SetAdditionalDataTests {

    @Nested
    @DisplayName("When additional data is null")
    class WhenNull {

      @DisplayName("Should set additionalData to null when parameter is null")
      @Test
      void testSetAdditionalData_NullParameter() {
        // Given: Book with existing additional data
        AdditionalBookDataEntity originalData = AdditionalBookDataEntity.builder().buildWithId();
        BookEntity book = BookEntity.builder().buildWithId();
        book.setId(java.util.UUID.randomUUID());
        book.setAdditionalData(originalData);

        // When: Set to null
        book.setAdditionalData(null);

        // Then
        assertNull(book.getAdditionalData());
        assertNull(originalData.getBook(), "Original data should have book set to null");
      }

      @DisplayName("Should work when book has no existing additional data")
      @Test
      void testSetAdditionalData_NullWhenAlreadyNull() {
        // Given: Book without additional data
        BookEntity book = BookEntity.builder().buildWithId();
        book.setId(java.util.UUID.randomUUID());

        // When: Set to null
        book.setAdditionalData(null);

        // Then
        assertNull(book.getAdditionalData());
      }
    }

    @Nested
    @DisplayName("When additional data is not null")
    class WhenNotNull {

      @DisplayName("Should set new additional data and create bidirectional link")
      @Test
      void testSetAdditionalData_NewRelation() {
        // Given: Book without additional data
        BookEntity book = BookEntity.builder().buildWithId();
        book.setId(java.util.UUID.randomUUID());

        AdditionalBookDataEntity newData = AdditionalBookDataEntity.builder().buildWithId();

        // When: Set new additional data
        book.setAdditionalData(newData);

        // Then
        assertSame(newData, book.getAdditionalData());
        assertSame(book, newData.getBook(), "Additional data should have book set");
      }

      @DisplayName("Should do nothing when setting same additional data (loop prevention)")
      @Test
      void testSetAdditionalData_SameData_NoLoop() {
        // Given: Book with existing additional data
        AdditionalBookDataEntity existingData = AdditionalBookDataEntity.builder().buildWithId();
        BookEntity book = BookEntity.builder().buildWithId();
        book.setId(java.util.UUID.randomUUID());
        book.setAdditionalData(existingData);

        // When: Set same additional data again
        book.setAdditionalData(existingData);

        // Then: No change, no infinite loop
        assertSame(existingData, book.getAdditionalData());
        assertSame(book, existingData.getBook());
      }

      @DisplayName("Should remove old link and create new link when changing additional data")
      @Test
      void testSetAdditionalData_ChangeData() {
        // Given: Book with existing additional data
        AdditionalBookDataEntity oldData = AdditionalBookDataEntity.builder().buildWithId();
        AdditionalBookDataEntity newData = AdditionalBookDataEntity.builder().buildWithId();
        BookEntity book = BookEntity.builder().buildWithId();
        book.setAdditionalData(oldData);

        // When: Set new additional data
        book.setAdditionalData(newData);

        // Then
        assertSame(newData, book.getAdditionalData());
        assertNull(oldData.getBook(), "Old data should have book set to null");
        assertSame(book, newData.getBook(), "New data should have book set");
      }
    }
  }

  //--------------------< addBookPublisher() Tests >--------------------

  @Nested
  @DisplayName("Tests für addBookPublisher()")
  class AddBookPublisherTests {

    @Nested
    @DisplayName("When argument is null")
    class WhenNullArgument {

      @DisplayName("Should throw NullPointerException")
      @Test
      void testAddBookPublisher_NullArgument() {
        // Given: Book
        BookEntity book = BookEntity.builder().buildWithId();
        book.setId(java.util.UUID.randomUUID());

        // When & Then
        assertThrows(NullPointerException.class, () -> book.addBookPublisher(null));
      }
    }

    @Nested
    @DisplayName("When argument is not null")
    class WhenNotNullArgument {

      @DisplayName("Should add publisher and create bidirectional link")
      @Test
      void testAddBookPublisher_NewRelation() {
        // Given: Book without publishers
        BookEntity book = BookEntity.builder().buildWithId();
        book.setId(java.util.UUID.randomUUID());

        BookPublisherEntity publisher = BookPublisherEntity.builder().buildWithId();

        // When: Add publisher
        book.addBookPublisher(publisher);

        // Then
        assertTrue(book.getBookPublishers().contains(publisher));
        assertSame(book, publisher.getBook(), "Publisher should have book set");
      }

      @DisplayName("Should do nothing when publisher already exists (loop prevention)")
      @Test
      void testAddBookPublisher_AlreadyExists_NoLoop() {
        // Given: Book with existing publisher
        BookEntity book = BookEntity.builder().buildWithId();
        book.setId(java.util.UUID.randomUUID());

        BookPublisherEntity publisher = BookPublisherEntity.builder().buildWithId();
        book.addBookPublisher(publisher);
        assertTrue(book.getBookPublishers().contains(publisher));

        // When: Add same publisher again
        book.addBookPublisher(publisher);

        // Then: No change, no infinite loop
        assertEquals(1, book.getBookPublishers().size());
        assertSame(book, publisher.getBook());
      }

      @DisplayName("Should add multiple publishers to the book")
      @Test
      void testAddBookPublisher_MultiplePublishers() {
        // Given: Book
        BookEntity book = BookEntity.builder().buildWithId();
        book.setId(java.util.UUID.randomUUID());

        BookPublisherEntity publisher1 = BookPublisherEntity.builder().buildWithId();
        BookPublisherEntity publisher2 = BookPublisherEntity.builder().buildWithId();

        // When: Add multiple publishers
        book.addBookPublisher(publisher1);
        book.addBookPublisher(publisher2);

        // Then
        assertEquals(2, book.getBookPublishers().size());
        assertTrue(book.getBookPublishers().contains(publisher1));
        assertTrue(book.getBookPublishers().contains(publisher2));
      }

      @DisplayName("Should allow same publisher for different books")
      @Test
      void testAddBookPublisher_SamePublisher_DifferentBooks() {
        // Given: Two books and one publisher
        BookEntity book1 = BookEntity.builder().buildWithId();
        BookEntity book2 = BookEntity.builder().buildWithId();
        PublisherEntity publisher = PublisherEntity.builder().buildWithId();
        BookPublisherEntity bookPublisher1 = BookPublisherEntity.builder()
            .withBook(book1)
            .withPublisher(publisher)
            .buildWithId();
        BookPublisherEntity bookPublisher2 = BookPublisherEntity.builder()
            .withBook(book2)
            .withPublisher(publisher)
            .buildWithId();

        // When: Add same publisher to both books
        book1.addBookPublisher(bookPublisher1);
        book2.addBookPublisher(bookPublisher2);

        // Then
        assertTrue(book1.getBookPublishers().contains(bookPublisher1));
        assertTrue(book2.getBookPublishers().contains(bookPublisher2));
      }
    }
  }

  //--------------------< setCustomer() Tests >--------------------

  @Nested
  @DisplayName("Tests für setCustomer()")
  class SetCustomerTests {

    @Nested
    @DisplayName("When customer is null")
    class WhenNull {

      @DisplayName("Should set customer to null when parameter is null")
      @Test
      void testSetCustomer_NullParameter() {
        // Given: Book with existing customer
        CustomerEntity originalCustomer = CustomerEntity.builder().buildWithId();
        BookEntity book = BookEntity.builder().buildWithId();
        book.setId(java.util.UUID.randomUUID());
        book.setCustomer(originalCustomer);

        // When: Set to null
        book.setCustomer(null);

        // Then
        assertNull(book.getCustomer());
        assertFalse(originalCustomer.getLoanedBooks().contains(book), "Original customer should lose the book");
      }

      @DisplayName("Should work when book has no existing customer")
      @Test
      void testSetCustomer_NullWhenAlreadyNull() {
        // Given: Book without customer
        BookEntity book = BookEntity.builder().buildWithId();
        book.setId(java.util.UUID.randomUUID());

        // When: Set to null
        book.setCustomer(null);

        // Then
        assertNull(book.getCustomer());
      }
    }

    @Nested
    @DisplayName("When customer is not null")
    class WhenNotNull {

      @DisplayName("Should set new customer and create bidirectional link")
      @Test
      void testSetCustomer_NewRelation() {
        // Given: Book without customer
        BookEntity book = BookEntity.builder().buildWithId();
        book.setId(java.util.UUID.randomUUID());

        CustomerEntity newCustomer = CustomerEntity.builder().buildWithId();

        // When: Set new customer
        book.setCustomer(newCustomer);

        // Then
        assertSame(newCustomer, book.getCustomer());
        assertTrue(newCustomer.getLoanedBooks().contains(book), "Customer should have book in loaned books");
      }

      @DisplayName("Should do nothing when setting same customer (loop prevention)")
      @Test
      void testSetCustomer_SameCustomer_NoLoop() {
        // Given: Book with existing customer
        CustomerEntity existingCustomer = CustomerEntity.builder().buildWithId();
        BookEntity book = BookEntity.builder().buildWithId();
        book.setId(java.util.UUID.randomUUID());
        book.setCustomer(existingCustomer);

        // When: Set same customer again
        book.setCustomer(existingCustomer);

        // Then: No change, no infinite loop
        assertSame(existingCustomer, book.getCustomer());
        assertTrue(existingCustomer.getLoanedBooks().contains(book));
      }

      @DisplayName("Should remove old customer and add new customer when changing")
      @Test
      void testSetCustomer_ChangeCustomer() {
        // Given: Book with existing customer
        CustomerEntity oldCustomer = CustomerEntity.builder()
            .withLastName("Old")
            .buildWithId();
        CustomerEntity newCustomer = CustomerEntity.builder()
            .withLastName("New")
            .buildWithId();
        BookEntity book = BookEntity.builder().buildWithId();
        book.setCustomer(oldCustomer);

        // When: Set new customer
        book.setCustomer(newCustomer);

        // Then
        assertSame(newCustomer, book.getCustomer());
        assertFalse(oldCustomer.getLoanedBooks().contains(book), "Old customer should lose the book");
        assertTrue(newCustomer.getLoanedBooks().contains(book), "New customer should have book");
      }

      @DisplayName("Should handle old customer removal when changing to new customer")
      @Test
      void testSetCustomer_OldCustomerProperlyRemoved() {
        // Given: Book with existing customer and loaned books collection
        CustomerEntity oldCustomer = CustomerEntity.builder().buildWithId();
        BookEntity book = BookEntity.builder().buildWithId();
        book.setId(java.util.UUID.randomUUID());

        // Manually add the book to old customer (simulating database state)
        oldCustomer.getLoanedBooks().add(book);
        book.setCustomer(oldCustomer);

        // When: Change to new customer
        CustomerEntity newCustomer = CustomerEntity.builder().buildWithId();
        book.setCustomer(newCustomer);

        // Then: Old customer should no longer have the book
        assertFalse(oldCustomer.getLoanedBooks().contains(book));
      }
    }
  }

  //--------------------< addAuthor() Tests >--------------------

  @Nested
  @DisplayName("Tests für addAuthor()")
  class AddAuthorTests {

    @Nested
    @DisplayName("When argument is null")
    class WhenNullArgument {

      @DisplayName("Should throw NullPointerException")
      @Test
      void testAddAuthor_NullArgument() {
        // Given: Book
        BookEntity book = BookEntity.builder().buildWithId();
        book.setId(java.util.UUID.randomUUID());

        // When & Then
        assertThrows(NullPointerException.class, () -> book.addAuthor(null));
      }
    }

    @Nested
    @DisplayName("When argument is not null")
    class WhenNotNullArgument {

      @DisplayName("Should add author and create bidirectional link")
      @Test
      void testAddAuthor_NewRelation() {
        // Given: Book without authors
        BookEntity book = BookEntity.builder().buildWithId();
        book.setId(java.util.UUID.randomUUID());

        AuthorEntity newAuthor = AuthorEntity.builder().buildWithId();

        // When: Add author
        book.addAuthor(newAuthor);

        // Then
        assertTrue(book.getAuthors().contains(newAuthor));
        assertTrue(newAuthor.getBooks().contains(book), "Author should have book in books");
      }

      @DisplayName("Should do nothing when author already exists (loop prevention)")
      @Test
      void testAddAuthor_AlreadyExists_NoLoop() {
        // Given: Book with existing author
        BookEntity book = BookEntity.builder().buildWithId();
        book.setId(java.util.UUID.randomUUID());

        AuthorEntity author = AuthorEntity.builder().buildWithId();
        book.addAuthor(author);
        assertTrue(book.getAuthors().contains(author));

        // When: Add same author again
        book.addAuthor(author);

        // Then: No change, no infinite loop
        assertEquals(1, book.getAuthors().size());
        assertTrue(author.getBooks().contains(book));
      }

      @DisplayName("Should add multiple authors to the book")
      @Test
      void testAddAuthor_MultipleAuthors() {
        // Given: Book
        BookEntity book = BookEntity.builder().buildWithId();
        book.setId(java.util.UUID.randomUUID());

        AuthorEntity author1 = AuthorEntity.builder().buildWithId();
        AuthorEntity author2 = AuthorEntity.builder().buildWithId();

        // When: Add multiple authors
        book.addAuthor(author1);
        book.addAuthor(author2);

        // Then
        assertEquals(2, book.getAuthors().size());
        assertTrue(book.getAuthors().contains(author1));
        assertTrue(book.getAuthors().contains(author2));
      }

      @DisplayName("Should allow same author for different books")
      @Test
      void testAddAuthor_SameAuthor_DifferentBooks() {
        // Given: Two books and one author
        BookEntity book1 = BookEntity.builder().buildWithId();
        book1.setId(java.util.UUID.randomUUID());

        BookEntity book2 = BookEntity.builder().buildWithId();
        book2.setId(java.util.UUID.randomUUID());

        AuthorEntity author = AuthorEntity.builder().buildWithId();

        // When: Add same author to both books
        book1.addAuthor(author);
        book2.addAuthor(author);

        // Then
        assertTrue(book1.getAuthors().contains(author));
        assertTrue(book2.getAuthors().contains(author));
      }

      @DisplayName("Should handle author with existing books collection")
      @Test
      void testAddAuthor_AuthorWithExistingBooks() {
        // Given: Author with existing book
        AuthorEntity author = AuthorEntity.builder().buildWithId();
        BookEntity existingBook = BookEntity.builder().buildWithId();
        existingBook.setId(java.util.UUID.randomUUID());
        author.getBooks().add(existingBook);

        BookEntity newBook = BookEntity.builder().buildWithId();
        newBook.setId(java.util.UUID.randomUUID());

        // When: Add new book to author
        newBook.addAuthor(author);

        // Then
        assertTrue(newBook.getAuthors().contains(author));
        assertTrue(author.getBooks().contains(newBook));
        assertTrue(author.getBooks().contains(existingBook), "Original book should still be in authors collection");
      }
    }
  }

  //--------------------< Additional BookEntity Tests >--------------------

  @Nested
  @DisplayName("Tests für BookEntity Builder Pattern")
  class BuilderPatternTests {

    @DisplayName("Should build a book with all properties set")
    @Test
    void testBookBuilder() {
      // Given: No preconditions

      // When: Build using builder
      AdditionalBookDataEntity data = AdditionalBookDataEntity.builder().buildWithId();
      CustomerEntity customer = CustomerEntity.builder().buildWithId();

      BookEntity book = BookEntity.builder()
          .withIsbn("978-3-12-123456-7")
          .withTitle("Test Buch")
          .withAdditionalData(data)
          .withCustomer(customer)
          .build();
      book.setId(UUID.randomUUID());

      // Then
      assertNotNull(book.getId());
      assertEquals("978-3-12-123456-7", book.getIsbn());
      assertEquals("Test Buch", book.getTitle());
      assertSame(data, book.getAdditionalData());
      assertSame(customer, book.getCustomer());
    }

    @DisplayName("Should build a book with authors and publishers")
    @Test
    void testBookBuilderWithCollections() {
      // Given
      AuthorEntity author = AuthorEntity.builder().buildWithId();
      BookPublisherEntity publisher = BookPublisherEntity.builder().buildWithId();

      // When
      BookEntity book = BookEntity.builder()
          .withIsbn("978-3-12-789456-1")
          .withTitle("Mehrfach Autor Buch")
          .build();
      book.setId(UUID.randomUUID());

      book.addAuthor(author);
      book.addBookPublisher(publisher);

      // Then
      assertEquals(1, book.getAuthors().size());
      assertEquals(1, book.getBookPublishers().size());
    }
  }

  //--------------------< Utility Methods >--------------------

  private void assertNotNull(final Object obj) {
    if (obj == null) {
      throw new AssertionError("Expected object to be non-null");
    }
  }

  private void assertEquals(final Object expected, final Object actual) {
    if (!java.util.Objects.equals(expected, actual)) {
      throw new AssertionError("Expected: " + expected + ", but was: " + actual);
    }
  }

}