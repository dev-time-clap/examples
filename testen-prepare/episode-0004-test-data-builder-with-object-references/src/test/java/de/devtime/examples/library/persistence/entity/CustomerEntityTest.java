package de.devtime.examples.library.persistence.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class CustomerEntityTest {

  @Nested
  @DisplayName("Tests für addLoanedBook (1:N)")
  class LoanedBookTests {

    @Test
    @DisplayName("Sollte Buch zur Liste hinzufügen und den Kunden im Buch setzen")
    void testAddLoanedBook_Success() {
      // Setup mit IDs via Builder
      CustomerEntity customer = CustomerEntity.builder().buildWithId();
      BookEntity book = BookEntity.builder().buildWithId();

      // Act
      customer.addLoanedBook(book);

      // Assert
      assertTrue(customer.getLoanedBooks().contains(book), "Buch muss in der Liste des Kunden sein");
      assertEquals(customer, book.getCustomer(), "Gegenseite (Buch) muss den Kunden referenzieren");
    }

    @Test
    @DisplayName("Sollte NullPointerException werfen, wenn das Buch null ist")
    void testAddLoanedBook_Null() {
      CustomerEntity customer = CustomerEntity.builder().buildWithId();

      assertThrows(NullPointerException.class, () -> customer.addLoanedBook(null));
    }

    @Test
    @DisplayName("Sollte Endlosschleife verhindern, wenn das Buch bereits in der Liste ist")
    void testAddLoanedBook_AvoidLoop() {
      // Setup
      CustomerEntity customer = CustomerEntity.builder().buildWithId();
      // Spy auf das Buch, um Interaktionen auf der Gegenseite (setCustomer) zu prüfen
      BookEntity book = spy(BookEntity.builder().buildWithId());

      // Erstes Hinzufügen
      customer.addLoanedBook(book);
      clearInvocations(book);

      // Act: Erneuter Aufruf mit identischem Buch (gleiche ID)
      customer.addLoanedBook(book);

      // Assert: Wegen this.loanedBooks.contains(book) darf setCustomer nicht erneut gerufen werden
      verify(book, never()).setCustomer(any());
      assertEquals(1, customer.getLoanedBooks().size());
    }

    @Test
    @DisplayName("Sollte Buch von altem Kunden entfernen, wenn es neu zugewiesen wird")
    void testAddLoanedBook_TransferFromOtherCustomer() {
      // Setup
      CustomerEntity firstCustomer = CustomerEntity.builder().buildWithId();
      CustomerEntity secondCustomer = CustomerEntity.builder().buildWithId();
      BookEntity book = BookEntity.builder().buildWithId();

      // Buch ist zuerst bei Kunde 1
      firstCustomer.addLoanedBook(book);
      assertTrue(firstCustomer.getLoanedBooks().contains(book));

      // Act: Kunde 2 fügt das Buch hinzu
      // White-Box: Dies triggert book.setCustomer(secondCustomer), 
      // was intern firstCustomer.getLoanedBooks().remove(book) aufruft.
      secondCustomer.addLoanedBook(book);

      // Assert
      assertFalse(firstCustomer.getLoanedBooks().contains(book), "Buch muss beim alten Kunden entfernt worden sein");
      assertTrue(secondCustomer.getLoanedBooks().contains(book), "Buch muss beim neuen Kunden registriert sein");
      assertEquals(secondCustomer, book.getCustomer());
    }
  }
}
