package de.schulung.sample.quarkus.domain;

import de.schulung.sample.quarkus.domain.events.CustomerCreatedEvent;
import de.schulung.sample.quarkus.shared.interceptors.LogPerformance;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@ApplicationScoped
@RequiredArgsConstructor
public class CustomersService {

  private final Map<UUID, Customer> customers = new HashMap<>();

  // TODO: can we use an interceptor
  private final Event<CustomerCreatedEvent> eventPublisher;

  public Stream<Customer> getAll() {
    return this.customers
      .values()
      .stream();
  }

  public Stream<Customer> getByState(@NotNull Customer.CustomerState state) {
    return this.getAll()
      .filter(c -> c.getState() == state);
  }

  @LogPerformance
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
