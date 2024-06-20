package de.schulung.sample.quarkus.boundary;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

/*
 * We test the app as a black box.
 * (Integration Test)
 */
@QuarkusTest
@DisplayName("API-Tests for: POST /customers")
public class CustomerApiPostCustomersTests {

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
