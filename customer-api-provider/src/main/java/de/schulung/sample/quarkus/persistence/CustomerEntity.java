package de.schulung.sample.quarkus.persistence;

import de.schulung.sample.quarkus.domain.Customer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Entity(name = "Customer")
@Table(name = "CUSTOMERS")
public class CustomerEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID uuid;
  @Size(min = 3, max = 100)
  @NotNull
  private String name;
  @Column(name = "DATE_OF_BIRTH")
  private LocalDate birthdate;
  @NotNull
  private Customer.CustomerState state = Customer.CustomerState.ACTIVE;

}
