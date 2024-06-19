package de.schulung.sample.quarkus.boundary;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/*
 * We test the app as a black box.
 * (Integration Test)
 */
@QuarkusTest
class CustomerApiTests {

  @Nested
  @DisplayName("GET /customers")
  class GetCustomersTests {
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

  @Nested
  @DisplayName("POST /customers")
  class CreateCustomerTests {

    @Test
    void shouldCreateCustomer() {
      var location = given()
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
        .header("Location", startsWith("http"))
        .contentType(ContentType.JSON)
        .body("name", is(equalTo("Tom")))
        .body("birth_date", is(equalTo("2000-10-04")))
        .body("uuid", is(notNullValue()))
        .extract()
        .header("Location");

      given()
        .when()
        .accept(ContentType.JSON)
        .get(location)
        .then()
        .statusCode(200)
        .body("name", is(equalTo("Tom")))
        .body("birth_date", is(equalTo("2000-10-04")));
    }

    @Test
    void shouldNotCreateCustomerWithInvalidName() {
      given()
        .when()
        .contentType(ContentType.JSON)
        .body("""
          {
              "name": "T",
              "birth_date": "2000-10-04",
              "state": "active"
          }
          """)
        .accept(ContentType.JSON)
        .post("/customers")
        .then()
        .statusCode(400);
    }

    @Test
    void shouldNotCreateCustomerWithInvalidState() {
      given()
        .when()
        .contentType(ContentType.JSON)
        .body("""
          {
              "name": "Tom Mayer",
              "birth_date": "2000-10-04",
              "state": "gelbekatze"
          }
          """)
        .accept(ContentType.JSON)
        .post("/customers")
        .then()
        .statusCode(400);
    }

  }

  @Nested
  @DisplayName("GET /customers/uuid")
  class GetSingleCustomerTests {

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

    @Nested
    @DisplayName("with customer that does NOT exist")
    class DeletedCustomerTests {

      @BeforeEach
      void setup() {
        given()
          .when()
          .delete(customerLocation)
          .then()
          .statusCode(204);
      }

      @Test
      void shouldReturn404WhenNotFound() {
        given()
          .when()
          .accept(ContentType.JSON)
          .get(customerLocation)
          .then()
          .statusCode(404);
      }

    }

  }

}