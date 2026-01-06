package de.devtime.app.example.api.contract;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Provides all REST endpoint URIs for this application.
 *
 * @author Morrigan
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class EndpointConstants {

  /** URI to register a new book */
  public static final String PATH_BOOKS_REGISTRATION = "/api/books/registration";

  /** URI to lend a book */
  public static final String PATH_BOOKS_LOAN = "/api/books/loan";

  /** URI to return a book */
  public static final String PATH_BOOKS_RETURN = "/api/books/return";

}
