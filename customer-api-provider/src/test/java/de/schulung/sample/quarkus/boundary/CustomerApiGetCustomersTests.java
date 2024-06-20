package de.schulung.sample.quarkus.boundary;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.startsWith;

/*
 * We test the app as a black box.
 * (Integration Test)
 */
@QuarkusTest
@DisplayName("API-Tests for: GET /customers")
public class CustomerApiGetCustomersTests {

  /*
   * GET /customers, Accept: application/json
   * -> 200, Content-Type: application/json
   */
  @Test
  void shouldReturn200WhenGetCustomers() {
    given()
      .when()
      .accept(ContentType.JSON)
      .get("/customers")
      .then()
      .statusCode(200)
      .contentType(ContentType.JSON)
      .body(startsWith("["))
      .body(endsWith("]"));
    // we could also test for JSON schema: https://medium.com/@dhadiprasetyo/asserting-json-schema-for-api-testing-with-java-and-restassured-79b4a851f282
  }

  /*
   * GET /customers, Accept: application/xml
   * -> 406
   */
  @Test
  void shouldReturn406WhenGetCustomersWithInvalidContentType() {
    given()
      .when()
      .accept(ContentType.XML)
      .get("/customers")
      .then()
      .statusCode(406);
  }

}
