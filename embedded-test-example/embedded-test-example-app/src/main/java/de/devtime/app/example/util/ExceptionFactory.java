package de.devtime.app.example.util;

import org.slf4j.helpers.MessageFormatter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * A simple exception factory that provides common exceptions.
 * <p>
 * <b>Usage</b><br>
 *
 * <b>Message format support</b><br>
 * All methods support the slf4j pattern format like 'The given value is invalid: {}'.
 *
 * @author Morrigan
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExceptionFactory {

  public static <T> T throwIllegalStateException(final String msgPattern, final Object... params) {
    return throwIllegalStateException(null, msgPattern, params);
  }

  public static <T> T throwIllegalStateException(final Throwable cause, final String msgPattern,
      final Object... params) {
    final String formattedMsg = MessageFormatter.arrayFormat(msgPattern, params).getMessage();
    throw new IllegalStateException(formattedMsg, cause);
  }

}
