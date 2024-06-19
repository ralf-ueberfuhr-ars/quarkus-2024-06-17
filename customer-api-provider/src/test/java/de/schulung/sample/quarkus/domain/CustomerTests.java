package de.schulung.sample.quarkus.domain;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CustomerTests {

  @Test
  void shouldHaveDefaultState() {
    var customer = Customer.builder().build();
    assertThat(customer)
      .extracting(Customer::getState)
      .isEqualTo(Customer.CustomerState.ACTIVE);
  }

}
