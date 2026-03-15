package de.devtime.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.bean.override.mockito.MockReset;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;
import org.springframework.transaction.PlatformTransactionManager;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.devtime.app.example.api.impl.BookRestController;
import de.devtime.app.example.businesslogic.service.BookService;
import de.devtime.app.example.persistence.repository.BookRepository;
import de.devtime.app.example.persistence.repository.LoanRepository;
import de.devtime.app.example.util.ValidationHelper;
import de.devtime.test.postgres.zonky.EnableTestingWithDockerPostgresZonky;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.validation.Validator;
import lombok.Setter;

// @EnableTestingWithH2
// @EnableTestingWithDockerPostgres
@EnableTestingWithDockerPostgresZonky
@SpringBootTest(classes = EmbeddedTestApplication.class)
public abstract class AbstractEmbeddedTest {

  @PersistenceContext
  protected EntityManager entityManager;
  @Setter(onMethod_ = { @Autowired })
  protected ApplicationContext applicationContext;

  // ----------< Repositories >----------

  @MockitoSpyBean(reset = MockReset.AFTER)
  protected BookRepository bookRepo;
  @MockitoSpyBean(reset = MockReset.AFTER)
  protected LoanRepository loanActivityRepo;

  // ------------< Services >------------

  @MockitoSpyBean(reset = MockReset.AFTER)
  protected BookService bookService;

  // -----------< Controller >-----------

  @MockitoSpyBean(reset = MockReset.AFTER)
  protected BookRestController bookRestController;

  // --------------< Utils >-------------

  @MockitoSpyBean(reset = MockReset.AFTER)
  protected ValidationHelper validationHelper;

  // -------------< Common >-------------

  @MockitoSpyBean(reset = MockReset.AFTER)
  protected PlatformTransactionManager transactionManager;
  @MockitoSpyBean(reset = MockReset.AFTER)
  protected ObjectMapper objectMapper;
  @MockitoSpyBean(reset = MockReset.AFTER)
  protected Validator validator;

}
