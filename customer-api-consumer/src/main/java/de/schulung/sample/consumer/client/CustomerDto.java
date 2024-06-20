package de.schulung.sample.consumer.client;

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
public class CustomerDto {

  // readonly property
  @Setter(onMethod_ = @JsonbTransient)
  private UUID uuid;
  private String name;
  //@JsonbProperty("birth_date")
  private LocalDate birthDate;
  private String state;

}
