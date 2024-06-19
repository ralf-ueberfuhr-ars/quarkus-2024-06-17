package de.schulung.sample.quarkus.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;

class CustomersServiceTests {

  private final CustomersService service = new CustomersService();

  @Test
  void shouldAddUuidToNewCustomer() {
    var customer = Customer.builder()
      .name("Tom")
      .birthdate(LocalDate.of(2000, Month.FEBRUARY, 2))
      .build();

    service.createCustomer(customer);

    assertThat(customer.getUuid())
      .isNotNull();
  }

  @Test
  void shouldFindNewCustomer() {
    var customer = Customer.builder()
      .name("Tom")
      .birthdate(LocalDate.of(2000, Month.FEBRUARY, 2))
      .build();
    service.createCustomer(customer);
    var uuid = customer.getUuid();

    var result = service.getByUuid(uuid);

    assertThat(result).isNotEmpty();
  }

}
