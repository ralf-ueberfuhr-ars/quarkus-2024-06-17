package de.schulung.sample.quarkus;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Path("/customers")
public class CustomersResource {

  // TODO Swagger Codegen

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
  public Response createCustomer(Customer customer, @Context UriInfo info) {
    customer.setUuid(UUID.randomUUID());
    customers.put(customer.getUuid(), customer);
    var location = info.getAbsolutePathBuilder() // current URL
      .path(customer.getUuid().toString()) // add "/<uuid>"
      .build();
    return Response
      .created(location)
      .entity(customer)
      .build();
  }

  @GET
  @Path("/{uuid}")
  public Customer findById(@PathParam("uuid") UUID uuid) {
    return customers.get(uuid);
  }

}
