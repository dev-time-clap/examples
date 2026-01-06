package de.devtime.test;

import org.springframework.test.context.TestContext;
import org.springframework.test.context.TestExecutionListener;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class AbstractTestExecutionListener implements TestExecutionListener {

  @Override
  public void prepareTestInstance(final TestContext testContext) throws Exception {
    log.trace("prepareTestInstance");
  }

  @Override
  public void beforeTestClass(final TestContext testContext) throws Exception {
    log.trace("beforeTestClass");
  }

  @Override
  public void beforeTestMethod(final TestContext testContext) throws Exception {
    log.trace("beforeTestMethod");
  }

  @Override
  public void beforeTestExecution(final TestContext testContext) throws Exception {
    final TestCaseConfig config = TestCaseConfig.provideTestConfiguration(testContext);
    final String testCaseLabel = config.isDisplayNameAvailabe() ? config.getDisplayName() : config.getTestMethodName();
    log.debug("{} | Run test case: '{}'", config.getDbResetMsg(), testCaseLabel);
  }

  @Override
  public void afterTestExecution(final TestContext testContext) throws Exception {
    log.trace("afterTestExecution");
  }

  @Override
  public void afterTestMethod(final TestContext testContext) throws Exception {
    log.trace("afterTestMethod");
  }

  @Override
  public void afterTestClass(final TestContext testContext) throws Exception {
    log.trace("afterTestClass");
  }
}
