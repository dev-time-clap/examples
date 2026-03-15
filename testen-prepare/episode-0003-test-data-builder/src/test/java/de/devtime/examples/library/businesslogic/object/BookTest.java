package de.devtime.examples.library.businesslogic.object;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import de.devtime.examples.library.persistence.entity.AuthorEntity;
import de.devtime.examples.library.persistence.entity.AuthorEntityTestData;
import de.devtime.examples.library.persistence.entity.BookEntity;
import de.devtime.examples.library.persistence.entity.BookEntityTestData;
import de.devtime.examples.library.persistence.entity.CustomerEntity;
import de.devtime.examples.library.persistence.entity.CustomerEntityTestData;
import de.devtime.examples.library.persistence.entity.PublisherEntity;
import de.devtime.examples.library.persistence.entity.PublisherEntityTestData;
import lombok.extern.slf4j.Slf4j;

@Slf4j
class BookTest {

  /*
   * 1. Entity mit Builder-Pattern ausstatten
   *    - @Builder an Klasse
   *    - protected constructor in der Builder Klasse
   * 2. TestDataBuilder Klasse erstellen
   *    - super fields übernehmen
   *    - buildWithId überschreiben und super fields setzen
   * 3. Problem mit fehlendem generischen Typen zeigen
   * 4. GenericBuilder Interface einführen
   *    - default and() Methode erstellen
   *    - An Builder in der Entity packen
   *    - Problem aus 3. besteht nicht mehr
   * 5. TestData Klasse erstellen
   *    - vordefinierte fachliche Szenarien pro Methode
   *    - Benutzung in Test-Methode zeigen
   *    - Vorteile: sehr flexibel | sehr gut lesbar | vordefinierte testdaten + überschreiben sehr einfach möglich
   * 6. KI Einsatz zeigen
   *    - Prompt in Datei vorbereiten
   *    - Übrige Klassen von KI generieren lassen
   *    - Von KI Demo Tests generieren lassen
   *
   * Verweis aufs nächste Video
   *  - Entity mit Referenzen
   *  - Builder-Pattern mit referenzierten Objekten
   */

  @Test
  void testOurNewTestDataBuilder() {
    BookEntity bookEntity = BookEntityTestData.create()
        .bookJustABookByMorrigan()
        .withId(UUID.randomUUID())
        .withIsOnLoan(true)
        .and()
        .buildWithId();

    log.info("bookEntity: {}", bookEntity);
  }

  @Test
  void testAuthorTestData() {
    // 1. Standard-Szenario direkt nutzen
    AuthorEntity king = AuthorEntityTestData.create()
        .authorStephenKing()
        .and()
        .buildWithId();
    log.info("Einfacher Author: {}", king);

    // 2. Szenario laden und einzelne Felder für den Test anpassen
    AuthorEntity modifiedKing = AuthorEntityTestData.create()
        .authorStephenKing()
        .withArtistName("Richard Bachman").and() // Pseudonym überschreiben
        .withId(UUID.randomUUID()) // Explizite ID statt Zufall
        .and()
        .buildWithId();
    log.info("Author mit überschriebenem Namen und ID: {}", modifiedKing);
  }

  @Test
  void testCustomerTestData() {
    // 1. Erstellen eines Kunden mit vordefinierten Büchern (Komposition)
    BookEntity book = BookEntityTestData.create()
        .bookJustABookByMorrigan()
        .buildWithId();

    CustomerEntity customerWithBooks = CustomerEntityTestData.create()
        .customerWithBooks(book)
        .withFirstName("Bücherwurm")
        .and()
        .buildWithId();
    log.info("Kunde mit direkt zugewiesenem Buch: {}", customerWithBooks);

    // 2. Minimalistischer Kunde ohne spezielles Szenario (nur Builder-Nutzen)
    CustomerEntity ghost = CustomerEntityTestData.create()
        .withVersion(1)
        .withLastName("Unbekannt")
        .and()
        .buildWithId();
    log.info("Minimaler Kunde: {}", ghost);
  }

  @Test
  void testPublisherTestData() {
    // 1. Mehrere Publisher in einem Stream erzeugen (nützlich für Massendaten)
    List<PublisherEntity> publishers = Stream.of("Addison-Wesley", "Hanser", "Packt")
        .map(name -> PublisherEntityTestData.create()
            .withName(name)
            .and()
            .buildWithId())
        .toList();
    log.info("Liste von Publishern: {}", publishers);

    // 2. Nutzung eines vordefinierten Publishers und Version-Check
    PublisherEntity springer = PublisherEntityTestData.create()
        .publisherSpringer()
        .withVersion(5)
        .and()
        .buildWithId();
    log.info("Publisher mit Versionsnummer: {}", springer);
  }
}
