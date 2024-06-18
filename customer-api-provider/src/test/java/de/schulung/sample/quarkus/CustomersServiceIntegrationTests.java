package de.schulung.sample.quarkus;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@QuarkusTest
public class CustomersServiceIntegrationTests {

  @Inject
  CustomersService service;

  // TODO Testfall: Customer anlegen mit zu kurzem Namen -> Validierungsfehler

  @Test
  void shouldNotCreateInvalidCustomer() {
    var customer = new Customer();
    assertThatThrownBy(() -> service.createCustomer(customer))
      .isNotNull();
  }

  @Test
  void shouldNotCreateNullCustomer() {
    assertThatThrownBy(() -> service.createCustomer(null))
      .isNotNull();
  }

  @Test
  void shouldNotInvokeSearchWithNullUuid() {
    assertThatThrownBy(() -> service.getByUuid(null))
      .isNotNull();
  }

}
