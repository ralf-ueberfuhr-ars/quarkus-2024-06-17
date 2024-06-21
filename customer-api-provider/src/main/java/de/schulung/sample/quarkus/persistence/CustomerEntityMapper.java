package de.schulung.sample.quarkus.persistence;

import de.schulung.sample.quarkus.domain.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "cdi")
public interface CustomerEntityMapper {

  CustomerEntity map(Customer source);

  Customer map(CustomerEntity source);

  void copy(CustomerEntity source, @MappingTarget Customer target);

}
