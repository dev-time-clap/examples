package de.devtime.test.postgres;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;

import de.devtime.app.example.constant.AppProfiles;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PostgresInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

  @Override
  public void initialize(final ConfigurableApplicationContext context) {
    final String[] activeProfiles = context.getEnvironment().getActiveProfiles();
    if (ArrayUtils.contains(activeProfiles, AppProfiles.PG_TEST)) {
      log.info("Applying PostgreSQL datasource properties before Spring Context");
      final PostgreSQLContainer<?> postgres = PostgresTestContainer.getInstance();

      log.debug("postgres.getJdbcUrl(): {}", postgres.getJdbcUrl());
      TestPropertyValues.of(
          "spring.datasource.url=" + postgres.getJdbcUrl(),
          "spring.datasource.username=" + postgres.getUsername(),
          "spring.datasource.password=" + postgres.getPassword(),
          "spring.flyway.url=" + postgres.getJdbcUrl(),
          "spring.flyway.user=" + postgres.getUsername(),
          "spring.flyway.password=" + postgres.getPassword()

      ).applyTo(context.getEnvironment());
    }
  }
}
