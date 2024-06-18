package de.schulung.sample.quarkus;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;

class CustomersServiceTests {

  private final CustomersService service = new CustomersService();

  @Test
  void shouldAddUuidToNewCustomer() {
    var customer = new Customer();
    customer.setName("Tom");
    customer.setBirthdate(LocalDate.of(2000, Month.FEBRUARY, 2));
    customer.setState("active");

    service.createCustomer(customer);

    assertThat(customer.getUuid())
      .isNotNull();
  }

  @Test
  void shouldFindNewCustomer() {
    var customer = new Customer();
    customer.setName("Tom");
    customer.setBirthdate(LocalDate.of(2000, Month.FEBRUARY, 2));
    customer.setState("active");
    service.createCustomer(customer);
    var uuid = customer.getUuid();

    var result = service.getByUuid(uuid);

    assertThat(result).isNotEmpty();
  }

}
