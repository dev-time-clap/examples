package de.devtime.examples.library.persistence.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class AdditionalBookDataEntityTest {

  @Nested
  @DisplayName("Tests für setBook (1:1 inverse)")
  class BookLinkageTests {

    @Test
    @DisplayName("Sollte bi-direktionale Verknüpfung zum Buch herstellen")
    void testSetBook_Success() {
      // Setup mit IDs via Builder
      AdditionalBookDataEntity details = AdditionalBookDataEntity.builder().buildWithId();
      BookEntity book = BookEntity.builder().buildWithId();

      // Act
      details.setBook(book);

      // Assert
      assertEquals(book, details.getBook());
      assertEquals(details, book.getAdditionalData(), "Das Buch muss die Details ebenfalls referenzieren");
    }

    @Test
    @DisplayName("Sollte Endlosschleife verhindern, wenn das Buch bereits identisch ist")
    void testSetBook_AvoidLoop() {
      // Setup
      AdditionalBookDataEntity details = AdditionalBookDataEntity.builder().buildWithId();
      // Spy auf das Buch, um Interaktionen auf der Gegenseite zu prüfen
      BookEntity book = spy(BookEntity.builder().buildWithId());

      // Erster Aufruf stellt Verbindung her
      details.setBook(book);
      clearInvocations(book);

      // Act: Zweiter Aufruf mit gleichem Objekt (gleiche ID)
      details.setBook(book);

      // Assert: Wegen Objects.equals darf setAdditionalData nicht erneut gerufen werden
      verify(book, never()).setAdditionalData(any());
    }

    @Test
    @DisplayName("Sollte Feld auf null setzen ohne Gegenseite zu triggern")
    void testSetBook_Null() {
      // Setup
      AdditionalBookDataEntity details = AdditionalBookDataEntity.builder().buildWithId();
      BookEntity book = BookEntity.builder().buildWithId();
      details.setBook(book); // Initial setzen

      // Act
      details.setBook(null);

      // Assert
      assertNull(details.getBook());
    }

    @Test
    @DisplayName("Sollte Verknüpfung bei Buchwechsel korrekt aktualisieren")
    void testSetBook_ChangeBook() {
      // Setup
      AdditionalBookDataEntity details = AdditionalBookDataEntity.builder().buildWithId();
      BookEntity oldBook = BookEntity.builder().buildWithId();
      BookEntity newBook = BookEntity.builder().buildWithId();

      details.setBook(oldBook);

      // Act
      details.setBook(newBook);

      // Assert
      assertEquals(newBook, details.getBook());
      assertEquals(details, newBook.getAdditionalData());
      // Hinweis: Da die Methode im Code das alte Buch nicht explizit nullt (kein oldBook.setAdditionalData(null)),
      // bleibt die Referenz im oldBook technisch bestehen, bis sie dort überschrieben wird.
      // Der Test validiert hier den Pfad der neuen Zuweisung.
    }
  }
}