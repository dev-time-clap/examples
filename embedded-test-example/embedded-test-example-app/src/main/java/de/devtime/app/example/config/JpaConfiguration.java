package de.devtime.app.example.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PACKAGE)
@Configuration
@EntityScan(basePackages = { "de.devtime" })
@EnableJpaRepositories(basePackages = { "de.devtime" })
@EnableTransactionManagement
public class JpaConfiguration {

  static {
    log.info("JPA support enabled!");
  }

}