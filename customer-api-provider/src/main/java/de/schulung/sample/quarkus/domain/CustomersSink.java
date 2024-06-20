package de.schulung.sample.quarkus.domain;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public interface CustomersSink {

  Stream<Customer> findAll();

  default Stream<Customer> findByState(Customer.CustomerState state) {
    return this
      .findAll()
      .filter(c -> c.getState() == state);
  }

  default Optional<Customer> findByUuid(UUID uuid) {
    return this
      .findAll()
      .filter(c -> c.getUuid().equals(uuid))
      .findFirst();
  }

  void save(Customer customer);

  boolean delete(UUID uuid);

  default boolean exists(UUID uuid) {
    return this
      .findByUuid(uuid)
      .isPresent();
  }

  long count();

}
