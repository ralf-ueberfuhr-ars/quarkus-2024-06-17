package de.schulung.sample.quarkus;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;

@QuarkusTest
public class CustomersServiceIntegrationTests {

  @Inject
  CustomersService service;

  // TODO Testfall: Customer anlegen mit zu kurzem Namen -> Validierungsfehler

}
