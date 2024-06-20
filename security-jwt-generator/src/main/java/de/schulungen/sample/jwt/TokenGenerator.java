package de.schulungen.sample.jwt;

import io.smallrye.jwt.build.Jwt;
import org.eclipse.microprofile.jwt.Claims;

import java.util.Set;

public class TokenGenerator {

  public static void main(String[] args) {
    System.setProperty("smallrye.jwt.sign.key.location", "privateKey.pem");
    final var token =
      Jwt.issuer("https://samples.schulungen.de/issuer")
        .upn("ralf.ueberfuhr@ars.de")
        .groups(Set.of("User", "Admin"))
        .claim(Claims.birthdate.name(), "1981-10-11")
        .sign();
    System.out.println(token);
  }

}
