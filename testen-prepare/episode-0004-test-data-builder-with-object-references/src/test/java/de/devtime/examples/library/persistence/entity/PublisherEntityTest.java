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

class PublisherEntityTest {

  @Nested
  @DisplayName("Tests für addBookPublisher (1:N inverse)")
  class BookPublisherLinkageTests {

    @Test
    @DisplayName("Sollte Relation hinzufügen und den Publisher in der Relation setzen")
    void testAddBookPublisher_Success() {
      // Setup mit IDs via Builder
      PublisherEntity publisher = PublisherEntity.builder().buildWithId();
      BookPublisherEntity relation = BookPublisherEntity.builder().buildWithId();

      // Act
      publisher.addBookPublisher(relation);

      // Assert
      assertTrue(publisher.getBookPublishers().contains(relation), "Relation muss im Set des Publishers sein");
      assertEquals(publisher, relation.getPublisher(), "Gegenseite (Relation) muss den Publisher referenzieren");
    }

    @Test
    @DisplayName("Sollte NullPointerException werfen, wenn die Relation null ist")
    void testAddBookPublisher_Null() {
      PublisherEntity publisher = PublisherEntity.builder().buildWithId();

      assertThrows(NullPointerException.class, () -> publisher.addBookPublisher(null));
    }

    @Test
    @DisplayName("Sollte Endlosschleife verhindern, wenn die Relation bereits im Set vorhanden ist")
    void testAddBookPublisher_AvoidLoop() {
      // Setup
      PublisherEntity publisher = PublisherEntity.builder().buildWithId();
      // Spy auf die Relation, um Interaktionen auf der Gegenseite (setPublisher) zu prüfen
      BookPublisherEntity relation = spy(BookPublisherEntity.builder().buildWithId());

      // Initiales Hinzufügen
      publisher.addBookPublisher(relation);
      clearInvocations(relation);

      // Act: Erneuter Aufruf mit identischer Relation (gleiche ID)
      publisher.addBookPublisher(relation);

      // Assert: Wegen this.bookPublishers.contains(bookPublisher) darf setPublisher nicht erneut gerufen werden
      verify(relation, never()).setPublisher(any());
      assertEquals(1, publisher.getBookPublishers().size());
    }

    @Test
    @DisplayName("Sollte Relation von altem Publisher entfernen, wenn sie neu zugewiesen wird")
    void testAddBookPublisher_TransferFromOtherPublisher() {
      // Setup
      PublisherEntity firstPublisher = PublisherEntity.builder().buildWithId();
      PublisherEntity secondPublisher = PublisherEntity.builder().buildWithId();
      BookPublisherEntity relation = BookPublisherEntity.builder().buildWithId();

      // Relation gehört zuerst zu Publisher 1
      firstPublisher.addBookPublisher(relation);
      assertTrue(firstPublisher.getBookPublishers().contains(relation));

      // Act: Publisher 2 fügt die Relation hinzu
      // White-Box: Dies triggert relation.setPublisher(secondPublisher), 
      // was intern firstPublisher.getBookPublishers().remove(relation) aufruft.
      secondPublisher.addBookPublisher(relation);

      // Assert
      assertFalse(firstPublisher.getBookPublishers().contains(relation),
          "Relation muss beim alten Publisher entfernt worden sein");
      assertTrue(secondPublisher.getBookPublishers().contains(relation),
          "Relation muss beim neuen Publisher registriert sein");
      assertEquals(secondPublisher, relation.getPublisher());
    }
  }
}
