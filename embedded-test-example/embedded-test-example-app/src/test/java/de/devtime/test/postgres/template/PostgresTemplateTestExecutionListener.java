package de.devtime.test.postgres.template;

import org.springframework.test.context.TestContext;

import de.devtime.test.AbstractTestExecutionListener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PostgresTemplateTestExecutionListener extends AbstractTestExecutionListener {

  @Override
  public void beforeTestExecution(final TestContext testContext) throws Exception {
    //    testContext.getApplicationContext()
    //        .getBean(DataSource.class)
    //        .getConnection()
    //        .close();
    //    final TestCaseConfig config = TestCaseConfig.provideTestConfiguration(testContext);
    //    final PostgresTemplateDatabaseManager manager = testContext.getApplicationContext()
    //        .getBean(PostgresTemplateDatabaseManager.class);
    //    manager.recreateFromTemplate(config.getDbTemplateToUse());
    super.beforeTestExecution(testContext);
  }
}
