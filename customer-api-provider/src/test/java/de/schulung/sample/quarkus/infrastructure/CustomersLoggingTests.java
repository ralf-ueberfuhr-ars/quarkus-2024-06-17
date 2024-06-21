package de.schulung.sample.quarkus.infrastructure;

import de.schulung.sample.quarkus.domain.Customer;
import de.schulung.sample.quarkus.domain.CustomersService;
import io.quarkus.arc.log.LoggerName;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.annotation.PreDestroy;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Produces;
import jakarta.enterprise.inject.spi.InjectionPoint;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;

@QuarkusTest
class CustomersLoggingTests {

  /*
   * Requirement:
   *  - when a customer is created, a logging should occur (Logger with name "customers")
   * Hints:
   *  - we mock the JBoss logger here to verify the logging
   *  - this is error-prone, because
   *     - we overwrite the Quarkus-internal @DefaultBean (see io.quarkus.arc.runtime.LoggerProducer)
   *     - we verify the invocation of the special logger method (instead of verifying any logging)
   *  - it would be better instead to encapsulate Customer Events Logging within a custom bean
   *    and mock this bean during the test
   */

  @Inject
  CustomersService service;
  @LoggerName("customers")
  Logger log;

  @Test
  void shouldLogWhenCustomerCreated() {
    reset(log); // already logged during startup (initialization)!
    var customer = Customer.builder()
      .name("Tom")
      .birthdate(LocalDate.of(2000, Month.FEBRUARY, 2))
      .build();
    service.createCustomer(customer);

    verify(log).info(anyString());

  }

  // just for this class, not for all tests!
  @ApplicationScoped
  static class LoggerMocksProducer {
    private final Map<String, Logger> loggers = new HashMap<>();

    private Optional<LoggerName> findLoggerName(InjectionPoint injectionPoint) {
      return injectionPoint.getQualifiers()
        .stream()
        .filter(q -> q.annotationType().equals(LoggerName.class))
        .findFirst()
        .map(LoggerName.class::cast);
    }

    private Logger createLogger(String name) {
      return Mockito.mock(Logger.class);
    }

    @Produces
    @Dependent
    @LoggerName("")
    Logger getMockedLogger(InjectionPoint injectionPoint) {
      return findLoggerName(injectionPoint)
        .map(LoggerName::value)
        .map(name -> loggers.computeIfAbsent(name, this::createLogger))
        .orElseThrow(() -> new IllegalStateException("Unable to derive the logger name at " + injectionPoint));
    }

    @PreDestroy
    void clear() {
      loggers.clear();
    }

  }

}
