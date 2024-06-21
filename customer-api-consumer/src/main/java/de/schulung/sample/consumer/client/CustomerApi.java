package de.schulung.sample.consumer.client;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.Collection;

@RegisterRestClient(configKey = "customer-api")
@Path("/customers")
public interface CustomerApi {

  // https://quarkus.io/guides/rest-client

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  Uni<Collection<CustomerDto>> getAllCustomers();

}
