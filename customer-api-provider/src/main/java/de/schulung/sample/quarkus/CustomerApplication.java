package de.schulung.sample.quarkus;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class CustomerApplication {

    public static void main(String[] args) {
        Quarkus.run(args);
    }

}
