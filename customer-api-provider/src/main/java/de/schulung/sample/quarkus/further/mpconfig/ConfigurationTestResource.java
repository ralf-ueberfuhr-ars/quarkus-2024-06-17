package de.schulung.sample.quarkus.further.mpconfig;

import de.schulung.sample.quarkus.further.mpconfig.ConfigurationPropertiesProvider.InitialCustomerName;
import io.quarkus.arc.profile.UnlessBuildProfile;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@UnlessBuildProfile("prod")
@Path("/config-properties")
public class ConfigurationTestResource {

  @Inject
  @InitialCustomerName
  String customerName;

  @GET
  @Produces(MediaType.TEXT_PLAIN)
  public String answer() {
    return "Initial customer name: " + customerName;
  }

}
