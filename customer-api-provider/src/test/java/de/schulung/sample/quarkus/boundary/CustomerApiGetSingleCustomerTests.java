package de.schulung.sample.quarkus.boundary;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

/*
 * We test the app as a black box.
 * (Integration Test)
 */
@QuarkusTest
@DisplayName("API-Tests for: GET /customers/{uuid}")
public class CustomerApiGetSingleCustomerTests {

  String customerLocation;

  @BeforeEach
    // we need to create a customer to be sure that one exist
  void setup() {
    customerLocation = given()
      .when()
      .contentType(ContentType.JSON)
      .body("""
        {
            "name": "Tom",
            "birth_date": "2000-10-04",
            "state": "active"
        }
        """)
      .accept(ContentType.JSON)
      .post("/customers")
      .then()
      .statusCode(201)
      .header("Location", is(notNullValue()))
      .extract()
      .header("Location");
  }

  private void ensureCustomerIsDeleted() {
    given()
      .when()
      .delete(customerLocation)
      .then()
      .statusCode(204);
  }

  @Test
  void shouldReturn404WhenNotFound() {
    ensureCustomerIsDeleted();
    given()
      .when()
      .accept(ContentType.JSON)
      .get(customerLocation)
      .then()
      .statusCode(404);
  }
}