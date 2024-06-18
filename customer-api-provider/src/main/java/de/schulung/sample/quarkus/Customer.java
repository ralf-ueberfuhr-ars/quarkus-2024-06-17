package de.schulung.sample.quarkus;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.json.bind.annotation.JsonbTypeAdapter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

  // readonly property
  @Setter(onMethod_ = @JsonbTransient)
  private UUID uuid;
  private String name;
  private LocalDate birthdate; // TODO birth_date?
  @JsonbTypeAdapter(CustomerStateAdapter.class) // TODO register globally?
  private CustomerState state = CustomerState.ACTIVE;

  // Would work without any adapter, if the name() matches the JSON value.
  // Here: "active", "locked" and "disabled".
  //  - DON'T: This would break with Java naming conventions.
  public enum CustomerState {

    ACTIVE, LOCKED, DISABLED

  }

}
