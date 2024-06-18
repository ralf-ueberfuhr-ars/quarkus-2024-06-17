package de.schulung.sample.quarkus;

import jakarta.enterprise.context.ApplicationScoped;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@ApplicationScoped
public class CustomersService {

  private final Map<UUID, Customer> customers = new HashMap<>();

  { // TODO replace this?
    var customer = new Customer(
      UUID.randomUUID(),
      "Tom",
      LocalDate.of(2000, Month.DECEMBER, 6),
      "active"
    );
    customers.put(customer.getUuid(), customer);
  }

  public Stream<Customer> getAll() {
    return this.customers
      .values()
      .stream();
  }

  public Stream<Customer> getByState(String state) {
    return this.getAll()
      .filter(c -> c.getState().equals(state));
  }

  public void createCustomer(Customer customer) {
    customer.setUuid(UUID.randomUUID());
    customers.put(customer.getUuid(), customer);
  }

  public Optional<Customer> getByUuid(UUID uuid) {
    return Optional.ofNullable(customers.get(uuid));
  }

  public boolean exists(UUID uuid) {
    return customers.containsKey(uuid);
  }

  public boolean delete(UUID uuid) {
    return customers.remove(uuid) != null;
  }

}
