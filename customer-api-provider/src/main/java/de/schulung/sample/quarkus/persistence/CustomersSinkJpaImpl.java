package de.schulung.sample.quarkus.persistence;

import de.schulung.sample.quarkus.domain.Customer;
import de.schulung.sample.quarkus.domain.CustomersSink;
import io.quarkus.arc.properties.IfBuildProperty;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Typed;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@ApplicationScoped
@Typed(CustomersSink.class)
@RequiredArgsConstructor
@IfBuildProperty(
  name = "persistence.sink.implementation",
  stringValue = "jpa"
)
public class CustomersSinkJpaImpl implements CustomersSink {

  private final CustomerEntityMapper mapper;
  private final EntityManager em;

  @Override
  public Stream<Customer> findAll() {
    return em.createQuery(
      "select c from Customer c",
      CustomerEntity.class
    )
      .getResultList()
      .stream()
      .map(mapper::map);
  }

  @Override
  public Stream<Customer> findByState(Customer.CustomerState state) {
    return em.createQuery(
        "select c from Customer c where c.state = :state",
        CustomerEntity.class
      )
      .setParameter("state", state)
      .getResultList()
      .stream()
      .map(mapper::map);
  }

  @Override
  public Optional<Customer> findByUuid(UUID uuid) {
    return Optional
      .ofNullable(em.find(CustomerEntity.class, uuid))
      .map(mapper::map);
  }

  @Override
  public void save(Customer customer) {
    var entity = this.mapper.map(customer);
    em.persist(entity);
    //customer.setUuid(entity.getUuid());
    mapper.copy(entity, customer);
  }

  @Override
  public boolean delete(UUID uuid) {
    var found = em.find(CustomerEntity.class, uuid);
    if(found == null) {
      return false;
    }
    em.remove(found);
    return true;
  }

  @Override
  public boolean exists(UUID uuid) {
    var found = em.find(CustomerEntity.class, uuid);
    return found != null;
  }

  @Override
  public long count() {
    return 0; // TODO ??
  }
}
