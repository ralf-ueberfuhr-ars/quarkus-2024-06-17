package de.schulung.sample.quarkus.boundary.security;

import io.quarkus.arc.profile.IfBuildProfile;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;
import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.ClaimValue;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;

@Path("/security-info")
@IfBuildProfile("dev")
public class SecurityInfoResource {

  @Inject
  JsonWebToken jwt;
  @Inject
  @Claim(standard = Claims.birthdate)
  ClaimValue<String> birthdate;

  @GET
  @Path("permit-all")
  @PermitAll
  @Produces(MediaType.TEXT_PLAIN)
  public String hello(@Context SecurityContext ctx) {
    return handle(ctx);
  }

  @GET
  @Path("roles-allowed")
  @RolesAllowed({ "User", "Admin" })
  @Produces(MediaType.TEXT_PLAIN)
  public String helloRolesAllowed(@Context SecurityContext ctx) {
    return handle(ctx) + ", birthdate: " + jwt.getClaim(Claims.birthdate.name());
  }

  @GET
  @Path("roles-allowed-admin")
  @RolesAllowed("Admin")
  @Produces(MediaType.TEXT_PLAIN)
  public String helloRolesAllowedAdmin(@Context SecurityContext ctx) {
    return handle(ctx) + ", birthdate: " + birthdate.getValue();
  }

  private String handle(SecurityContext ctx) {
    final String name;
    if (ctx.getUserPrincipal() == null) {
      name = "anonymous";
    } else if (!ctx.getUserPrincipal().getName().equals(jwt.getName())) {
      throw new InternalServerErrorException("Principal and JsonWebToken names do not match");
    } else {
      name = ctx.getUserPrincipal().getName();
    }
    return String.format("hello + %s,"
        + " isHttps: %s,"
        + " authScheme: %s,"
        + " hasJWT: %s",
      name, ctx.isSecure(), ctx.getAuthenticationScheme(), hasJwt());
  }

  private boolean hasJwt() {
    return jwt.getClaimNames() != null;
  }
}