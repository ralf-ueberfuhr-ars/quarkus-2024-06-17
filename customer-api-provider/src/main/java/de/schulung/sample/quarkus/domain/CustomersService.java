package de.schulung.sample.quarkus.domain;

import de.schulung.sample.quarkus.domain.events.CustomerCreatedEvent;
import de.schulung.sample.quarkus.shared.interceptors.FireEvent;
import de.schulung.sample.quarkus.shared.interceptors.LogPerformance;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.jboss.logging.Logger;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@ApplicationScoped
@RequiredArgsConstructor
public class CustomersService {

  private final CustomersSink sink;

  public Stream<Customer> getAll() {
    return this.sink.findAll();
  }

  public Stream<Customer> getByState(@NotNull Customer.CustomerState state) {
    return this.sink.findByState(state);
  }

  @LogPerformance(Logger.Level.DEBUG)
  @FireEvent(CustomerCreatedEvent.class)
  public void createCustomer(@Valid Customer customer) {
    this.sink.save(customer);
  }

  public Optional<Customer> getByUuid(@NotNull UUID uuid) {
    return this.sink.findByUuid(uuid);
  }

  public boolean exists(@NotNull UUID uuid) {
    return this.sink.exists(uuid);
  }

  public boolean delete(@NotNull UUID uuid) {
    return this.sink.delete(uuid);
  }

  public long count() {
    return this.sink.count();
  }
}
