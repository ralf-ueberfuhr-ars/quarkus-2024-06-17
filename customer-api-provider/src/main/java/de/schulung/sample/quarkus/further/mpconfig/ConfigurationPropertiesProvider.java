package de.schulung.sample.quarkus.further.mpconfig;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Qualifier;
import lombok.Getter;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@ApplicationScoped
public class ConfigurationPropertiesProvider {

  @Retention(RetentionPolicy.RUNTIME)
  @Target({
    ElementType.TYPE,
    ElementType.FIELD,
    ElementType.METHOD,
    ElementType.PARAMETER
  })
  @Qualifier
  public @interface InitialCustomerName {
  }

  @ConfigProperty(
    name = "customers.initialization.sample.name",
    defaultValue = "John Doe"
  )
  @Getter(onMethod_ = {
    @Produces,
    @InitialCustomerName
  })
  String initialCustomerName;


}
