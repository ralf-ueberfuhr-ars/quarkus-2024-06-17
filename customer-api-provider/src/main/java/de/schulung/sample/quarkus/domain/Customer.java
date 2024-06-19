package de.schulung.sample.quarkus.domain;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@Builder
public class Customer {

  private UUID uuid;
  @Size(min = 3, max = 100)
  @NotNull
  private String name;
  private LocalDate birthdate;
  @NotNull
  private CustomerState state = CustomerState.ACTIVE;

  public enum CustomerState {
    ACTIVE, LOCKED, DISABLED
  }


}
