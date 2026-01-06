package de.devtime.test.postgres.basic;

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
@Profile(AppProfiles.PG_TEST)
public class PostgresDatabaseCleaner implements DatabaseCleaner {

  private final JdbcTemplate jdbcTemplate;

  @Override
  public void clean() {
    // 1. Erzeuge dynamische TRUNCATE Anweisung mit Cascade für alle Tables
    final String sql = this.jdbcTemplate.queryForObject(
        """
            SELECT
                'TRUNCATE TABLE '
                || string_agg(format('%I.%I', table_schema, table_name), ', ')
                || ' RESTART IDENTITY CASCADE;'
            FROM information_schema.tables
            WHERE table_schema = 'public'
              AND table_type = 'BASE TABLE';
            """,
        String.class);

    // 2. Wenn nichts gefunden wurde, nichts tun
    if ((sql == null) || sql.isEmpty()) {
      return;
    }

    // 3. Führe TRUNCATE aus
    this.jdbcTemplate.execute(sql);
  }
}
