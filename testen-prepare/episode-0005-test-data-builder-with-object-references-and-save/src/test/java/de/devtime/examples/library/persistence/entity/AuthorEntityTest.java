package de.devtime.examples.library.persistence.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class AuthorEntityTest {

  @Nested
  @DisplayName("Tests für addBook (N:M)")
  class AddBookTests {

    @Test
    @DisplayName("Sollte Buch hinzufügen und Autor im Buch-Set registrieren")
    void testAddBook_Success() {
      // Setup mit IDs
      AuthorEntity author = AuthorEntity.builder().build().generateId();
      BookEntity book = BookEntity.builder().build().generateId();

      // Act
      author.addBook(book);

      // Assert
      assertTrue(author.getBooks().contains(book), "Buch muss im Set des Autors sein");
      assertTrue(book.getAuthors().contains(author), "Autor muss im Set des Buches sein");
    }

    @Test
    @DisplayName("Sollte NullPointerException werfen, wenn das Buch null ist")
    void testAddBook_Null() {
      AuthorEntity author = AuthorEntity.builder().build().generateId();

      assertThrows(NullPointerException.class, () -> author.addBook(null));
    }

    @Test
    @DisplayName("Sollte Abbruch triggern, wenn Buch bereits im Set vorhanden ist")
    void testAddBook_AvoidLoop() {
      // Setup
      AuthorEntity author = AuthorEntity.builder().build().generateId();
      // Spy auf Buch, um Interaktionen auf der Gegenseite (getAuthors) zu prüfen
      BookEntity book = spy(BookEntity.builder().build().generateId());

      // Initiales Hinzufügen
      author.addBook(book);
      clearInvocations(book);

      // Act: Erneuter Aufruf mit gleichem Objekt (gleiche ID)
      author.addBook(book);

      // Assert: Wegen this.books.contains(book) darf die Gegenseite nicht erneut gefragt werden
      verify(book, never()).getAuthors();
      assertEquals(1, author.getBooks().size());
    }

    @Test
    @DisplayName("Sollte sich nicht redundant im Buch eintragen, wenn Link dort schon existiert")
    void testAddBook_AlreadyLinkedOnOtherSide() {
      // Setup
      AuthorEntity author = AuthorEntity.builder().build().generateId();
      BookEntity book = BookEntity.builder().build().generateId();

      // Wir simulieren, dass das Buch den Autor bereits kennt (z.B. durch andere Logik)
      book.addAuthor(author);
      assertEquals(1, book.getAuthors().size());

      // Act: Autor fügt nun das Buch hinzu
      author.addBook(book);

      // Assert
      assertTrue(author.getBooks().contains(book));
      assertEquals(1, book.getAuthors().size(), "Buch-Set sollte nicht doppelt addiert werden");
    }
  }
}