package de.devtime.app.example.api.contract.dto;

import de.devtime.app.example.api.contract.data.BookData;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(setterPrefix = "with")
public class BookResponseDto {

  private final boolean success;
  private final BookData bookData;
  private final ErrorDto errorData;

}
