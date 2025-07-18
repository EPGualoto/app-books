package com.programacion.distribuida.book.health;

import jakarta.enterprise.context.ApplicationScoped;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Liveness;

@ApplicationScoped
@Liveness
// http://localhost:6060/q/health/live
public class AuthorsHealthCheck implements HealthCheck {

    @Override
    public HealthCheckResponse call() {
        return HealthCheckResponse.named("app-books-health")
                .withData("app-books-health", "UP ->Esta ok")
                .up()
                .build();
    }
}
