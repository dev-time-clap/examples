package de.devtime.app.fast.example.api;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Test;

import de.devtime.app.example.api.contract.data.BookData;
import de.devtime.app.example.api.contract.data.BookLoanData;
import de.devtime.app.example.api.contract.data.BookRegistrationData;
import de.devtime.app.example.api.contract.data.BookReturnData;
import de.devtime.app.example.api.contract.dto.BookLoanRequestDto;
import de.devtime.app.example.api.contract.dto.BookRegistrationRequestDto;
import de.devtime.app.example.api.contract.dto.BookResponseDto;
import de.devtime.app.example.api.contract.dto.BookReturnRequestDto;
import de.devtime.app.example.persistence.entity.BookEntity;
import de.devtime.app.example.persistence.entity.LoanActivityEntity;
import de.devtime.test.AbstractEmbeddedTest;
import de.devtime.test.ResetDatabase;
import de.devtime.test.SkipResetDatabase;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@SkipResetDatabase
class BookRestControllerEmbeddedTest extends AbstractEmbeddedTest {

  @Test
  void registerBookShouldThrowCveWhenGivenRequestDtoIsNull() {
    // Given the request dto is null
    final BookRegistrationRequestDto requestDto = null;

    // When I try to register a new book with that data
    final ConstraintViolationException actualException = assertThrows(ConstraintViolationException.class,
        () -> this.bookRestController.registerBook(requestDto));

    // Then I receive an error indicating that required information is missing
    final Set<ConstraintViolation<?>> constraintViolations = actualException.getConstraintViolations();
    assertThat(constraintViolations).hasSize(1);
    final ConstraintViolation<?> violation = constraintViolations.iterator().next();
    assertThat(violation.getPropertyPath().toString()).contains("registerBook.requestDto");
    assertThat(violation.getMessageTemplate()).contains("NotNull");
  }

  @Test
  void registerBookShouldThrowCveWhenGivenRegistrationDtoIsNull() {
    // Given the payload of the request dto is null
    final BookRegistrationData bookRegistrationData = null;
    final BookRegistrationRequestDto requestDto = new BookRegistrationRequestDto(bookRegistrationData);

    // When I try to register a new book with that data
    final ConstraintViolationException actualException = assertThrows(ConstraintViolationException.class,
        () -> this.bookRestController.registerBook(requestDto));

    // Then I receive an error indicating that required information is missing
    final Set<ConstraintViolation<?>> constraintViolations = actualException.getConstraintViolations();
    assertThat(constraintViolations).hasSize(1);
    final ConstraintViolation<?> violation = constraintViolations.iterator().next();
    assertThat(violation.getPropertyPath().toString()).contains("registerBook.requestDto.registrationData");
    assertThat(violation.getMessageTemplate()).contains("NotNull");
  }

  @Test
  void registerBookShouldThrowCveWhenGivenIsbnIsNull() {
    // Given the ISBN of a book is null
    final BookRegistrationData bookRegistrationData = BookRegistrationData.builder()
        .withIsbn(null)
        .withTitle("A good book")
        .build();
    final BookRegistrationRequestDto requestDto = new BookRegistrationRequestDto(bookRegistrationData);

    // When I try to register a new book with that data
    final ConstraintViolationException actualException = assertThrows(ConstraintViolationException.class,
        () -> this.bookRestController.registerBook(requestDto));

    // Then I receive an error indicating that required information is missing
    final Set<ConstraintViolation<?>> constraintViolations = actualException.getConstraintViolations();
    assertThat(constraintViolations).hasSize(1);
    final ConstraintViolation<?> violation = constraintViolations.iterator().next();
    assertThat(violation.getPropertyPath().toString()).contains("registerBook.requestDto.registrationData.isbn");
    assertThat(violation.getMessageTemplate()).contains("NotNull");
  }

  @Test
  void registerBookShouldThrowCveWhenGivenTitleIsNull() {
    // Given the title of a book is null
    final BookRegistrationData bookRegistrationData = BookRegistrationData.builder()
        .withIsbn("isbn-4711")
        .withTitle(null)
        .build();
    final BookRegistrationRequestDto requestDto = new BookRegistrationRequestDto(bookRegistrationData);

    // When I try to register a new book with that data
    final ConstraintViolationException actualException = assertThrows(ConstraintViolationException.class,
        () -> this.bookRestController.registerBook(requestDto));

    // Then I receive an error indicating that required information is missing
    final Set<ConstraintViolation<?>> constraintViolations = actualException.getConstraintViolations();
    assertThat(constraintViolations).hasSize(1);
    final ConstraintViolation<?> violation = constraintViolations.iterator().next();
    assertThat(violation.getPropertyPath().toString()).contains("registerBook.requestDto.registrationData.title");
    assertThat(violation.getMessageTemplate()).contains("NotNull");
  }

  @Test
  @ResetDatabase
  void registerBookShouldSaveANewBookIntoTheDbWithGivenData() {
    // Given I have all required data of a new book
    final BookRegistrationData bookRegistrationData = BookRegistrationData.builder()
        .withIsbn("isbn-4711")
        .withTitle("A good book")
        .build();
    final BookRegistrationRequestDto requestDto = new BookRegistrationRequestDto(bookRegistrationData);

    // When I try to register a new book with that data
    this.bookRestController.registerBook(requestDto);

    // Then this book is registered in the system with the given data
    final List<BookEntity> allBooks = this.bookRepo.findAll();
    assertThat(allBooks).hasSize(1);
    final BookEntity bookEntity = allBooks.get(0);
    assertThat(bookEntity.getIsbn()).isEqualTo(bookRegistrationData.getIsbn());
    assertThat(bookEntity.getTitle()).isEqualTo(bookRegistrationData.getTitle());
  }

  @Test
  @ResetDatabase
  void registerBookShouldReturnCorrectData() {
    // Given I have all required data of a new book
    final BookRegistrationData bookRegistrationData = BookRegistrationData.builder()
        .withIsbn("isbn-4711")
        .withTitle("A good book")
        .build();
    final BookRegistrationRequestDto requestDto = new BookRegistrationRequestDto(bookRegistrationData);

    // When I try to register a new book with that data
    final BookResponseDto responseDto = this.bookRestController.registerBook(requestDto);

    // Then correct data of the registered book is returned
    final BookEntity bookEntity = this.bookRepo.findAll().get(0);
    assertThat(responseDto.isSuccess()).isTrue();
    assertThat(responseDto.getErrorData()).isNull();
    assertThat(responseDto.getBookData()).isNotNull();
    final BookData bookData = responseDto.getBookData();
    assertThat(bookData.getId()).isEqualTo(bookEntity.getId());
    assertThat(bookData.getIsbn()).isEqualTo(bookRegistrationData.getIsbn());
    assertThat(bookData.getTitle()).isEqualTo(bookRegistrationData.getTitle());
    assertThat(bookData.isOnLoan()).isFalse();
    assertThat(bookData.getLoanDate()).isNull();
    assertThat(bookData.getReturnDate()).isNull();
  }

  @Test
  void lendBookShouldThrowCveWhenGivenRequestDtoIsNull() {
    // Given the request dto is null
    final BookLoanRequestDto requestDto = null;

    // When I try to lend a book with that data
    final ConstraintViolationException actualException = assertThrows(ConstraintViolationException.class,
        () -> this.bookRestController.lendBook(requestDto));

    // Then I receive an error indicating that required information is missing
    final Set<ConstraintViolation<?>> constraintViolations = actualException.getConstraintViolations();
    assertThat(constraintViolations).hasSize(1);
    final ConstraintViolation<?> violation = constraintViolations.iterator().next();
    assertThat(violation.getPropertyPath().toString()).contains("lendBook.requestDto");
    assertThat(violation.getMessageTemplate()).contains("NotNull");
  }

  @Test
  void lendBookShouldThrowCveWhenGivenLoanDtoIsNull() {
    // Given the payload of the request dto is null
    final BookLoanData bookLoanData = null;
    final BookLoanRequestDto requestDto = new BookLoanRequestDto(bookLoanData);

    // When I try to lend a book with that data
    final ConstraintViolationException actualException = assertThrows(ConstraintViolationException.class,
        () -> this.bookRestController.lendBook(requestDto));

    // Then I receive an error indicating that required information is missing
    final Set<ConstraintViolation<?>> constraintViolations = actualException.getConstraintViolations();
    assertThat(constraintViolations).hasSize(1);
    final ConstraintViolation<?> violation = constraintViolations.iterator().next();
    assertThat(violation.getPropertyPath().toString()).contains("lendBook.requestDto.loanData");
    assertThat(violation.getMessageTemplate()).contains("NotNull");
  }

  @Test
  void lendBookShouldThrowCveWhenGivenIsbnIsNull() {
    // Given the ISBN of a book is null
    final BookLoanData bookLoanData = BookLoanData.builder()
        .withIsbn(null)
        .withLoanDate(LocalDate.now())
        .build();
    final BookLoanRequestDto requestDto = new BookLoanRequestDto(bookLoanData);

    // When I try to lend a book with that data
    final ConstraintViolationException actualException = assertThrows(ConstraintViolationException.class,
        () -> this.bookRestController.lendBook(requestDto));

    // Then I receive an error indicating that required information is missing
    final Set<ConstraintViolation<?>> constraintViolations = actualException.getConstraintViolations();
    assertThat(constraintViolations).hasSize(1);
    final ConstraintViolation<?> violation = constraintViolations.iterator().next();
    assertThat(violation.getPropertyPath().toString()).contains("lendBook.requestDto.loanData.isbn");
    assertThat(violation.getMessageTemplate()).contains("NotNull");
  }

  @Test
  void lendBookShouldThrowCveWhenGivenLoanDateIsNull() {
    // Given the loan date of a book is null
    final BookLoanData bookLoanData = BookLoanData.builder()
        .withIsbn("isbn-4711")
        .withLoanDate(null)
        .build();
    final BookLoanRequestDto requestDto = new BookLoanRequestDto(bookLoanData);

    // When I try to lend a book with that data
    final ConstraintViolationException actualException = assertThrows(ConstraintViolationException.class,
        () -> this.bookRestController.lendBook(requestDto));

    // Then I receive an error indicating that required information is missing
    final Set<ConstraintViolation<?>> constraintViolations = actualException.getConstraintViolations();
    assertThat(constraintViolations).hasSize(1);
    final ConstraintViolation<?> violation = constraintViolations.iterator().next();
    assertThat(violation.getPropertyPath().toString()).contains("lendBook.requestDto.loanData.loanDate");
    assertThat(violation.getMessageTemplate()).contains("NotNull");
  }

  @Test
  @ResetDatabase
  void lendBookShouldSaveTheNewLoanStateWithGivenData() {
    // Given I have all required data to lend a book
    final BookLoanData bookLoanData = BookLoanData.builder()
        .withIsbn("isbn-4711")
        .withLoanDate(LocalDate.now())
        .build();
    final BookLoanRequestDto requestDto = new BookLoanRequestDto(bookLoanData);
    this.bookRepo.saveAndFlush(BookEntity.builder()
        .withIsbn(bookLoanData.getIsbn())
        .withTitle("A good title")
        .build());

    // When I try to lend a book with that data
    this.bookRestController.lendBook(requestDto);

    // Then this book is marked as loaned out
    //  and a loan activity entry was created
    final List<BookEntity> allBooks = this.bookRepo.findAll();
    assertThat(allBooks).hasSize(1);
    final BookEntity bookEntity = allBooks.get(0);
    assertThat(bookEntity.isOnLoan()).isTrue();

    final List<LoanActivityEntity> allLoans = this.loanActivityRepo.findAll();
    assertThat(allBooks).hasSize(1);
    final LoanActivityEntity loanEntity = allLoans.get(0);
    assertThat(loanEntity.getIsbn()).isEqualTo(bookLoanData.getIsbn());
    assertThat(loanEntity.getLoanDate()).isEqualTo(bookLoanData.getLoanDate());
    assertThat(loanEntity.getReturnDate()).isNull();
  }

  @Test
  @ResetDatabase
  void lendBookShouldReturnCorrectData() {
    // Given I have all required data to lend a book
    final BookLoanData bookLoanData = BookLoanData.builder()
        .withIsbn("isbn-4711")
        .withLoanDate(LocalDate.now())
        .build();
    final BookLoanRequestDto requestDto = new BookLoanRequestDto(bookLoanData);
    this.bookRepo.saveAndFlush(BookEntity.builder()
        .withIsbn(bookLoanData.getIsbn())
        .withTitle("A good title")
        .build());

    // When I try to lend a book with that data
    final BookResponseDto responseDto = this.bookRestController.lendBook(requestDto);

    // Then correct data of the loaned out book is returned
    final BookEntity bookEntity = this.bookRepo.findAll().get(0);
    assertThat(responseDto.isSuccess()).isTrue();
    assertThat(responseDto.getErrorData()).isNull();
    assertThat(responseDto.getBookData()).isNotNull();
    final BookData bookData = responseDto.getBookData();
    assertThat(bookData.getId()).isEqualTo(bookEntity.getId());
    assertThat(bookData.isOnLoan()).isTrue();
    assertThat(bookData.getLoanDate()).isEqualTo(bookLoanData.getLoanDate());
    assertThat(bookData.getReturnDate()).isNull();
  }

  @Test
  void returnBookShouldThrowCveWhenGivenRequestDtoIsNull() {
    // Given the request dto is null
    final BookReturnRequestDto requestDto = null;

    // When I try to return a book with that data
    final ConstraintViolationException actualException = assertThrows(ConstraintViolationException.class,
        () -> this.bookRestController.returnBook(requestDto));

    // Then I receive an error indicating that required information is missing
    final Set<ConstraintViolation<?>> constraintViolations = actualException.getConstraintViolations();
    assertThat(constraintViolations).hasSize(1);
    final ConstraintViolation<?> violation = constraintViolations.iterator().next();
    assertThat(violation.getPropertyPath().toString()).contains("returnBook.requestDto");
    assertThat(violation.getMessageTemplate()).contains("NotNull");
  }

  @Test
  void returnBookShouldThrowCveWhenGivenReturnDtoIsNull() {
    // Given the payload of the request dto is null
    final BookReturnData bookReturnData = null;
    final BookReturnRequestDto requestDto = new BookReturnRequestDto(bookReturnData);

    // When I try to return a book with that data
    final ConstraintViolationException actualException = assertThrows(ConstraintViolationException.class,
        () -> this.bookRestController.returnBook(requestDto));

    // Then I receive an error indicating that required information is missing
    final Set<ConstraintViolation<?>> constraintViolations = actualException.getConstraintViolations();
    assertThat(constraintViolations).hasSize(1);
    final ConstraintViolation<?> violation = constraintViolations.iterator().next();
    assertThat(violation.getPropertyPath().toString()).contains("returnBook.requestDto.returnData");
    assertThat(violation.getMessageTemplate()).contains("NotNull");
  }

  @Test
  void returnBookShouldThrowCveWhenGivenIsbnIsNull() {
    // Given the ISBN of a book is null
    final BookReturnData bookReturnData = BookReturnData.builder()
        .withIsbn(null)
        .withReturnDate(LocalDate.now())
        .build();
    final BookReturnRequestDto requestDto = new BookReturnRequestDto(bookReturnData);

    // When I try to return a book with that data
    final ConstraintViolationException actualException = assertThrows(ConstraintViolationException.class,
        () -> this.bookRestController.returnBook(requestDto));

    // Then I receive an error indicating that required information is missing
    final Set<ConstraintViolation<?>> constraintViolations = actualException.getConstraintViolations();
    assertThat(constraintViolations).hasSize(1);
    final ConstraintViolation<?> violation = constraintViolations.iterator().next();
    assertThat(violation.getPropertyPath().toString()).contains("returnBook.requestDto.returnData.isbn");
    assertThat(violation.getMessageTemplate()).contains("NotNull");
  }

  @Test
  void returnBookShouldThrowCveWhenGivenReturnDateIsNull() {
    // Given the return date of a book is null
    final BookReturnData bookLoanData = BookReturnData.builder()
        .withIsbn("isbn-4711")
        .withReturnDate(null)
        .build();
    final BookReturnRequestDto requestDto = new BookReturnRequestDto(bookLoanData);

    // When I try to return a book with that data
    final ConstraintViolationException actualException = assertThrows(ConstraintViolationException.class,
        () -> this.bookRestController.returnBook(requestDto));

    // Then I receive an error indicating that required information is missing
    final Set<ConstraintViolation<?>> constraintViolations = actualException.getConstraintViolations();
    assertThat(constraintViolations).hasSize(1);
    final ConstraintViolation<?> violation = constraintViolations.iterator().next();
    assertThat(violation.getPropertyPath().toString()).contains("returnBook.requestDto.returnData.returnDate");
    assertThat(violation.getMessageTemplate()).contains("NotNull");
  }

  @Test
  @ResetDatabase
  void returnBookShouldSaveTheNewReturnStateWithGivenData() {
    // Given I want to return a book
    //   and this book is marked as loaned in the system
    final BookReturnData bookReturnData = BookReturnData.builder()
        .withIsbn("isbn-4711")
        .withReturnDate(LocalDate.now())
        .build();
    final BookReturnRequestDto requestDto = new BookReturnRequestDto(bookReturnData);
    this.bookRepo.saveAndFlush(BookEntity.builder()
        .withIsbn(bookReturnData.getIsbn())
        .withTitle("A good title")
        .withIsOnLoan(true)
        .build());

    // When I try to return a book with that data
    this.bookRestController.returnBook(requestDto);

    // Then this book is marked as available for loan again
    //  and a loan activity entry was created
    final List<BookEntity> allBooks = this.bookRepo.findAll();
    assertThat(allBooks).hasSize(1);
    final BookEntity bookEntity = allBooks.get(0);
    assertThat(bookEntity.isOnLoan()).isFalse();

    final List<LoanActivityEntity> allLoans = this.loanActivityRepo.findAll();
    assertThat(allBooks).hasSize(1);
    final LoanActivityEntity loanEntity = allLoans.get(0);
    assertThat(loanEntity.getIsbn()).isEqualTo(bookReturnData.getIsbn());
    assertThat(loanEntity.getReturnDate()).isEqualTo(bookReturnData.getReturnDate());
    assertThat(loanEntity.getLoanDate()).isNull();
  }

  @Test
  @ResetDatabase
  void returnBookShouldReturnCorrectData() {
    // Given I want to return a book
    //   and this book is marked as loaned in the system
    final BookReturnData bookReturnData = BookReturnData.builder()
        .withIsbn("isbn-4711")
        .withReturnDate(LocalDate.now())
        .build();
    final BookReturnRequestDto requestDto = new BookReturnRequestDto(bookReturnData);
    this.bookRepo.saveAndFlush(BookEntity.builder()
        .withIsbn(bookReturnData.getIsbn())
        .withTitle("A good title")
        .withIsOnLoan(true)
        .build());

    // When I try to return a book with that data
    final BookResponseDto responseDto = this.bookRestController.returnBook(requestDto);

    // Then correct data of the returned book is returned
    final BookEntity bookEntity = this.bookRepo.findAll().get(0);
    assertThat(responseDto.isSuccess()).isTrue();
    assertThat(responseDto.getErrorData()).isNull();
    assertThat(responseDto.getBookData()).isNotNull();
    final BookData bookData = responseDto.getBookData();
    assertThat(bookData.getId()).isEqualTo(bookEntity.getId());
    assertThat(bookData.isOnLoan()).isFalse();
    assertThat(bookData.getLoanDate()).isNull();
    assertThat(bookData.getReturnDate()).isEqualTo(bookReturnData.getReturnDate());
  }
}
