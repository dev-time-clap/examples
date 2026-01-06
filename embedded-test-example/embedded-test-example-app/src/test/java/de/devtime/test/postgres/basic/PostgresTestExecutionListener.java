package de.devtime.test.postgres.basic;

import org.springframework.test.context.TestContext;

import de.devtime.test.AbstractTestExecutionListener;
import de.devtime.test.DatabaseCleaner;
import de.devtime.test.TestCaseConfig;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PostgresTestExecutionListener extends AbstractTestExecutionListener {

  @Override
  public void beforeTestExecution(final TestContext testContext) throws Exception {
    final TestCaseConfig config = TestCaseConfig.provideTestConfiguration(testContext);
    if (config.isResetDatabase()) {
      final DatabaseCleaner databaseCleaner = testContext.getApplicationContext().getBean(DatabaseCleaner.class);
      databaseCleaner.clean();
    }
    super.beforeTestExecution(testContext);
  }
}
