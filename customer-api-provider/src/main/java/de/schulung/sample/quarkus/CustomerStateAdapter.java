package de.schulung.sample.quarkus;

import jakarta.json.bind.adapter.JsonbAdapter;

/*
 * A more generic mapper is not possible easily, because
 *  - the adapter doesn't have any information about the mapped field (target type)
 *  - we need to be careful with reflection (GraalVM!)
 */
public class CustomerStateAdapter implements JsonbAdapter<Customer.CustomerState, String> {

  @Override
  public String adaptToJson(Customer.CustomerState source) throws Exception {
    return null == source ? null : switch (source) {
      case ACTIVE -> "active";
      case LOCKED -> "locked";
      case DISABLED -> "disabled";
    };
  }

  @Override
  public Customer.CustomerState adaptFromJson(String source) throws Exception {
    return null == source ? null : switch (source) {
      case "active" -> Customer.CustomerState.ACTIVE;
      case "locked" -> Customer.CustomerState.LOCKED;
      case "disabled" -> Customer.CustomerState.DISABLED;
      default -> throw new IllegalArgumentException(source + " is not supported.");
    };
  }
}