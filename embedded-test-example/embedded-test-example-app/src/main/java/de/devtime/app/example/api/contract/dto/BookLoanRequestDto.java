package de.devtime.app.example.api.contract.dto;

import de.devtime.app.example.api.contract.data.BookLoanData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * A simple request DTO that contains all necessary data to lend a book.
 *
 * @author Morrigan
 */
@Data
public class BookLoanRequestDto {

  @Valid
  @NotNull
  private final BookLoanData loanData;

}
