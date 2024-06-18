package de.schulung.sample.quarkus;

import jakarta.json.bind.annotation.JsonbTransient;
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
  private String state;

}
