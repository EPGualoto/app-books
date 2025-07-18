package com.programacion.distribuida.book.health;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@ApplicationScoped
@Readiness

public class AuthorsReadinessCheck implements HealthCheck {

    @Inject
    DataSource dataSource;

    @Override
    public HealthCheckResponse call() {
        try {

            checkDatabaseConnection(); //para ver si se conecta a mi base de datos

            return HealthCheckResponse.named("app-books-health")
                    .withData("database", "available")
                    .withData("database-url", "jdbc:postgresql://localhost:5432/distribuida")
                    .up()
                    .build();

        } catch (Exception e) {
            return HealthCheckResponse.named("app-books-health")
                    .withData("database", "unavailable")
                    .withData("error", e.getMessage())
                    .down()
                    .build();
        }
    }

    private void checkDatabaseConnection() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {

            connection.createStatement().execute("SELECT 1");
        }
    }
}

