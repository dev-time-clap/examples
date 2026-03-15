package de.devtime.examples.library.persistence.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class BookPublisherEntityTest {

  @Nested
  @DisplayName("Tests für setPublisher")
  class PublisherLinkageTests {

    @Test
    @DisplayName("Sollte Publisher verknüpfen und bei altem Publisher entfernen")
    void testSetPublisher_LinkageAndCleanup() {
      BookPublisherEntity relation = BookPublisherEntity.builder().buildWithId();
      PublisherEntity oldPublisher = PublisherEntity.builder().withName("Old").buildWithId();
      PublisherEntity newPublisher = PublisherEntity.builder().withName("New").buildWithId();

      // Initialer Publisher
      relation.setPublisher(oldPublisher);
      assertTrue(oldPublisher.getBookPublishers().contains(relation));

      // Wechsel zu neuem Publisher
      relation.setPublisher(newPublisher);

      assertFalse(oldPublisher.getBookPublishers().contains(relation), "Alte Verknüpfung muss gelöscht sein");
      assertTrue(newPublisher.getBookPublishers().contains(relation), "Neue Verknüpfung muss bestehen");
      assertEquals(newPublisher, relation.getPublisher());
    }

    @Test
    @DisplayName("Sollte Endlosschleife verhindern, wenn Publisher identisch ist")
    void testSetPublisher_AvoidLoop() {
      BookPublisherEntity relation = BookPublisherEntity.builder().buildWithId();
      PublisherEntity publisher = spy(PublisherEntity.builder().buildWithId());

      relation.setPublisher(publisher);
      clearInvocations(publisher);

      // Zweiter Aufruf mit gleichem Objekt (gleiche ID)
      relation.setPublisher(publisher);

      // White-Box: getBookPublishers darf nicht erneut gerufen werden (Abbruch durch Objects.equals)
      verify(publisher, never()).getBookPublishers();
    }
  }

  @Nested
  @DisplayName("Tests für setBook")
  class BookLinkageTests {

    @Test
    @DisplayName("Sollte Buch verknüpfen und bei altem Buch entfernen")
    void testSetBook_LinkageAndCleanup() {
      BookPublisherEntity relation = BookPublisherEntity.builder().buildWithId();
      BookEntity oldBook = BookEntity.builder().buildWithId();
      BookEntity newBook = BookEntity.builder().buildWithId();

      // Initiales Buch
      relation.setBook(oldBook);
      assertTrue(oldBook.getBookPublishers().contains(relation));

      // Wechsel zu neuem Buch
      relation.setBook(newBook);

      assertFalse(oldBook.getBookPublishers().contains(relation), "Alte Verknüpfung beim Buch muss gelöscht sein");
      assertTrue(newBook.getBookPublishers().contains(relation), "Neue Verknüpfung beim Buch muss bestehen");
      assertEquals(newBook, relation.getBook());
    }

    @Test
    @DisplayName("Sollte Endlosschleife verhindern, wenn Buch identisch ist")
    void testSetBook_AvoidLoop() {
      BookPublisherEntity relation = BookPublisherEntity.builder().buildWithId();
      BookEntity book = spy(BookEntity.builder().buildWithId());

      relation.setBook(book);
      clearInvocations(book);

      // Zweiter Aufruf
      relation.setBook(book);

      verify(book, never()).getBookPublishers();
    }

    @Test
    @DisplayName("Sollte Feld auf null setzen und aus Buch-Liste entfernen")
    void testSetBook_Null() {
      BookPublisherEntity relation = BookPublisherEntity.builder().buildWithId();
      BookEntity book = BookEntity.builder().buildWithId();
      relation.setBook(book);

      // Act
      relation.setBook(null);

      // Assert
      assertNull(relation.getBook());
      assertFalse(book.getBookPublishers().contains(relation));
    }
  }
}
