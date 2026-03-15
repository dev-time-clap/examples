package de.devtime.examples.library.test.builder;

import java.util.UUID;

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
    ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();
    TransactionHelper txHelper = applicationContext.getBean(TransactionHelper.class);
    return txHelper.executeInTx(Propagation.REQUIRES_NEW, _ -> {
      return buildInternally(true, true, new SaveContext(applicationContext));
    });
  }

  @Override
  default E buildInternally(final boolean withReferences) {
    return buildInternally(withReferences, false, null);
  }

  E buildInternally(final boolean withReferences, final boolean save, final SaveContext context);

  default E save(final E entity, final SaveContext context) {
    if ((context != null) && context.isSaveSupported()) {
      if (context.contains(entity.getClass(), getUniqueDataSetKey(entity))) {
        return context.get(entity.getClass(), getUniqueDataSetKey(entity));
      } else {
        R repository = getRepository(context.getApplicationContext());
        E savedEntity = repository.save(entity);
        context.put(savedEntity.getClass(), getUniqueDataSetKey(savedEntity), savedEntity);
        return savedEntity;
      }
    } else {
      return entity;
    }
  }
}
