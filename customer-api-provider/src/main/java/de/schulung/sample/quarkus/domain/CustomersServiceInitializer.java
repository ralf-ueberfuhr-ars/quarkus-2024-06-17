package de.schulung.sample.quarkus.domain;

import io.quarkus.arc.properties.IfBuildProperty;
import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.Dependent;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.time.Month;

@Dependent // destroy object immediately
@RequiredArgsConstructor
//@IfBuildProfile("dev") - nur für lokale Entwicklung
//@UnlessBuildProfile("prod") - NICHT für prod
@IfBuildProperty(
  name = "customers.initialization.enabled",
  stringValue = "true"
)
public class CustomersServiceInitializer {

  private final CustomersService service;

  // TODO InitialCustomerConfig class injecten

  @Startup
  public void initializeData() {
    var customer = Customer.builder()
      .name("Tom")
      .birthdate(LocalDate.of(2000, Month.DECEMBER, 6))
      .build();
    service.createCustomer(customer);
  }

}
