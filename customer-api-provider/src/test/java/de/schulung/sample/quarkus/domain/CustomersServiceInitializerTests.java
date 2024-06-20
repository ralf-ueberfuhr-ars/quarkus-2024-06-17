package de.schulung.sample.quarkus.domain;

import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.QuarkusTestProfile;
import io.quarkus.test.junit.TestProfile;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
@TestProfile(CustomersServiceInitializerTests.InitializationEnabledProfile.class)
public class CustomersServiceInitializerTests {

  @Inject
  CustomersService service;

  @Test
  void shouldHaveAtLeastOneCustomer() {
    assertThat(service.getAll())
      .hasAtLeastOneElementOfType(Customer.class);
  }

  public static class InitializationEnabledProfile implements QuarkusTestProfile {
    @Override
    public Map<String, String> getConfigOverrides() {
      return Map.of(
        "customers.initialization.enabled",
        "true"
      );
    }
  }


}
