package de.schulung.sample.quarkus.domain.events;

import de.schulung.sample.quarkus.domain.Customer;

public record CustomerCreatedEvent(
  Customer customer
) {
}
