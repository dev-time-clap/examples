package de.devtime.app.example.api.contract.dto;

import de.devtime.app.example.api.contract.data.BookRegistrationData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * A simple request DTO that contains all necessary data to register a new book.
 *
 * @author Morrigan
 */
@Data
public class BookRegistrationRequestDto {

  @Valid
  @NotNull
  private final BookRegistrationData registrationData;

}
