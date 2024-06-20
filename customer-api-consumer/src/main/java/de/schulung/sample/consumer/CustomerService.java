package de.schulung.sample.consumer;

import de.schulung.sample.consumer.client.CustomerApi;
import de.schulung.sample.consumer.client.CustomerDto;
import io.quarkus.cache.CacheInvalidateAll;
import io.quarkus.cache.CacheResult;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.RequiredArgsConstructor;

import java.util.Collection;

@ApplicationScoped
@RequiredArgsConstructor
public class CustomerService {

  private final CustomerApi customerApi;

  // https://quarkus.io/guides/cache

  @CacheResult(cacheName = "customers-api-cache")
  public Uni<Collection<CustomerDto>> getAllCustomers() { // quick'n'dirty without mapping!
    return customerApi.getAllCustomers();
  }

  @CacheInvalidateAll(cacheName = "customers-api-cache")
  public void reset(){}

}
