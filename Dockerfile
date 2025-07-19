FROM eclipse-temurin:21.0.7_6-jre-noble

WORKDIR /app

COPY build/quarkus-app/ /app

CMD ["java", "-jar", "app-books-unspecified-runner.jar"]
