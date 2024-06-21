package de.schulung.sample.quarkus.boundary.security;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.QuarkusTestProfile;
import io.quarkus.test.junit.TestProfile;
import io.quarkus.test.security.TestSecurity;
import io.quarkus.test.security.jwt.Claim;
import io.quarkus.test.security.jwt.JwtSecurity;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
@TestHTTPEndpoint(SecurityInfoResource.class)
@TestProfile(SecurityInfoResourceTests.DevForTestProfile.class) // enable the JAX-RS resource
public class SecurityInfoResourceTests {

  @Inject
  Logger log;

  @Test
  void shouldReturnNotAuthenticatedForRolesAllowed() {
    given()
      .when()
      .get("/roles-allowed")
      .then()
      .statusCode(401);
  }

  @Test
  @TestSecurity(user = "user1", roles = "test")
  void shouldReturnForbiddenForRolesAllowed() {
    given()
      .when()
      .get("/roles-allowed")
      .then()
      .statusCode(403);
  }

  @Test
  @TestSecurity(
    user = "user1",
    roles = "User"
  )
  @JwtSecurity(
    claims = @Claim(
      key = "birthdate",
      value = "1981-10-11"
    )
  )
  void shouldReturnOkForRolesAllowed() {
    log.info("Body: " +
      given()
        .when()
        .get("/roles-allowed")
        .then()
        .statusCode(200)
        .extract()
        .body()
        .asString()
    );
  }

  public static class DevForTestProfile implements QuarkusTestProfile {

    @Override
    public String getConfigProfile() {
      return "dev";
    }
  }

}
