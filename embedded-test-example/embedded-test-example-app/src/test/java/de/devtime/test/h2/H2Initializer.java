package de.devtime.test.h2;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import de.devtime.app.example.constant.AppProfiles;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class H2Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

  @Override
  public void initialize(final ConfigurableApplicationContext context) {
    final String[] activeProfiles = context.getEnvironment().getActiveProfiles();
    if (ArrayUtils.contains(activeProfiles, AppProfiles.H2_TEST)) {
      log.info("Applying H2 datasource properties before Spring Context");
      TestPropertyValues.of(
          "spring.datasource.url=jdbc:h2:mem:testdb",
          "spring.datasource.username=sa",
          "spring.datasource.password=sa");
    }
  }
}