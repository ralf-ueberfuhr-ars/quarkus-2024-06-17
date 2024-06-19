package de.schulung.sample.quarkus.domain;

import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.Dependent;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.Month;

@Dependent
@RequiredArgsConstructor
public class CustomersServiceInitializer {

  private final CustomersService service;

  @Startup
  public void initializeData() {
    var customer = Customer.builder()
      .name("Tom")
      .birthdate(LocalDate.of(2000, Month.DECEMBER, 6))
      .build();
    service.createCustomer(customer);
  }

}
