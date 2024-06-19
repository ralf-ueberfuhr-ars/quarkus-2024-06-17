package de.schulung.sample.quarkus.domain;

import de.schulung.sample.quarkus.domain.events.CustomerCreatedEvent;
import io.quarkus.arc.log.LoggerName;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import org.jboss.logging.Logger;

@ApplicationScoped
public class CustomerEventsLogger {

  // https://quarkus.io/guides/logging
  @LoggerName("customers")
  Logger log;

  void onCustomerCreated(@Observes CustomerCreatedEvent evt) {
    log.info("customer created");
  }

}
