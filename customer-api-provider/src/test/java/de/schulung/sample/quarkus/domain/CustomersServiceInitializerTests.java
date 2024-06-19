package de.schulung.sample.quarkus.domain;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
public class CustomersServiceInitializerTests {

  @Inject
  CustomersService service;

  @Test
  void shouldHaveAtLeastOneCustomer() {
    assertThat(service.getAll())
      .hasAtLeastOneElementOfType(Customer.class);
  }


}
