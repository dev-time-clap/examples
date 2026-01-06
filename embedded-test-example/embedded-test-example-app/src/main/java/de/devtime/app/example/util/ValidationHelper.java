package de.devtime.app.example.util;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.Setter;

/**
 * Helper class to validate and throw a {@link ConstraintViolationException} an object annotated with some jakarta
 * validation annotations.
 *
 * @author Morrigan
 */
@Component
public class ValidationHelper {

  @Setter(onMethod_ = { @Autowired })
  private Validator validator;

  /**
   * Validates an object and throws an exception if some validation rule does not pass.
   *
   * @param <T> type of the object to validate
   * @param toValidate object to be validated
   * @throws ConstraintViolationException if the object to validate has some constraint violations
   */
  public <T> void validateOrThrow(final T toValidate) {
    final Set<ConstraintViolation<T>> violations = this.validator.validate(toValidate);
    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }
  }

  /**
   * Validates an object and throws an exception if some validation rule does not pass. If the given object to validate
   * is {@code null}, then the validation is skipped and no exception will be thrown.
   *
   * @param <T> type of the object to validate
   * @param toValidate object to be validated
   * @throws ConstraintViolationException if the object to validate has some constraint violations
   */
  public <T> void validateNullableOrThrow(final T toValidate) {
    if (toValidate != null) {
      validateOrThrow(toValidate);
    }
  }
}
