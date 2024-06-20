package de.schulung.sample.quarkus.persistence;

import de.schulung.sample.quarkus.domain.Customer;
import de.schulung.sample.quarkus.domain.CustomersSink;
import io.quarkus.arc.properties.IfBuildProperty;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Typed;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@ApplicationScoped
@Typed(CustomersSink.class)
@RequiredArgsConstructor
@IfBuildProperty(
  name = "persistence.sink.implementation",
  stringValue = "panache"
)
public class CustomersSinkPanacheImpl implements CustomersSink {

  private final CustomerEntityRepository repo;
  private final CustomerEntityMapper mapper;

  @Override
  public Stream<Customer> findAll() {
    return repo.listAll()
      .stream()
      .map(mapper::map);
  }

  @Override
  public Stream<Customer> findByState(Customer.CustomerState state) {
    return repo.list("state", state)
      .stream()
      .map(mapper::map);
  }

  @Override
  public Optional<Customer> findByUuid(UUID uuid) {
    return repo.findByIdOptional(uuid)
      .map(mapper::map);
  }

  @Override
  @Transactional
  public void save(Customer customer) {
    var entity = this.mapper.map(customer);
    repo.persist(entity);
    //customer.setUuid(entity.getUuid());
    mapper.copy(entity, customer);
  }

  @Override
  @Transactional
  public boolean delete(UUID uuid) {
    return this.repo.deleteById(uuid);
  }

  @Override
  public boolean exists(UUID uuid) {
    return repo.findByIdOptional(uuid).isPresent();
  }

  @Override
  public long count() {
    return repo.count();
  }
}
