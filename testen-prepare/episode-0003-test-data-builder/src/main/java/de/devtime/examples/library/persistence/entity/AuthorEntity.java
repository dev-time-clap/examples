package de.devtime.examples.library.persistence.entity;

import java.time.LocalDate;

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
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(setterPrefix = "with")
@EqualsAndHashCode(callSuper = true)
@Getter
@ToString

@Entity
@Table(name = "Author")
public class AuthorEntity extends AbstractEntity {

  @Column(name = "FIRST_NAME")
  private String firstName;

  @Column(name = "LAST_NAME")
  private String lastName;

  @Column(name = "ARTIST_NAME", nullable = false)
  private String artistName;

  @Column(name = "BIRTHDAY")
  private LocalDate birthday;

  public static class AuthorEntityBuilder<B> implements GenericBuilder<B> {

    protected AuthorEntityBuilder() {}

    public AuthorEntity buildWithId() {
      AuthorEntity entity = build();
      entity.setId(Generators.timeBasedEpochRandomGenerator().generate());
      return entity;
    }
  }
}
