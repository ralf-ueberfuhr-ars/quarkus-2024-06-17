package de.schulung.sample.quarkus.persistence;

import de.schulung.sample.quarkus.domain.Customer;
import de.schulung.sample.quarkus.domain.CustomersSink;
import io.quarkus.arc.properties.IfBuildProperty;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Typed;
import lombok.RequiredArgsConstructor;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

@ApplicationScoped
@Typed(CustomersSink.class)
@RequiredArgsConstructor
@IfBuildProperty(
  name = "persistence.sink.implementation",
  stringValue = "jdbc"
)
public class CustomersSinkJdbcImpl implements CustomersSink {

  private final DataSource ds;

  private static LocalDate convert(Date date) {
    return Optional.ofNullable(date)
      .map(Date::toLocalDate)
      .orElse(null);
  }

  @Override
  public Stream<Customer> findAll() {
    try(Connection con = ds.getConnection();
    Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select * from CUSTOMERS")) {

      Collection<Customer> result = new LinkedList<>();

      while(rs.next()) {

        var customer = Customer.builder()
          .uuid(UUID.fromString(rs.getString("UUID")))
          .birthdate(convert(rs.getDate("DATE_OF_BIRTH")))
          .name(rs.getString("NAME"))
          //.state(rs.?)
          .build();
        result.add(customer);

      }

      return result.stream();

    } catch (SQLException e) {
        throw new RuntimeException(e); // eigene Exception?
    }
  }

  @Override
  public Stream<Customer> findByState(Customer.CustomerState state) {
    return CustomersSink.super.findByState(state);
  }

  @Override
  public Optional<Customer> findByUuid(UUID uuid) {
    return CustomersSink.super.findByUuid(uuid);
  }

  @Override
  public void save(Customer customer) {

  }

  @Override
  public boolean delete(UUID uuid) {
    return false;
  }

  @Override
  public boolean exists(UUID uuid) {
    return CustomersSink.super.exists(uuid);
  }

  @Override
  public long count() {
    return 0;
  }
}
