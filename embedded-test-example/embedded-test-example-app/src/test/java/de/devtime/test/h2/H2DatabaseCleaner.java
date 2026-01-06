package de.devtime.test.h2;

import java.util.List;

import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import de.devtime.app.example.constant.AppProfiles;
import de.devtime.test.DatabaseCleaner;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
@Profile(AppProfiles.H2_TEST)
public class H2DatabaseCleaner implements DatabaseCleaner {

  private final JdbcTemplate jdbcTemplate;

  @Override
  public void clean() {
    this.jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY FALSE");

    final List<String> tables = this.jdbcTemplate.queryForList("""
          SELECT TABLE_NAME
            FROM INFORMATION_SCHEMA.TABLES
            WHERE TABLE_SCHEMA = 'PUBLIC'
        """, String.class);

    for (final String table : tables) {
      this.jdbcTemplate.execute("TRUNCATE TABLE " + table);
    }

    this.jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY TRUE");
  }
}
