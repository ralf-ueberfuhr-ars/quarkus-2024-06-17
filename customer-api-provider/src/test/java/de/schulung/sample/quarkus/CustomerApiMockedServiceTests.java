package de.schulung.sample.quarkus;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@QuarkusTest
public class CustomerApiMockedServiceTests {

  @InjectMock
  CustomersService service;

  @Test
  void shouldReturn404WhenCustomerNotExists() {

    var uuid = UUID.randomUUID();
    when(service.getByUuid(uuid))
      .thenReturn(Optional.empty());

    given()
      .when()
      .accept(ContentType.JSON)
      .get("/customers/{uuid}", uuid)
      .then()
      .statusCode(404);

    verify(service).getByUuid(uuid);

  }

  // TODO: wenn invalider Kunde angelegt werden soll, dann 400 + kein Service-Aufruf

}
