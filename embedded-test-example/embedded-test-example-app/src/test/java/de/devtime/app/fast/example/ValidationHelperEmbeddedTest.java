package de.devtime.app.fast.example;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Set;

import org.junit.jupiter.api.Test;

import de.devtime.app.example.api.contract.data.BookRegistrationData;
import de.devtime.test.AbstractEmbeddedTest;
import de.devtime.test.SkipResetDatabase;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@SkipResetDatabase
class ValidationHelperEmbeddedTest extends AbstractEmbeddedTest {

  @Test
  void validateOrThrowShouldNotThrowCveWhenNoConstraintViolationIsPresent() {
    final BookRegistrationData objectToValidate = BookRegistrationData.builder()
        .withIsbn("ISBN-4711")
        .withTitle("Just a book")
        .build();

    assertDoesNotThrow(() -> this.validationHelper.validateOrThrow(objectToValidate));
  }

  @Test
  void validateOrThrowShouldThrowCveWhenConstraintViolationIsPresent() {
    final BookRegistrationData objectToValidate = BookRegistrationData.builder()
        .withIsbn(null)
        .withTitle("Just a book")
        .build();

    final ConstraintViolationException actualException = assertThrows(ConstraintViolationException.class,
        () -> this.validationHelper.validateOrThrow(objectToValidate));

    final Set<ConstraintViolation<?>> constraintViolations = actualException.getConstraintViolations();
    assertThat(constraintViolations).hasSize(1);
    final ConstraintViolation<?> violation = constraintViolations.iterator().next();
    assertThat(violation.getPropertyPath().toString()).contains("isbn");
    assertThat(violation.getMessageTemplate()).contains("NotNull");
  }

  @Test
  void validateNullableOrThrowShouldNotThrowAnyExceptionWhenNullIsGiven() {
    assertDoesNotThrow(() -> this.validationHelper.validateNullableOrThrow(null));
  }
}
