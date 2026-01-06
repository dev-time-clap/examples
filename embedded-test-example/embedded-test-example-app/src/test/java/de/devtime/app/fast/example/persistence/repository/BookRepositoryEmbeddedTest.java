package de.devtime.app.fast.example.persistence.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import de.devtime.app.example.persistence.entity.BookEntity;
import de.devtime.test.AbstractEmbeddedTest;

class BookRepositoryEmbeddedTest extends AbstractEmbeddedTest {

  @Test
  void findByIsbnShouldReturnAnEmptyOptionalWhenNoBookWasFound() {
    final Optional<BookEntity> actualResult = this.bookRepo.findByIsbn("isbn-0815");

    assertThat(actualResult).isEmpty();
  }

  @Test
  void findByIsbnShouldReturnAnOptionalWithTheCorrectBookWhenIsbnMatches() {
    final String expectedIsbn = "isbn-4711";
    final String expectedTitle = "Just a book";
    final BookEntity bookEntity = BookEntity.builder()
        .withIsbn(expectedIsbn)
        .withIsOnLoan(false)
        .withTitle(expectedTitle)
        .build();
    this.bookRepo.save(bookEntity);

    final Optional<BookEntity> actualResult = this.bookRepo.findByIsbn(expectedIsbn);

    assertThat(actualResult).isNotEmpty();
    final BookEntity actualBookEntity = actualResult.get();
    assertThat(actualBookEntity.getIsbn()).isEqualTo(expectedIsbn);
    assertThat(actualBookEntity.getTitle()).isEqualTo(expectedTitle);
  }

  @Test
  void findAllByTitleShouldReturnAnEmptyListWhenNoBooksWereFound() {
    final List<BookEntity> actualResult = this.bookRepo.findAllByTitle("Just a book");

    assertThat(actualResult).isEmpty();
  }

  @Test
  void findAllByTitleShouldOnlyReturnMatchingBooks() {
    this.bookRepo.save(BookEntity.builder()
        .withIsbn("isbn-4711")
        .withIsOnLoan(false)
        .withTitle("Just a book")
        .build());
    this.bookRepo.save(BookEntity.builder()
        .withIsbn("isbn-4712")
        .withIsOnLoan(false)
        .withTitle("Just a second book")
        .build());

    final List<BookEntity> actualResult = this.bookRepo.findAllByTitle("Just a book");

    assertThat(actualResult).hasSize(1);
  }

  @Test
  void findAllByIsOnLoanShouldReturnAllNotLoanedOutBooks() {
    this.bookRepo.save(BookEntity.builder()
        .withIsbn("isbn-4711")
        .withIsOnLoan(false)
        .withTitle("Just a book")
        .build());
    this.bookRepo.save(BookEntity.builder()
        .withIsbn("isbn-4712")
        .withIsOnLoan(true)
        .withTitle("Just a second book")
        .build());

    final List<BookEntity> actualResult = this.bookRepo.findAllByIsOnLoan(false);

    assertThat(actualResult).hasSize(1);
    final BookEntity bookEntity = actualResult.get(0);
    assertThat(bookEntity.getIsbn()).isEqualTo("isbn-4711");
  }

  @Test
  void findAllByIsOnLoanShouldReturnAllLoanedOutBooks() {
    this.bookRepo.save(BookEntity.builder()
        .withIsbn("isbn-4711")
        .withIsOnLoan(false)
        .withTitle("Just a book")
        .build());
    this.bookRepo.save(BookEntity.builder()
        .withIsbn("isbn-4712")
        .withIsOnLoan(true)
        .withTitle("Just a second book")
        .build());

    final List<BookEntity> actualResult = this.bookRepo.findAllByIsOnLoan(true);

    assertThat(actualResult).hasSize(1);
    final BookEntity bookEntity = actualResult.get(0);
    assertThat(bookEntity.getIsbn()).isEqualTo("isbn-4712");
  }
}
