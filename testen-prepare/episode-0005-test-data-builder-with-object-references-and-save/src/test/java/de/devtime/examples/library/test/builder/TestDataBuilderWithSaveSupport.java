package de.devtime.examples.library.test.builder;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Propagation;

import de.devtime.examples.library.context.ApplicationContextProvider;
import de.devtime.examples.library.persistence.util.TransactionHelper;

public interface TestDataBuilderWithSaveSupport<E, R extends JpaRepository<E, UUID>, T extends TestDataBuilder<E>>
    extends TestDataBuilder<E> {

  String getUniqueDataSetKey(E entity);

  R getRepository(final ApplicationContext appContext);

  default E buildWithReferencesAndSave() {
    return buildInternally(true, true);
  }

  @Override
  default E buildInternally(final boolean withReferences) {
    return buildInternally(withReferences, false);
  }

  E buildInternally(final boolean withReferences, final boolean save);

  default E save(final E entity) {
    ApplicationContext appContext = ApplicationContextProvider.getApplicationContext();
    if (appContext == null) {
      Logger log = LoggerFactory.getLogger(TestDataBuilderWithSaveSupport.class);
      log.warn("No spring context available. The entity was not saved!");
      return entity;
    } else {
      TransactionHelper txHelper = appContext.getBean(TransactionHelper.class);
      return txHelper.executeInTx(Propagation.REQUIRES_NEW, _ -> {
        R repository = getRepository(appContext);
        List<E> allEntities = repository.findAll();
        return allEntities.stream()
            .filter(persitedEntity -> Objects.equals(getUniqueDataSetKey(entity), getUniqueDataSetKey(persitedEntity)))
            .findFirst()
            .orElseGet(() -> (E) repository.save(entity));
      });
    }
  }
}
