package de.schulung.sample.quarkus.domain;

import io.quarkus.arc.DefaultBean;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Typed;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@ApplicationScoped
@Typed(CustomersSink.class)
@DefaultBean
public class CustomersSinkInMemoryImpl implements CustomersSink {

  private final Map<UUID, Customer> customers = new HashMap<>();

  @Override
  public Stream<Customer> findAll() {
    return this.customers
      .values()
      .stream();
  }

  @Override
  public Optional<Customer> findByUuid(UUID uuid) {
    return Optional.ofNullable(customers.get(uuid));
  }

  @Override
  public void save(Customer customer) {
    customer.setUuid(UUID.randomUUID());
    customers.put(customer.getUuid(), customer);
  }

  @Override
  public boolean delete(UUID uuid) {
    return customers.remove(uuid) != null;
  }

  @Override
  public boolean exists(UUID uuid) {
    return customers.containsKey(uuid);
  }

  @Override
  public long count() {
    return customers.size();
  }
}
