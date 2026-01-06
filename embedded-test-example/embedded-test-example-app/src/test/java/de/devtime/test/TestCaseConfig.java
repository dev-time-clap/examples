package de.devtime.test;

import java.lang.reflect.Method;

import org.junit.jupiter.api.DisplayName;
import org.springframework.test.context.TestContext;
import org.testcontainers.shaded.org.apache.commons.lang3.StringUtils;

import lombok.Getter;

@Getter
public class TestCaseConfig {

  public static TestCaseConfig provideTestConfiguration(final TestContext testContext) {
    final TestCaseConfig config = new TestCaseConfig();
    final Class<?> testClass = testContext.getTestClass();
    final Method testMethod = testContext.getTestMethod();
    config.testMethodName = testMethod.getName();

    final DisplayName displayNameAnno = testMethod.getDeclaredAnnotation(DisplayName.class);
    if (displayNameAnno != null) {
      config.displayName = displayNameAnno.value();
    }

    config.resetDatabase = true; // Default value
    final SkipResetDatabase skipResetDbOnClass = testClass.getDeclaredAnnotation(SkipResetDatabase.class);
    if (skipResetDbOnClass != null) {
      config.resetDatabase = false;
    }

    final ResetDatabase resetDbOnMethod = testMethod.getDeclaredAnnotation(ResetDatabase.class);
    if (resetDbOnMethod != null) {
      config.resetDatabase = true;
    }
    final SkipResetDatabase skipResetDbOnMethod = testMethod.getDeclaredAnnotation(SkipResetDatabase.class);
    if (skipResetDbOnMethod != null) {
      config.resetDatabase = false;
    }

    if (config.dbTemplateToUse == null) {
      config.dbTemplateToUse = DbTemplateType.EMPTY;
    }
    return config;
  }

  private String testMethodName;
  private String displayName;
  private boolean resetDatabase;
  private DbTemplateType dbTemplateToUse;

  public boolean isDisplayNameAvailabe() {
    return StringUtils.isNotBlank(this.displayName);
  }

  public String getDbResetMsg() {
    return isResetDatabase() ? "DB clean: yes" : "DB clean:  no";
  }
}
