package de.devtime.app.example.api.contract.data;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(setterPrefix = "with")
public class BookData {

  private final String id;
  private final String isbn;
  private final String title;
  private final boolean isOnLoan;
  private final LocalDate loanDate;
  private final LocalDate returnDate;

}
