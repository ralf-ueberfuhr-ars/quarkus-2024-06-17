package de.schulung.sample.consumer;

import de.schulung.sample.consumer.client.CustomerDto;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import lombok.RequiredArgsConstructor;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Path("/customers")
public class CustomersResource {

  private final CustomerService service;

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public Uni<String> getCustomerNames() {
    return this.service
      .getAllCustomers()
      .map(list -> list
        .stream()
        .map(CustomerDto::getName)
        .collect(Collectors.joining("\n"))
      );
  }

}
