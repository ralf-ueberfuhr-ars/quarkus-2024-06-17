package de.schulung.sample.quarkus.persistence;

import de.schulung.sample.quarkus.domain.Customer;
import de.schulung.sample.quarkus.domain.CustomersSink;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.TestProfile;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
@TestTransaction
@TestProfile(UseJdbcImplementation.class)
public class PersistenceJdbcCustomersSinkTests {

  @Inject
  CustomersSink sink;

  @Test
  void shouldFindByState() {
    var result = sink.findByState(Customer.CustomerState.ACTIVE);
    assertThat(result).isNotNull();
  }

}
