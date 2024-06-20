package de.schulung.sample.quarkus.infrastructure;

import io.quarkus.runtime.Startup;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.infrastructure.Infrastructure;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;
import org.eclipse.microprofile.health.Readiness;

@ApplicationScoped
public class LongRunningStartup {

  private volatile boolean initialized = false;
  private volatile boolean initializingFailed = false;

  // check during startup: http://localhost:9090/q/health/ready

  private Uni<Void> doLongRunningInitialization() {
    try {
      Thread.sleep(5000);
      initialized = true;
      return Uni.createFrom().voidItem();
    } catch (InterruptedException e) {
      initializingFailed = true;
      return Uni.createFrom().failure(e);
    }
  }

  @Startup
  public void init() {
    Uni
      .createFrom()
      .voidItem()
      .emitOn(Infrastructure.getDefaultWorkerPool())
      .subscribe()
      .with(v -> this.doLongRunningInitialization());
  }

  @Produces
  @ApplicationScoped
  @Liveness
  public HealthCheck myStartupLiveness() {
    return new HealthCheck() {
      @Override
      public HealthCheckResponse call() {
        final var result = HealthCheckResponse
          .named("My long-running startup");
        return (initializingFailed ? result.down() : result.up()).build();
      }
    };
  }

  @Produces
  @ApplicationScoped
  @Readiness
  public HealthCheck myStartupReadyness() {
    return new HealthCheck() {
      @Override
      public HealthCheckResponse call() {
        final var result = HealthCheckResponse
          .named("My long-running startup");
        return (initialized ? result.up() : result.down()).build();
      }
    };
  }

}
