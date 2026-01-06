package de.devtime.test.postgres;

import org.testcontainers.containers.PostgreSQLContainer;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PostgresTestContainer {

  private static final String POSTGRES_IMAGE_NAME = "postgres:18.1-alpine";

  private static final PostgreSQLContainer<?> INSTANCE = new PostgreSQLContainer<>(POSTGRES_IMAGE_NAME);

  static {
    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      log.info(">>> Stopping PostgreSQL Testcontainer");
      INSTANCE.stop();
    }));
  }

  //  private static boolean initialized = false;

  private PostgresTestContainer() {}

  public static PostgreSQLContainer<?> getInstance() {
    if (!INSTANCE.isRunning()) {
      INSTANCE.start();
      //      initTemplates();
    }
    return INSTANCE;
  }

  //  private static void migrateTemplate(final String dbName) {
  //
  //    final String url = INSTANCE.getJdbcUrl().replace(INSTANCE.getDatabaseName(), dbName);
  //    log.info("url: {}", url);
  //
  //    Flyway.configure()
  //        .dataSource(url, INSTANCE.getUsername(), INSTANCE.getPassword())
  //        .locations("classpath:db/migration")
  //        .baselineOnMigrate(true)
  //        .failOnMissingLocations(true)
  //        .load()
  //        .migrate();
  //  }
  //
  //  private static void initTemplates() {
  //    if (initialized) {
  //      return;
  //    }
  //
  //    log.info(">>> Initializing PostgreSQL template databases");
  //
  //    final String adminDatabaseName = INSTANCE.getDatabaseName();
  //    final String adminUsername = INSTANCE.getUsername();
  //    final String adminPassword = INSTANCE.getPassword();
  //
  //    log.info("databaseName: {}, username: {}, password: {}", adminDatabaseName, adminUsername, adminPassword);
  //    try (Connection conn = DriverManager.getConnection(INSTANCE.getJdbcUrl(), adminUsername, adminPassword)) {
  //
  //      conn.setAutoCommit(true);
  //
  //      execute(conn, "CREATE DATABASE " + DbTemplateType.EMPTY.getDbName());
  //      execute(conn, "CREATE DATABASE " + DbTemplateType.BOOKS_1000.getDbName());
  //
  //    } catch (final SQLException e) {
  //      throw new IllegalStateException("Failed to initialize template databases", e);
  //    }
  //    migrateTemplate(DbTemplateType.EMPTY.getDbName());
  //    migrateTemplate(DbTemplateType.BOOKS_1000.getDbName());
  //    initialized = true;
  //    log.debug("Templates initialized");
  //  }
  //
  //  private static void execute(final Connection conn, final String sql) throws SQLException {
  //    try (Statement st = conn.createStatement()) {
  //      st.execute(sql);
  //    }
  //  }
}
