package de.schulung.sample.consumer.client;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Default;
import jakarta.enterprise.inject.Produces;
import lombok.Getter;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@ApplicationScoped
public class CustomerApiProvider {

  @RestClient
  @Getter(onMethod_ = {
    @Produces,
    @Default
  })
  CustomerApi customerApi;

}
