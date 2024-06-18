package de.schulung.sample.quarkus;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
  @Size(min = 3, max = 100)
  private String name;
  @JsonbProperty("birth_date") // TODO -> use snake_case globally?
  private LocalDate birthdate;
  @Pattern(regexp = "active|locked|disabled")
  private String state;

}
