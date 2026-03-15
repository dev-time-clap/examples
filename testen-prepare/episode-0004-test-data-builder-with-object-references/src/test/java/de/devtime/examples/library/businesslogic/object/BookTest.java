package de.devtime.examples.library.businesslogic.object;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import de.devtime.examples.library.persistence.entity.AdditionalBookDataEntityTestDataProvider;
import de.devtime.examples.library.persistence.entity.AuthorEntity;
import de.devtime.examples.library.persistence.entity.AuthorEntityTestDataProvider;
import de.devtime.examples.library.persistence.entity.BookEntity;
import de.devtime.examples.library.persistence.entity.BookEntityTestDataProvider;
import de.devtime.examples.library.persistence.entity.CustomerEntity;
import de.devtime.examples.library.persistence.entity.CustomerEntityTestDataProvider;
import de.devtime.examples.library.persistence.entity.PublisherEntity;
import de.devtime.examples.library.persistence.entity.PublisherEntityTestDataProvider;
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
   *    - Übrige Klassen von KI generieren lassen
   *    - Von KI Demo Tests generieren lassen
   *
   * Verweis aufs nächste Video
   *  - Entity mit Referenzen
   *  - Builder-Pattern mit referenzierten Objekten
   */

  @Test
  void testOurNewTestDataBuilderWithReferences() {
    BookEntity book1 = BookEntityTestDataProvider.create()
        .bookByMorriganWithTitleTestingwithJUnitAndCo()
        .buildWithReferences(true);
    log.info("book1: {}", book1);

    BookEntity book2 = BookEntityTestDataProvider.create()
        .bookByMorriganWithTitleTestingwithJUnitAndCo()
        .withIsbn("ISBN-0816")
        .and()
        .buildWithReferences(true);
    log.info("book2: {}", book2);

    BookEntity book3 = BookEntityTestDataProvider.create()
        .bookByMorriganWithTitleTestingwithJUnitAndCo()
        .withCustomer(CustomerEntityTestDataProvider::customerMaxMustermann)
        .withIsOnLoan(true)
        .withIsbn("ISBN-0816")
        .and()
        .buildWithReferences(true);
    log.info("book3: {}", book3);

    BookEntity book4 = BookEntityTestDataProvider.create()
        .bookByMorriganWithTitleTestingwithJUnitAndCo()
        .withCustomer(customer -> customer
            .customerMaxMustermann()
            .withNumber("knd-1234"))
        .withIsOnLoan(true)
        .withIsbn("ISBN-0816")
        .and()
        .buildWithReferences(true);
    log.info("book4: {}", book4);

    CustomerEntity customer = CustomerEntityTestDataProvider.create()
        .customerMaxMustermann()
        .withLoanedBook(book -> book
            .bookByMorriganWithTitleLombokHowTo()
            .withIsOnLoan(true))
        .withLoanedBook(book -> book
            .bookByMorriganWithTitleSpringBootPrototyping()
            .withIsOnLoan(true))
        .withLoanedBook(book -> book
            .bookByMorriganWithTitleTestingwithJUnitAndCo()
            .withIsOnLoan(true))
        .buildWithReferences(true);
    log.info("customer: {}", customer);
    customer.getLoanedBooks().forEach(book -> log.info("customer book: {}", book));
  }

  @Test
  void testOurNewTestDataBuilder() {
    BookEntity bookEntity = BookEntityTestDataProvider.create()
        .withAdditionalData(AdditionalBookDataEntityTestDataProvider::thrillerDetails)
        .withAuthor(AuthorEntityTestDataProvider::authorStephenKing)
        .withCustomer(CustomerEntityTestDataProvider::customerMaxMustermann)
        .withId(UUID.randomUUID())
        .withIsOnLoan(true)
        .and()
        .buildWithReferences();

    log.info("bookEntity: {}", bookEntity);
  }

  @Test
  void testAuthorTestData() {
    // 1. Standard-Szenario direkt nutzen
    AuthorEntity king = AuthorEntityTestDataProvider.create()
        .authorStephenKing()
        .and()
        .buildWithId();
    log.info("Einfacher Author: {}", king);

    // 2. Szenario laden und einzelne Felder für den Test anpassen
    AuthorEntity modifiedKing = AuthorEntityTestDataProvider.create()
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
    BookEntity book = BookEntityTestDataProvider.create()
        .bookByMorriganWithTitleTestingwithJUnitAndCo()
        .buildWithId();

    CustomerEntity customerWithBooks = CustomerEntityTestDataProvider.create()
        .customerWithBooks(book)
        .withFirstName("Bücherwurm")
        .and()
        .buildWithId();
    log.info("Kunde mit direkt zugewiesenem Buch: {}", customerWithBooks);

    // 2. Minimalistischer Kunde ohne spezielles Szenario (nur Builder-Nutzen)
    CustomerEntity ghost = CustomerEntityTestDataProvider.create()
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
        .map(name -> PublisherEntityTestDataProvider.create()
            .withName(name)
            .and()
            .buildWithId())
        .toList();
    log.info("Liste von Publishern: {}", publishers);

    // 2. Nutzung eines vordefinierten Publishers und Version-Check
    PublisherEntity springer = PublisherEntityTestDataProvider.create()
        .publisherSpringer()
        .withVersion(5)
        .and()
        .buildWithId();
    log.info("Publisher mit Versionsnummer: {}", springer);
  }
}
