package de.devtime.app.example.api.contract.dto;

import de.devtime.app.example.api.contract.data.BookReturnData;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * A simple request DTO that contains all necessary data to return a loaned out book.
 *
 * @author Morrigan
 */
@Data
public class BookReturnRequestDto {

  @Valid
  @NotNull
  private final BookReturnData returnData;

}
