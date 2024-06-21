package de.schulung.sample.quarkus.persistence;

import de.schulung.sample.quarkus.domain.Customer;
import de.schulung.sample.quarkus.domain.Customer.CustomerState;
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

  /* ******************************************************* *
   * Converter methods - could be converter objects instead  *
   * ******************************************************* */

  private static UUID convertUuid(String uuid) {
    return Optional.ofNullable(uuid)
      .map(UUID::fromString)
      .orElse(null);
  }

  private static String convertUuid(UUID uuid) {
    return Optional.ofNullable(uuid)
      .map(UUID::toString)
      .orElse(null);
  }

  private static LocalDate convertDate(Date date) {
    return Optional.ofNullable(date)
      .map(Date::toLocalDate)
      .orElse(null);
  }

  private static Date convertDate(LocalDate date) {
    return Optional.ofNullable(date)
      .map(Date::valueOf)
      .orElse(null);
  }

  private static CustomerState convertState(int value) {
    return CustomerState.values()[value];
  }

  private static int convertState(CustomerState value) {
    return Optional.ofNullable(value)
      .map(CustomerState::ordinal)
      .orElse(0);

  }

  /* ******************************************************* *
   * Row Mapping - could be a RowMapper object instead       *
   * ******************************************************* */

  private static Customer readSingle(ResultSet rs) throws SQLException {
    return Customer.builder()
      .uuid(convertUuid(rs.getString("UUID")))
      .birthdate(convertDate(rs.getDate("DATE_OF_BIRTH")))
      .name(rs.getString("NAME"))
      .state(convertState(rs.getInt("STATE")))
      .build();
  }

  private static Stream<Customer> readAll(ResultSet rs) throws SQLException {
    Collection<Customer> result = new LinkedList<>();
    while (rs.next()) {
      result.add(readSingle(rs));
    }
    return result.stream();
  }

  /* ******************************************************* *
   * CustomersSink implementation                            *
   * ******************************************************* */

  @Override
  public Stream<Customer> findAll() {
    try (Connection con = ds.getConnection();
         Statement stmt = con.createStatement();
         ResultSet rs = stmt.executeQuery(
           "select * from CUSTOMERS"
         )) {

      return readAll(rs);

    } catch (SQLException e) {
      throw new RuntimeException(e); // eigene Exception?
    }
  }

  @Override
  public Stream<Customer> findByState(CustomerState state) {
    try (Connection con = ds.getConnection();
         PreparedStatement stmt = con.prepareStatement(
           "select * from CUSTOMERS where STATE=?"
         )) {

      stmt.setInt(1, convertState(state));

      try (ResultSet rs = stmt.executeQuery()) {
        return readAll(rs);
      }

    } catch (SQLException e) {
      throw new RuntimeException(e); // eigene Exception?
    }
  }

  @Override
  public Optional<Customer> findByUuid(UUID uuid) {
    try (Connection con = ds.getConnection();
         PreparedStatement stmt = con.prepareStatement(
           "select * from CUSTOMERS where UUID=?"
         )) {

      stmt.setString(1, convertUuid(uuid));

      try (ResultSet rs = stmt.executeQuery()) {
        if (!rs.next()) {
          return Optional.empty();
        }
        return Optional.of(readSingle(rs));
      }

    } catch (SQLException e) {
      throw new RuntimeException(e); // eigene Exception?
    }
  }

  @Override
  public void save(Customer customer) {
    // TODO only insert, we need an update too, if the UUID is already set
    try (Connection con = ds.getConnection();
         PreparedStatement stmt = con.prepareStatement(
           "insert into CUSTOMERS(UUID,NAME,DATE_OF_BIRTH,STATE) values(?,?,?,?)",
           Statement.RETURN_GENERATED_KEYS
         )) {

      stmt.setString(1, convertUuid(customer.getUuid()));
      stmt.setString(2, customer.getName());
      stmt.setDate(3, convertDate(customer.getBirthdate()));
      stmt.setInt(4, convertState(customer.getState()));
      stmt.executeUpdate();

      try (ResultSet rs = stmt.getGeneratedKeys()) {
        if (!rs.next()) {
          throw new RuntimeException("not expected"); // bessere Exception
        }
        customer.setUuid(convertUuid(rs.getString(1)));
      }

    } catch (SQLException e) {
      throw new RuntimeException(e); // eigene Exception?
    }
  }

  @Override
  public boolean delete(UUID uuid) {
    try (Connection con = ds.getConnection();
         PreparedStatement stmt = con.prepareStatement(
           "delete from CUSTOMERS where UUID=?"
         )) {

      stmt.setString(1, convertUuid(uuid));
      return stmt.executeUpdate() > 0;

    } catch (SQLException e) {
      throw new RuntimeException(e); // eigene Exception?
    }
  }

  @Override
  public long count() {
    try (Connection con = ds.getConnection();
         Statement stmt = con.createStatement();
         ResultSet rs = stmt.executeQuery(
           "select count(uuid) from CUSTOMERS"
         )) {

      if (!rs.next()) {
        return 0;
      }
      return rs.getLong(1);

    } catch (SQLException e) {
      throw new RuntimeException(e); // eigene Exception?
    }
  }
}
