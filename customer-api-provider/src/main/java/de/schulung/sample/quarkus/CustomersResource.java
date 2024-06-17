package de.schulung.sample.quarkus;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Path("/customers")
public class CustomersResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<Customer> getCustomers() {
        return List.of(
            new Customer(
                    UUID.randomUUID(),
                    "Tom",
                    LocalDate.of(2000, Month.DECEMBER, 6),
                    "active"
            )
        );
    }

}
