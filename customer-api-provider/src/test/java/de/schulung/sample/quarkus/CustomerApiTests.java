package de.schulung.sample.quarkus;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

/*
 * We test the app as a black box.
 * (Integration Test)
 */
@QuarkusTest
class CustomerApiTests {

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
                .contentType(ContentType.JSON);
        // TODO test for JSON array
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
