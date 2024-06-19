package de.schulung.sample.quarkus.domain;

import de.schulung.sample.quarkus.domain.events.CustomerCreatedEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@ApplicationScoped
public class CustomersService {

  private final Map<UUID, Customer> customers = new HashMap<>();

  @Inject // TODO: can we use an interceptor
  Event<CustomerCreatedEvent> eventPublisher;

  public Stream<Customer> getAll() {
    return this.customers
      .values()
      .stream();
  }

  public Stream<Customer> getByState(@NotNull Customer.CustomerState state) {
    return this.getAll()
      .filter(c -> c.getState() == state);
  }

  public void createCustomer(@Valid Customer customer) {
    customer.setUuid(UUID.randomUUID());
    customers.put(customer.getUuid(), customer);
    eventPublisher.fire(new CustomerCreatedEvent(customer));
  }

  public Optional<Customer> getByUuid(@NotNull UUID uuid) {
    return Optional.ofNullable(customers.get(uuid));
  }

  public boolean exists(@NotNull UUID uuid) {
    return customers.containsKey(uuid);
  }

  public boolean delete(@NotNull UUID uuid) {
    return customers.remove(uuid) != null;
  }

}
