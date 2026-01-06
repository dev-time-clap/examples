package de.devtime.app.example.businesslogic.service;

import static de.devtime.app.example.util.ExceptionFactory.throwIllegalStateException;

import java.util.Optional;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.devtime.app.example.api.contract.data.BookData;
import de.devtime.app.example.api.contract.data.BookLoanData;
import de.devtime.app.example.api.contract.data.BookRegistrationData;
import de.devtime.app.example.api.contract.data.BookReturnData;
import de.devtime.app.example.api.contract.data.BookData.BookDataBuilder;
import de.devtime.app.example.api.contract.data.BookLoanData.BookLoanDataBuilder;
import de.devtime.app.example.api.contract.data.BookRegistrationData.BookRegistrationDataBuilder;
import de.devtime.app.example.api.contract.data.BookReturnData.BookReturnDataBuilder;
import de.devtime.app.example.persistence.entity.BookEntity;
import de.devtime.app.example.persistence.entity.LoanActivityEntity;
import de.devtime.app.example.persistence.repository.BookRepository;
import de.devtime.app.example.persistence.repository.LoanRepository;
import de.devtime.app.example.util.ValidationHelper;
import lombok.Setter;

/**
 * This services handles all possible business cases on a book.
 *
 * @author Morrigan
 */
@Service
public class BookService {

  @Setter(onMethod_ = { @Autowired })
  private BookRepository bookRepo;

  @Setter(onMethod_ = { @Autowired })
  private LoanRepository loanRepo;

  @Setter(onMethod_ = { @Autowired })
  private ValidationHelper validationHelper;

  // ---------------< Business methods >---------------

  /**
   * Register a new book.
   *
   * @param consumer a data collector for this business use case
   */
  @Transactional
  public BookData registerBook(final Consumer<BookRegistrationDataBuilder> consumer) {
    final BookRegistrationDataBuilder builder = BookRegistrationData.builder();
    consumer.accept(builder);
    final BookRegistrationData bookData = builder.build();
    this.validationHelper.validateOrThrow(bookData);

    final BookEntity bookEntity = new BookEntity();
    bookEntity.setIsbn(bookData.getIsbn());
    bookEntity.setTitle(bookData.getTitle());
    bookEntity.setOnLoan(false);

    final BookEntity savedBookEntity = this.bookRepo.save(bookEntity);

    return mapDookData(savedBookEntity, null);
  }

  @Transactional
  public BookData lendBook(final Consumer<BookLoanDataBuilder> consumer) {
    final BookLoanDataBuilder builder = BookLoanData.builder();
    consumer.accept(builder);
    final BookLoanData bookLoanData = builder.build();
    this.validationHelper.validateOrThrow(bookLoanData);

    final String isbn = bookLoanData.getIsbn();
    final BookEntity bookEntity = verifyBookExists(isbn);
    verifyBookIsNotOnLoan(bookEntity);

    bookEntity.setOnLoan(true);
    final BookEntity savedBookEntity = this.bookRepo.save(bookEntity);

    final LoanActivityEntity loanEntity = LoanActivityEntity.builder()
        .withIsbn(isbn)
        .withLoanDate(bookLoanData.getLoanDate())
        .build();
    final LoanActivityEntity savedLoanActivityEntity = this.loanRepo.save(loanEntity);

    return mapDookData(savedBookEntity, savedLoanActivityEntity);
  }

  @Transactional
  public BookData returnBook(final Consumer<BookReturnDataBuilder> consumer) {
    final BookReturnDataBuilder builder = BookReturnData.builder();
    consumer.accept(builder);
    final BookReturnData bookReturnData = builder.build();
    this.validationHelper.validateOrThrow(bookReturnData);

    final String isbn = bookReturnData.getIsbn();
    final BookEntity bookEntity = verifyBookExists(isbn);
    verifyBookIsOnLoad(bookEntity);

    bookEntity.setOnLoan(false);
    final BookEntity savedBookEntity = this.bookRepo.save(bookEntity);

    final LoanActivityEntity loanEntity = LoanActivityEntity.builder()
        .withIsbn(isbn)
        .withReturnDate(bookReturnData.getReturnDate())
        .build();
    final LoanActivityEntity savedLoanActivityEntity = this.loanRepo.save(loanEntity);

    return mapDookData(savedBookEntity, savedLoanActivityEntity);
  }

  // ---------------< Validations >---------------

  private void verifyBookIsOnLoad(final BookEntity bookEntity) {
    if (!bookEntity.isOnLoan()) {
      throwIllegalStateException("The book with ISBN {} is not loaned out at all!", bookEntity.getIsbn());
    }
  }

  private void verifyBookIsNotOnLoan(final BookEntity bookEntity) {
    if (bookEntity.isOnLoan()) {
      throwIllegalStateException("The book with ISBN {} is already loaned out!", bookEntity.getIsbn());
    }
  }

  private BookEntity verifyBookExists(final String isbn) {
    final Optional<BookEntity> optBookEntity = this.bookRepo.findByIsbn(isbn);
    if (optBookEntity.isEmpty()) {
      return throwIllegalStateException("The book with ISBN {} does not exist!", isbn);
    }
    return optBookEntity.get();
  }

  // ---------------< Mapping >---------------

  private BookData mapDookData(final BookEntity bookEntity, final LoanActivityEntity loanActivityEntity) {
    final BookDataBuilder builder = BookData.builder()
        .withId(bookEntity.getId())
        .withIsbn(bookEntity.getIsbn())
        .withIsOnLoan(bookEntity.isOnLoan())
        .withTitle(bookEntity.getTitle());
    if (loanActivityEntity != null) {
      builder
          .withLoanDate(loanActivityEntity.getLoanDate())
          .withReturnDate(loanActivityEntity.getReturnDate());
    }
    return builder.build();
  }
}