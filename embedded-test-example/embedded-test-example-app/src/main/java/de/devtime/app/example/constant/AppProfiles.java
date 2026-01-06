package de.devtime.app.example.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Provides all Spring Boot profiles supported by this application.
 *
 * @author Morrigan
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AppProfiles {

  public static final String EMBEDDED_TEST = "embedded-test";
  public static final String H2_TEST = "h2-test";
  public static final String PG_TEST = "pg-test";
  public static final String PG_TEMPLATE_TEST = "pg-template-test";
  public static final String PG_ZONKY_TEST = "pg-zonky-test";

}
