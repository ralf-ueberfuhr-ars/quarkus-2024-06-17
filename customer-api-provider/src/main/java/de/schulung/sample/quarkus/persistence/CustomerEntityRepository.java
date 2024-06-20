package de.schulung.sample.quarkus.persistence;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CustomerEntityRepository implements PanacheRepository<CustomerEntity> {
}
