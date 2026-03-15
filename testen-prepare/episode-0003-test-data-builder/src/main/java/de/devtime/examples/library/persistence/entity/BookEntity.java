package de.devtime.examples.library.persistence.entity;

import com.fasterxml.uuid.Generators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(setterPrefix = "with")
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Getter
@Setter

@Entity
@Table(name = "Book")
public class BookEntity extends AbstractEntity {

  @Column(name = "ISBN", nullable = false)
  private String isbn;

  @Column(name = "TITLE", nullable = false)
  private String title;

  @Column(name = "IS_ON_LOAN")
  private boolean isOnLoan;

  public static class BookEntityBuilder<B> implements GenericBuilder<B> {

    protected BookEntityBuilder() {}

    public BookEntity buildWithId() {
      BookEntity entity = build();
      entity.setId(Generators.timeBasedEpochRandomGenerator().generate());
      return entity;
    }
  }
}
