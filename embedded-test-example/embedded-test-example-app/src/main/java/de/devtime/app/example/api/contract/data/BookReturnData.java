package de.devtime.app.example.api.contract.data;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * A simple business data object that contains all necessary business data to return a loaned out book.
 *
 * @author Morrigan
 */
@Getter
@Builder(setterPrefix = "with")
@ToString
public class BookReturnData {

  @NotNull
  private final String isbn;

  @NotNull
  private final LocalDate returnDate;

}
