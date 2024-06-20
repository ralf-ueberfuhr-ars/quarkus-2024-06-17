package de.schulung.sample.quarkus.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class CustomersServiceTests {

  // nicht mehr notwendig wegen @FireEvent interceptor
  //@Mock
  //Event<CustomerCreatedEvent> event;
  @InjectMocks
  CustomersService service;

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
