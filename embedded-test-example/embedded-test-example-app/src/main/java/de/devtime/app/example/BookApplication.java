package de.devtime.app.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot application entry point.
 *
 * @author Morrigan
 */
@SpringBootApplication(scanBasePackages = "de.devtime")
public class BookApplication {

  public static void main(final String[] args) {
    SpringApplication.run(BookApplication.class, args);
  }

}
