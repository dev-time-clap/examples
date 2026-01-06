package de.devtime.test.postgres.template;

import lombok.extern.slf4j.Slf4j;

@Slf4j
//@Component
//@Profile(AppProfiles.PG_TEMPLATE_TEST)
public class PostgresTemplateDatabaseManager {

  //  private final PostgreSQLContainer<?> postgres;
  //
  //  public PostgresTemplateDatabaseManager() {
  //    this.postgres = PostgresTestContainer.getInstance();
  //  }
  //
  //  public void recreateFromTemplate(final DbTemplateType template) {
  //    log.debug("Recreating database '{}' from template '{}'",
  //        PostgresInitializer.TEST_DB_NAME, template.getDbName());
  //
  //    try (Connection conn = DriverManager.getConnection(this.postgres.getJdbcUrl(),
  //        this.postgres.getUsername(),
  //        this.postgres.getPassword())) {
  //      conn.setAutoCommit(true);
  //
  //      try (Statement st = conn.createStatement()) {
  //        st.execute("""
  //            SELECT pg_terminate_backend(pid)
  //            FROM pg_stat_activity
  //            WHERE datname = '%s'
  //              AND pid <> pg_backend_pid()
  //            """.formatted(PostgresInitializer.TEST_DB_NAME));
  //
  //        st.execute("DROP DATABASE IF EXISTS %s".formatted(PostgresInitializer.TEST_DB_NAME));
  //        st.execute("CREATE DATABASE %s WITH TEMPLATE %s"
  //            .formatted(PostgresInitializer.TEST_DB_NAME, template.getDbName()));
  //      }
  //    } catch (final SQLException e) {
  //      throw new IllegalStateException("Failed to recreate database", e);
  //    }
  //  }
}
