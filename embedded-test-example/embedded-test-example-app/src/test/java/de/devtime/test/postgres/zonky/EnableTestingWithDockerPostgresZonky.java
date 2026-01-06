package de.devtime.test.postgres.zonky;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import de.devtime.app.example.constant.AppProfiles;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase.DatabaseProvider;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase.DatabaseType;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase.RefreshMode;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@ActiveProfiles({ AppProfiles.EMBEDDED_TEST, AppProfiles.PG_ZONKY_TEST })
@AutoConfigureEmbeddedDatabase(
    provider = DatabaseProvider.DOCKER,
    type = DatabaseType.POSTGRES,
    refresh = RefreshMode.BEFORE_EACH_TEST_METHOD)
@TestPropertySource(properties = {
    "zonky.test.database.postgres.docker.image=postgres:18.1-alpine"
})
public @interface EnableTestingWithDockerPostgresZonky {

}
