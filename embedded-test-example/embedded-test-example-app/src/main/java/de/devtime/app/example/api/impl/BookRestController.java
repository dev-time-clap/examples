package de.devtime.app.example.api.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import de.devtime.app.example.api.contract.EndpointConstants;
import de.devtime.app.example.api.contract.data.BookData;
import de.devtime.app.example.api.contract.data.BookLoanData;
import de.devtime.app.example.api.contract.data.BookRegistrationData;
import de.devtime.app.example.api.contract.data.BookReturnData;
import de.devtime.app.example.api.contract.dto.BookLoanRequestDto;
import de.devtime.app.example.api.contract.dto.BookRegistrationRequestDto;
import de.devtime.app.example.api.contract.dto.BookResponseDto;
import de.devtime.app.example.api.contract.dto.BookReturnRequestDto;
import de.devtime.app.example.businesslogic.service.BookService;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Provides access to the 'book' ressource.
 *
 * @author Morrigan
 */
@Slf4j
@Validated
@RestController
public class BookRestController {

  @Setter(onMethod_ = { @Autowired })
  private BookService bookService;

  /**
   * With this endpoint a new book is registered in the system.
   *
   * @param requestDto a request DTO that contains data about the new book
   * @throws ConstraintViolationException if some mandatory data is not available
   */
  @PostMapping(EndpointConstants.PATH_BOOKS_REGISTRATION)
  @ResponseStatus(HttpStatus.CREATED)
  public BookResponseDto registerBook(@Valid @NotNull final BookRegistrationRequestDto requestDto) {
    log.debug("requestDto: {}", requestDto);
    final BookRegistrationData registrationData = requestDto.getRegistrationData();
    final BookData actualBookData = this.bookService.registerBook(b -> b
        .withIsbn(registrationData.getIsbn())
        .withTitle(registrationData.getTitle()));
    return BookResponseDto.builder()
        .withSuccess(true)
        .withBookData(actualBookData)
        .build();
  }

  /**
   * With this endpoint, an existing book can be loaned out.
   *
   * @param requestDto a request DTO that contains data about the book to loan.
   * @throws ConstraintViolationException if some mandatory data is not available
   */
  @PostMapping(EndpointConstants.PATH_BOOKS_LOAN)
  @ResponseStatus(HttpStatus.CREATED)
  public BookResponseDto lendBook(@Valid @NotNull final BookLoanRequestDto requestDto) {
    log.debug("requestDto: {}", requestDto);
    final BookLoanData loanData = requestDto.getLoanData();
    final BookData actualBookData = this.bookService.lendBook(b -> b
        .withIsbn(loanData.getIsbn())
        .withLoanDate(loanData.getLoanDate()));
    return BookResponseDto.builder()
        .withSuccess(true)
        .withBookData(actualBookData)
        .build();
  }

  /**
   * With this endpoint, an existing book can be returned.
   *
   * @param requestDto a request DTO that contains data about the book to return.
   * @throws ConstraintViolationException if some mandatory data is not available
   */
  @PostMapping(EndpointConstants.PATH_BOOKS_RETURN)
  @ResponseStatus(HttpStatus.CREATED)
  public BookResponseDto returnBook(@Valid @NotNull final BookReturnRequestDto requestDto) {
    log.debug("requestDto: {}", requestDto);
    final BookReturnData returnData = requestDto.getReturnData();
    final BookData actualBookData = this.bookService.returnBook(b -> b
        .withIsbn(returnData.getIsbn())
        .withReturnDate(returnData.getReturnDate()));
    return BookResponseDto.builder()
        .withSuccess(true)
        .withBookData(actualBookData)
        .build();
  }
}
