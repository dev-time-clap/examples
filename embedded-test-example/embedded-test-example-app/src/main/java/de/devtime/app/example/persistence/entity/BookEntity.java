package de.devtime.app.example.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder(setterPrefix = "with")
@Getter
@Setter
public class BookEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  private String isbn;
  private String title;
  private boolean isOnLoan;
}
