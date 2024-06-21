package de.schulung.sample.quarkus.persistence;

import de.schulung.sample.quarkus.domain.Customer;
import de.schulung.sample.quarkus.domain.Customer.CustomerState;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import jakarta.persistence.PersistenceException;

@Converter(autoApply = true)
public class CustomerStateConverter implements AttributeConverter<CustomerState, String> {


  @Override
  public String convertToDatabaseColumn(CustomerState source) {
    return null == source ? null : switch (source) {
      case ACTIVE -> "a";
      case LOCKED -> "l";
      case DISABLED -> "d";
    };
  }

  @Override
  public CustomerState convertToEntityAttribute(String source) {
    return null == source ? null : switch (source) {
      case "a" -> Customer.CustomerState.ACTIVE;
      case "l" -> Customer.CustomerState.LOCKED;
      case "d" -> Customer.CustomerState.DISABLED;
      default -> throw new PersistenceException(source + " is not allowed here.");
    };
  }
}
