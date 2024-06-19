package de.schulung.sample.quarkus.boundary;

import de.schulung.sample.quarkus.domain.CustomersService;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;
import lombok.RequiredArgsConstructor;

import java.util.Collection;
import java.util.UUID;

@Path("/customers")
@RequiredArgsConstructor
public class CustomersResource {

  private final CustomersService service;
  private final CustomerDtoMapper mapper;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public Collection<CustomerDto> getCustomers() {
    return service
      .getAll()
      .map(mapper::map)
      .toList();
  }

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response createCustomer(@Valid CustomerDto dto) {
    var customer = mapper.map(dto);
    service.createCustomer(customer);
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
      .entity(mapper.map(customer))
      .build();
  }

  // Exceptions: https://cristian.sulea.net/blog/rest-java-jax-rs-exception-handling/

  @GET
  @Path("/{uuid}")
  public CustomerDto findCustomerById(@PathParam("uuid") UUID uuid) {
    return service.getByUuid(uuid)
      .map(mapper::map)
      .orElseThrow(NotFoundException::new);
  }

  @DELETE
  @Path("/{uuid}")
  public Response deleteCustomerById(@PathParam("uuid") UUID uuid) {
    if (!service.delete(uuid)) {
      throw new NotFoundException();
    }
    return Response
      .noContent()
      .build();
  }

}
