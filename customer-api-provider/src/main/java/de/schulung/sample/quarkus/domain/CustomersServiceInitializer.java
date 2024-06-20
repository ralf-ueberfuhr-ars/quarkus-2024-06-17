package de.schulung.sample.quarkus.domain;

import io.quarkus.arc.properties.IfBuildProperty;
import io.quarkus.runtime.Startup;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.inject.Default;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.eclipse.microprofile.config.inject.ConfigProperties;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.time.LocalDate;

@Dependent // destroy object immediately
@RequiredArgsConstructor
//@IfBuildProfile("dev") - nur für lokale Entwicklung
//@UnlessBuildProfile("prod") - NICHT für prod
@IfBuildProperty(
  name = "customers.initialization.enabled",
  stringValue = "true"
)
public class CustomersServiceInitializer {

  @Getter
  @Setter
  @ConfigProperties(prefix = "customers.initialization.sample")
  @Dependent
  public static class InitialCustomerConfig {

    @ConfigProperty(defaultValue = "John Doe")
    String name;
    @ConfigProperty(defaultValue = "2010-04-05")
    String birthdate;

  }

  /*
   * @ConfigProperties ist ein CDI @Qualifier
   * -> beim Nutzer: @Inject @ConfigProperties
   * -> Probleme mit @RequiredArgsConstructor von Lombox
   * -> hier ein CDI Producer, der das Object auch als @Default bereitstellt
   */
  @Dependent
  public static class InitialCustomerConfigProvider {

    @Inject
    @ConfigProperties
    @Getter(onMethod_ = {
      @Produces,
      @Default
    })
    InitialCustomerConfig cfg;

  }

  private final CustomersService service;
  private final InitialCustomerConfig customerConfig;

  @Startup
  public void initializeData() {
    var customer = Customer.builder()
      .name(customerConfig.getName())
      .birthdate(LocalDate.parse(customerConfig.getBirthdate()))
      .build();
    service.createCustomer(customer);
  }

}
