package de.devtime.test.postgres.template;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;

import de.devtime.app.example.constant.AppProfiles;
import de.devtime.test.postgres.PostgresInitializer;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ActiveProfiles({ AppProfiles.EMBEDDED_TEST, AppProfiles.PG_TEMPLATE_TEST })
@ContextConfiguration(initializers = PostgresInitializer.class)
@TestExecutionListeners(
    listeners = PostgresTemplateTestExecutionListener.class,
    mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)
@TestPropertySource(properties = {
    "spring.flyway.enabled=true"
})
public @interface EnableTestingWithDockerPostgresTemplate {

}
