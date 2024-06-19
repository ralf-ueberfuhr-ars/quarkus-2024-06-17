package de.schulung.sample.quarkus.boundary.config;

import io.quarkus.jsonb.JsonbConfigCustomizer;
import jakarta.enterprise.context.Dependent;
import jakarta.json.bind.JsonbConfig;
import jakarta.json.bind.config.PropertyNamingStrategy;

@Dependent
public class JsonSnakeCaseConfiguration implements JsonbConfigCustomizer {

  @Override
  public void customize(JsonbConfig jsonbConfig) {
    jsonbConfig.withPropertyNamingStrategy(
      PropertyNamingStrategy.LOWER_CASE_WITH_UNDERSCORES
    );
  }

}
