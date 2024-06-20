package de.schulung.sample.quarkus.persistence;

import de.schulung.sample.quarkus.domain.Customer;
import de.schulung.sample.quarkus.domain.CustomersSink;
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
    // TODO
    return CustomersSink.super.findByState(state);
  }

  @Override
  public Optional<Customer> findByUuid(UUID uuid) {
    // TODO findById mit UUID?
    return CustomersSink.super.findByUuid(uuid);
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
  public boolean delete(UUID uuid) {
    // TODO delete by UUID
    return false;
  }

  @Override
  public boolean exists(UUID uuid) {
    // TODO exists by UUID
    return CustomersSink.super.exists(uuid);
  }

  @Override
  public long count() {
    return repo.count();
  }
}
