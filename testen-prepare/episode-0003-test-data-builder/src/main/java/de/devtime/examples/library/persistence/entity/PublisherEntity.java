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
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(setterPrefix = "with")
@EqualsAndHashCode(callSuper = true)
@Getter
@ToString

@Entity
@Table(name = "Publisher")
public class PublisherEntity extends AbstractEntity {

  @Column(name = "NAME")
  private String name;

  public static class PublisherEntityBuilder<B> implements GenericBuilder<B> {

    protected PublisherEntityBuilder() {}

    public PublisherEntity buildWithId() {
      PublisherEntity entity = build();
      entity.setId(Generators.timeBasedEpochRandomGenerator().generate());
      return entity;
    }
  }
}
