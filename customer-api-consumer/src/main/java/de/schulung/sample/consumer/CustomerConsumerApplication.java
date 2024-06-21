package de.schulung.sample.consumer;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class CustomerConsumerApplication {

  public static void main(String[] args) {
    Quarkus.run(args);
  }

}
