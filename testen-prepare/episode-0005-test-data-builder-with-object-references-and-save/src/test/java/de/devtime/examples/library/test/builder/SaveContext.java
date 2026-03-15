package de.devtime.examples.library.test.builder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;

import lombok.Getter;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SaveContext {

  @Value
  private static class EntityRepositoryPair {
    Object entity;
    JpaRepository<?, UUID> repository;
  }

  private static String cacheKey(final Class<?> entityClass, final String uniqueKey) {
    return entityClass.getName() + "::" + uniqueKey;
  }

  private final Map<JpaRepository<?, UUID>, List<Object>> cacheForSave = new HashMap<>();
  private final Map<String, Object> cache = new HashMap<>();

  @Getter
  private final ApplicationContext applicationContext;

  public SaveContext(final ApplicationContext appContext) {
    this.applicationContext = appContext;
    if (this.applicationContext == null) {
      log.warn("No spring context available. Entities will not be saved!");
    }
  }

  public void put(final Class<?> entityClass, final String uniqueKey, final Object entity) {
    this.cache.put(cacheKey(entityClass, uniqueKey), entity);
  }

  @SuppressWarnings("unchecked")
  public <E> E get(final Class<?> entityClass, final String uniqueKey) {
    return (E) this.cache.get(cacheKey(entityClass, uniqueKey));
  }

  public boolean contains(final Class<?> entityClass, final String uniqueKey) {
    return this.cache.containsKey(cacheKey(entityClass, uniqueKey));
  }

  public boolean isSaveSupported() {
    return this.applicationContext != null;
  }
}