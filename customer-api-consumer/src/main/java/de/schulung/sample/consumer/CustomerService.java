package de.schulung.sample.consumer;

import de.schulung.sample.consumer.client.CustomerApi;
import de.schulung.sample.consumer.client.CustomerDto;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

import java.util.Collection;

@ApplicationScoped
@RequiredArgsConstructor
public class CustomerService {

  private final CustomerApi customerApi;

  public Uni<Collection<CustomerDto>> getAllCustomers() { // quick'n'dirty without mapping!
    return customerApi.getAllCustomers();
  }
}
