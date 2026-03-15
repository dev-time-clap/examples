package de.devtime.examples.library.persistence.entity;

import java.util.Set;

import com.fasterxml.uuid.Generators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(setterPrefix = "with")
@EqualsAndHashCode(callSuper = true)
@Getter
@ToString

@Entity
@Table(name = "Customer")
public class CustomerEntity extends AbstractEntity {

  @Column(name = "FIRST_NAME", nullable = false)
  private String firstName;

  @Column(name = "LAST_NAME", nullable = false)
  private String lastName;

  @OneToMany
  private Set<BookEntity> loanedBooks;

  public static class CustomerEntityBuilder<B> implements GenericBuilder<B> {

    protected CustomerEntityBuilder() {}

    public CustomerEntity buildWithId() {
      CustomerEntity entity = build();
      entity.setId(Generators.timeBasedEpochRandomGenerator().generate());
      return entity;
    }
  }
}
