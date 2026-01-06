package de.devtime.test.h2;

import org.springframework.context.ApplicationContext;
import org.springframework.test.context.TestContext;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import de.devtime.test.AbstractTestExecutionListener;
import de.devtime.test.DatabaseCleaner;
import de.devtime.test.TestCaseConfig;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class H2TestExecutionListener extends AbstractTestExecutionListener {

  @Override
  public void beforeTestExecution(final TestContext testContext) throws Exception {
    final TestCaseConfig config = TestCaseConfig.provideTestConfiguration(testContext);
    if (config.isResetDatabase()) {
      final ApplicationContext appContext = testContext.getApplicationContext();
      final DatabaseCleaner dbCleaner = appContext.getBean(DatabaseCleaner.class);
      final PlatformTransactionManager txManager = appContext.getBean(PlatformTransactionManager.class);
      new TransactionTemplate(txManager).executeWithoutResult(status -> dbCleaner.clean());
    }
    super.beforeTestExecution(testContext);
  }
}
