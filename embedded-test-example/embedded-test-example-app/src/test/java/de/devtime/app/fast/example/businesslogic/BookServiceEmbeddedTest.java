package de.devtime.app.fast.example.businesslogic;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import de.devtime.app.example.persistence.entity.BookEntity;
import de.devtime.app.example.persistence.entity.LoanActivityEntity;
import de.devtime.app.example.persistence.enumeration.LoanActivity;
import de.devtime.test.AbstractEmbeddedTest;
import de.devtime.test.SkipResetDatabase;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@SkipResetDatabase
class BookServiceEmbeddedTest extends AbstractEmbeddedTest {

  @Test
  void registerBookShouldThrowCveWhenGivenIsbnIsNull() {
    doReturn(null).when(this.bookRepo).save(any());

    final ConstraintViolationException actualException = assertThrows(ConstraintViolationException.class,
        () -> this.bookService.registerBook(b -> b
            .withIsbn(null)
            .withTitle("Just a book")));

    final Set<ConstraintViolation<?>> constraintViolations = actualException.getConstraintViolations();
    assertThat(constraintViolations).hasSize(1);
    final ConstraintViolation<?> violation = constraintViolations.iterator().next();
    assertThat(violation.getPropertyPath().toString()).contains("isbn");
    assertThat(violation.getMessageTemplate()).contains("NotNull");
  }

  @Test
  void registerBookShouldThrowCveWhenGivenTitleIsNull() {
    doReturn(null).when(this.bookRepo).save(any());

    final ConstraintViolationException actualException = assertThrows(ConstraintViolationException.class,
        () -> this.bookService.registerBook(b -> b
            .withIsbn("isbn-4711")
            .withTitle(null)));

    final Set<ConstraintViolation<?>> constraintViolations = actualException.getConstraintViolations();
    assertThat(constraintViolations).hasSize(1);
    final ConstraintViolation<?> violation = constraintViolations.iterator().next();
    assertThat(violation.getPropertyPath().toString()).contains("title");
    assertThat(violation.getMessageTemplate()).contains("NotNull");
  }

  @Test
  void registerNewBookShouldDelegateCorrectEntityToBeSaved() {
    // Given I have all data to register a new book
    final String expectedIsbn = "isbn-4711";
    final String expectedTitle = "Title";
    doReturn(BookEntity.builder()
        .withId(UUID.randomUUID().toString())
        .withIsbn(expectedIsbn)
        .withIsOnLoan(true)
        .withTitle(expectedTitle)
        .build())
            .when(this.bookRepo).save(any());

    // When I try to register a new book
    this.bookService.registerBook(b -> b
        .withIsbn(expectedIsbn)
        .withTitle(expectedTitle));

    // Then all given data are passed to the repository
    final ArgumentCaptor<BookEntity> captor = ArgumentCaptor.forClass(BookEntity.class);
    verify(this.bookRepo).save(captor.capture());
    final BookEntity actualEntity = captor.getValue();
    assertThat(actualEntity).isNotNull();
    assertThat(actualEntity.getId()).isNull();
    assertThat(actualEntity.getIsbn()).isEqualTo(expectedIsbn);
    assertThat(actualEntity.getTitle()).isEqualTo(expectedTitle);
  }

  @Test
  void lendBookShouldThrowCveWhenGivenIsbnIsNull() {
    doReturn(null).when(this.bookRepo).save(any());

    final ConstraintViolationException actualException = assertThrows(ConstraintViolationException.class,
        () -> this.bookService.lendBook(b -> b
            .withIsbn(null)
            .withLoanDate(LocalDate.now())));

    final Set<ConstraintViolation<?>> constraintViolations = actualException.getConstraintViolations();
    assertThat(constraintViolations).hasSize(1);
    final ConstraintViolation<?> violation = constraintViolations.iterator().next();
    assertThat(violation.getPropertyPath().toString()).contains("isbn");
    assertThat(violation.getMessageTemplate()).contains("NotNull");
  }

  @Test
  void lendBookShouldThrowCveWhenGivenLoanDataIsNull() {
    doReturn(null).when(this.bookRepo).save(any());

    final ConstraintViolationException actualException = assertThrows(ConstraintViolationException.class,
        () -> this.bookService.lendBook(b -> b
            .withIsbn("isbn-4711")
            .withLoanDate(null)));

    final Set<ConstraintViolation<?>> constraintViolations = actualException.getConstraintViolations();
    assertThat(constraintViolations).hasSize(1);
    final ConstraintViolation<?> violation = constraintViolations.iterator().next();
    assertThat(violation.getPropertyPath().toString()).contains("loanDate");
    assertThat(violation.getMessageTemplate()).contains("NotNull");
  }

  @Test
  void lendBookShouldThrowIseWhenBookDoesNotExist() {
    final IllegalStateException actualException = assertThrows(IllegalStateException.class,
        () -> this.bookService.lendBook(b -> b
            .withIsbn("0815")
            .withLoanDate(LocalDate.now())));

    assertThat(actualException.getMessage()).contains("The book", "0815", "does not exist");
  }

  @Test
  void lendBookShouldThrowIseWhenBookIsAlreadyLend() {
    final String isbn = "isbn-4711";
    final LocalDate loanData = LocalDate.now();
    doReturn(Optional.of(BookEntity.builder()
        .withId(UUID.randomUUID().toString())
        .withIsbn(isbn)
        .withIsOnLoan(true)
        .withTitle("Just a book")
        .build()))
            .when(this.bookRepo).findByIsbn(any());

    final IllegalStateException actualException = assertThrows(IllegalStateException.class,
        () -> this.bookService.lendBook(b -> b
            .withIsbn(isbn)
            .withLoanDate(loanData)));

    assertThat(actualException.getMessage()).contains("The book", isbn, "is already loaned out");
  }

  @Test
  void lendBookShouldDelegateCorrectEntityToBeSaved() {
    // Given I have all data to lend a book
    final String expectedIsbn = "isbn-4711";
    final LocalDate expectedLoanDate = LocalDate.now();
    final BookEntity bookEntity = BookEntity.builder()
        .withId(UUID.randomUUID().toString())
        .withIsbn(expectedIsbn)
        .withIsOnLoan(false)
        .withTitle("Just a book")
        .build();
    final LoanActivityEntity loanActivityEntity = LoanActivityEntity.builder()
        .withId(UUID.randomUUID().toString())
        .withIsbn(expectedIsbn)
        .withLoanActivity(LoanActivity.LOAN_BOOK)
        .withLoanDate(expectedLoanDate)
        .build();
    doReturn(bookEntity).when(this.bookRepo).save(any());
    doReturn(loanActivityEntity).when(this.loanActivityRepo).save(any());
    doReturn(Optional.of(bookEntity)).when(this.bookRepo).findByIsbn(any());

    // When I try to lend a new book
    this.bookService.lendBook(b -> b
        .withIsbn(expectedIsbn)
        .withLoanDate(expectedLoanDate));

    // Then all given data are passed to the repository
    final ArgumentCaptor<BookEntity> bookEntityCaptor = ArgumentCaptor
        .forClass(BookEntity.class);
    verify(this.bookRepo).save(bookEntityCaptor.capture());
    final BookEntity actualBookEntity = bookEntityCaptor.getValue();
    assertThat(actualBookEntity).isNotNull();
    assertThat(actualBookEntity.isOnLoan()).isTrue();
    final ArgumentCaptor<LoanActivityEntity> loanActivityEntityCaptor = ArgumentCaptor
        .forClass(LoanActivityEntity.class);
    verify(this.loanActivityRepo).save(loanActivityEntityCaptor.capture());
    final LoanActivityEntity actualLoanActivityEntity = loanActivityEntityCaptor.getValue();
    assertThat(actualLoanActivityEntity).isNotNull();
    assertThat(actualLoanActivityEntity.getId()).isNull();
    assertThat(actualLoanActivityEntity.getIsbn()).isEqualTo(expectedIsbn);
    assertThat(actualLoanActivityEntity.getLoanDate()).isEqualTo(expectedLoanDate);
  }

  @Test
  void returnBookShouldThrowCveWhenGivenIsbnIsNull() {
    doReturn(null).when(this.bookRepo).save(any());

    final ConstraintViolationException actualException = assertThrows(ConstraintViolationException.class,
        () -> this.bookService.returnBook(b -> b
            .withIsbn(null)
            .withReturnDate(LocalDate.now())));

    final Set<ConstraintViolation<?>> constraintViolations = actualException.getConstraintViolations();
    assertThat(constraintViolations).hasSize(1);
    final ConstraintViolation<?> violation = constraintViolations.iterator().next();
    assertThat(violation.getPropertyPath().toString()).contains("isbn");
    assertThat(violation.getMessageTemplate()).contains("NotNull");
  }

  @Test
  void returnBookShouldThrowCveWhenGivenReturnDataIsNull() {
    doReturn(null).when(this.bookRepo).save(any());

    final ConstraintViolationException actualException = assertThrows(ConstraintViolationException.class,
        () -> this.bookService.returnBook(b -> b
            .withIsbn("isbn-4711")
            .withReturnDate(null)));

    final Set<ConstraintViolation<?>> constraintViolations = actualException.getConstraintViolations();
    assertThat(constraintViolations).hasSize(1);
    final ConstraintViolation<?> violation = constraintViolations.iterator().next();
    assertThat(violation.getPropertyPath().toString()).contains("returnDate");
    assertThat(violation.getMessageTemplate()).contains("NotNull");
  }

  @Test
  void returnBookShouldThrowIseWhenBookDoesNotExist() {
    final IllegalStateException actualException = assertThrows(IllegalStateException.class,
        () -> this.bookService.returnBook(b -> b
            .withIsbn("0815")
            .withReturnDate(LocalDate.now())));

    assertThat(actualException.getMessage()).contains("The book", "0815", "does not exist");
  }

  @Test
  void returnBookShouldThrowIseWhenBookIsNotLoanedOutAtAll() {
    final String isbn = "isbn-4711";
    final LocalDate returnData = LocalDate.now();
    doReturn(Optional.of(BookEntity.builder()
        .withId(UUID.randomUUID().toString())
        .withIsbn(isbn)
        .withIsOnLoan(false)
        .withTitle("Just a book")
        .build()))
            .when(this.bookRepo).findByIsbn(any());

    final IllegalStateException actualException = assertThrows(IllegalStateException.class,
        () -> this.bookService.returnBook(b -> b
            .withIsbn(isbn)
            .withReturnDate(returnData)));

    assertThat(actualException.getMessage()).contains("The book", isbn, "is not loaned out at all");
  }

  @Test
  void returnBookShouldDelegateCorrectEntityToBeSaved() {
    // Given I have all data to return a book
    final String expectedIsbn = "isbn-4711";
    final LocalDate expectedReturnDate = LocalDate.now();
    final BookEntity bookEntity = BookEntity.builder()
        .withId(UUID.randomUUID().toString())
        .withIsbn(expectedIsbn)
        .withIsOnLoan(true)
        .withTitle("Just a book")
        .build();
    final LoanActivityEntity loanActivityEntity = LoanActivityEntity.builder()
        .withId(UUID.randomUUID().toString())
        .withIsbn(expectedIsbn)
        .withLoanActivity(LoanActivity.RETURN_BOOK)
        .withReturnDate(expectedReturnDate)
        .build();
    doReturn(bookEntity).when(this.bookRepo).save(any());
    doReturn(loanActivityEntity).when(this.loanActivityRepo).save(any());
    doReturn(Optional.of(bookEntity)).when(this.bookRepo).findByIsbn(any());

    // When I try to return a book
    this.bookService.returnBook(b -> b
        .withIsbn(expectedIsbn)
        .withReturnDate(expectedReturnDate));

    // Then all given data are passed to the repository
    final ArgumentCaptor<BookEntity> bookEntityCaptor = ArgumentCaptor
        .forClass(BookEntity.class);
    verify(this.bookRepo).save(bookEntityCaptor.capture());
    final BookEntity actualBookEntity = bookEntityCaptor.getValue();
    assertThat(actualBookEntity).isNotNull();
    assertThat(actualBookEntity.isOnLoan()).isFalse();
    final ArgumentCaptor<LoanActivityEntity> loanActivityEntityCaptor = ArgumentCaptor
        .forClass(LoanActivityEntity.class);
    verify(this.loanActivityRepo).save(loanActivityEntityCaptor.capture());
    final LoanActivityEntity actualLoanActivityEntity = loanActivityEntityCaptor.getValue();
    assertThat(actualLoanActivityEntity).isNotNull();
    assertThat(actualLoanActivityEntity.getId()).isNull();
    assertThat(actualLoanActivityEntity.getIsbn()).isEqualTo(expectedIsbn);
    assertThat(actualLoanActivityEntity.getReturnDate()).isEqualTo(expectedReturnDate);
  }
}
