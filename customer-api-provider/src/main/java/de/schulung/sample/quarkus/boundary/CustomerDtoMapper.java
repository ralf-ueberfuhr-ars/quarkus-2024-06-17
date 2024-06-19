package de.schulung.sample.quarkus.boundary;

import de.schulung.sample.quarkus.domain.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface CustomerDtoMapper {

  Customer map(CustomerDto source);

  CustomerDto map(Customer source);

  default String mapState(Customer.CustomerState source) {
    return null == source ? null : switch (source) {
      case ACTIVE -> "active";
      case LOCKED -> "locked";
      case DISABLED -> "disabled";
    };
  }

  default Customer.CustomerState mapState(String source) {
    return null == source ? null : switch (source) {
      case "active" -> Customer.CustomerState.ACTIVE;
      case "locked" -> Customer.CustomerState.LOCKED;
      case "disabled" -> Customer.CustomerState.DISABLED;
      default -> throw new IllegalArgumentException(source + " is not allowed here.");
    };
  }

}
