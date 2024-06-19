package de.schulung.sample.quarkus.boundary;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.validation.constraints.NotNull;
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
public class CustomerDto {

  // readonly property
  @Setter(onMethod_ = @JsonbTransient)
  private UUID uuid;
  @Size(min = 3, max = 100)
  @NotNull
  private String name;
  //@JsonbProperty("birth_date")
  private LocalDate birthDate;
  @Pattern(regexp = "active|locked|disabled")
  @NotNull
  private String state;

}
