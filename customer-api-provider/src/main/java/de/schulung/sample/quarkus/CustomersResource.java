package de.schulung.sample.quarkus;

import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

import java.time.LocalDate;
import java.time.Month;
import java.util.*;

@Path("/customers")
public class CustomersResource {

  private final Map<UUID, Customer> customers = new HashMap<>();

  {
    var customer = new Customer(
      UUID.randomUUID(),
      "Tom",
      LocalDate.of(2000, Month.DECEMBER, 6),
      "active"
    );
    customers.put(customer.getUuid(), customer);
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Collection<Customer> getCustomers() {
    return customers.values();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response createCustomer(@Valid Customer customer) {
    customer.setUuid(UUID.randomUUID());
    customers.put(customer.getUuid(), customer);
    final var location = UriBuilder.fromResource(CustomersResource.class)
      .path(CustomersResource.class, "findCustomerById")
      .build(customer.getUuid().toString());
    /* Alternatively, add a parameter "@Context UriInfo info" and use it like this:
     *
     * info
     *   .getAbsolutePathBuilder()
     *   .path(customer.getUuid().toString())
     *   .build();
     */
    return Response
      .created(location)
      .entity(customer)
      .build();
  }

  // Exceptions: https://cristian.sulea.net/blog/rest-java-jax-rs-exception-handling/

  @GET
  @Path("/{uuid}")
  public Customer findCustomerById(@PathParam("uuid") UUID uuid) {
    return Optional.ofNullable(customers.get(uuid))
      .orElseThrow(NotFoundException::new);
  }

  @DELETE
  @Path("/{uuid}")
  public Response deleteCustomerById(@PathParam("uuid") UUID uuid) {
    if (customers.remove(uuid) == null) {
      throw new NotFoundException();
    }
    return Response
      .noContent()
      .build();
  }

}
