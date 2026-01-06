package de.devtime.app.example.api.contract.data;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

/**
 * A simple business data object that contains all necessary business data to register a new book.
 *
 * @author Morrigan
 */
@Data
@Builder(setterPrefix = "with")
public class BookRegistrationData {

  @NotNull
  private final String isbn;
  @NotNull
  private final String title;

}
