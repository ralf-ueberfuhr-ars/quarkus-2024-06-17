package de.schulung.sample.quarkus.domain;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@QuarkusTest
public class CustomersServiceIntegrationTests {

  @Inject
  CustomersService service;

  @Test
  void shouldNotCreateInvalidCustomer() {
    var customer = Customer.builder().build();
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
