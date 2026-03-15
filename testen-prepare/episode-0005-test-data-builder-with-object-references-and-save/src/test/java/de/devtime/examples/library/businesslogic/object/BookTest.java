package de.devtime.examples.library.businesslogic.object;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

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
@SpringBootTest
class BookTest {

  /*
   * 1. tbd
   */

  @Test
  void testOurNewTestDataBuilderWithReferences2() {
    AdditionalBookDataEntityTestDataProvider.create()
        .withBook(BookEntityTestDataProvider::bookByMorriganWithTitleLombokHowTo)
        .bookDetailsForJustABookByMorrigan()
        .buildWithReferencesAndSave();
  }

  @Test
  void testOurNewTestDataBuilderWithReferences() {
    BookEntity book = BookEntityTestDataProvider.create()
        .withAdditionalData(AdditionalBookDataEntityTestDataProvider::bookDetailsForJustABookByMorrigan)
        .withAuthor(author -> author.anonymousAuthor().withArtistName("1"))
        .withAuthor(author -> author.anonymousAuthor().withArtistName("2"))
        .withAuthor(author -> author.anonymousAuthor().withArtistName("3"))
        .withAuthor(author -> author.anonymousAuthor().withArtistName("4"))
        .withAuthor(author -> author.anonymousAuthor().withArtistName("5"))
        .withAuthor(author -> author.anonymousAuthor().withArtistName("6"))
        .withAuthor(author -> author.anonymousAuthor().withArtistName("7"))
        .withAuthor(author -> author.anonymousAuthor().withArtistName("8"))
        .withAuthor(author -> author.anonymousAuthor().withArtistName("9"))
        .withAuthor(author -> author.anonymousAuthor().withArtistName("10"))
        .withAuthor(author -> author.anonymousAuthor().withArtistName("11"))
        .withAuthor(author -> author.anonymousAuthor().withArtistName("12"))
        .withAuthor(author -> author.anonymousAuthor().withArtistName("13"))
        .withAuthor(author -> author.anonymousAuthor().withArtistName("14"))
        .withAuthor(author -> author.anonymousAuthor().withArtistName("15"))
        .withAuthor(author -> author.anonymousAuthor().withArtistName("16"))
        .withAuthor(author -> author.anonymousAuthor().withArtistName("17"))
        .withAuthor(author -> author.anonymousAuthor().withArtistName("18"))
        .withAuthor(author -> author.anonymousAuthor().withArtistName("19"))
        .withAuthor(author -> author.anonymousAuthor().withArtistName("20"))
        .withAuthor(author -> author.anonymousAuthor().withArtistName("20"))
        .withAuthor(author -> author.anonymousAuthor().withArtistName("21"))
        .withAuthor(author -> author.anonymousAuthor().withArtistName("22"))
        .withAuthor(author -> author.anonymousAuthor().withArtistName("23"))
        .withAuthor(author -> author.anonymousAuthor().withArtistName("24"))
        .withAuthor(author -> author.anonymousAuthor().withArtistName("25"))
        .withAuthor(author -> author.anonymousAuthor().withArtistName("26"))
        .withAuthor(author -> author.anonymousAuthor().withArtistName("27"))
        .withAuthor(author -> author.anonymousAuthor().withArtistName("28"))
        .withAuthor(author -> author.anonymousAuthor().withArtistName("29"))
        .withAuthor(author -> author.anonymousAuthor().withArtistName("30"))
        .withIsbn("ISBN-0815")
        .withTitle("Test")
        .and()
        .buildWithReferencesAndSave();
    log.info("book: {}", book);
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
        .build()
        .generateId();
    log.info("Einfacher Author: {}", king);

    // 2. Szenario laden und einzelne Felder für den Test anpassen
    AuthorEntity modifiedKing = AuthorEntityTestDataProvider.create()
        .authorStephenKing()
        .withArtistName("Richard Bachman").and() // Pseudonym überschreiben
        .withId(UUID.randomUUID()) // Explizite ID statt Zufall
        .and()
        .build()
        .generateId();
    log.info("Author mit überschriebenem Namen und ID: {}", modifiedKing);
  }

  @Test
  void testCustomerTestData() {
    // 1. Erstellen eines Kunden mit vordefinierten Büchern (Komposition)
    BookEntity book = BookEntityTestDataProvider.create()
        .bookByMorriganWithTitleTestingwithJUnitAndCo()
        .buildOnlyThis();

    CustomerEntity customerWithBooks = CustomerEntityTestDataProvider.create()
        .customerWithBooks(book)
        .withFirstName("Bücherwurm")
        .and()
        .build()
        .generateId();
    log.info("Kunde mit direkt zugewiesenem Buch: {}", customerWithBooks);

    // 2. Minimalistischer Kunde ohne spezielles Szenario (nur Builder-Nutzen)
    CustomerEntity ghost = CustomerEntityTestDataProvider.create()
        .withVersion(1)
        .withLastName("Unbekannt")
        .and()
        .build()
        .generateId();
    log.info("Minimaler Kunde: {}", ghost);
  }

  @Test
  void testPublisherTestData() {
    // 1. Mehrere Publisher in einem Stream erzeugen (nützlich für Massendaten)
    List<PublisherEntity> publishers = Stream.of("Addison-Wesley", "Hanser", "Packt")
        .map(name -> PublisherEntityTestDataProvider.create()
            .withName(name)
            .and()
            .build()
            .generateId())
        .toList();
    log.info("Liste von Publishern: {}", publishers);

    // 2. Nutzung eines vordefinierten Publishers und Version-Check
    PublisherEntity springer = PublisherEntityTestDataProvider.create()
        .publisherSpringer()
        .withVersion(5)
        .and()
        .build()
        .generateId();
    log.info("Publisher mit Versionsnummer: {}", springer);
  }
}
