plugins {
    id("java")
    id("io.quarkus") version "3.22.2"
    id("io.freefair.lombok") version "8.13.1"
}

group = "org.example"
version = "unspecified"

repositories {
    mavenCentral()
}

val quarkusVersion = "3.22.1"

dependencies {
    implementation(enforcedPlatform("io.quarkus.platform:quarkus-bom:${quarkusVersion}"))

    //--- CONTENEDORES --
    //CDI
    implementation("io.quarkus:quarkus-arc")

    //REST
    implementation("io.quarkus:quarkus-rest")
    implementation("io.quarkus:quarkus-rest-jsonb")

    //consumir json --DEPENDENCICAS DEL CLIENTE
    implementation("io.quarkus:quarkus-rest-client")
    implementation("io.quarkus:quarkus-rest-client-jsonb")


    //JPA
    implementation("io.quarkus:quarkus-hibernate-orm-panache")
    implementation("io.quarkus:quarkus-jdbc-postgresql")

    //para DTOS
    implementation("org.modelmapper:modelmapper:3.2.3")

    //SERIVE DISCOVERY
   // implementation("io.quarkus:quarkus-smallrye-stork")
    //implementation("io.smallrye.stork:stork-service-discovery-static-list")
    //service Discovery
    implementation("io.quarkus:quarkus-smallrye-stork")
    implementation("io.smallrye.stork:stork-service-discovery-consul")
    implementation("io.smallrye.reactive:smallrye-mutiny-vertx-consul-client")

    // LIBRERIA DE TOLERANCIA A FALLOS
    implementation("io.quarkus:quarkus-smallrye-fault-tolerance")

    //Telemetria metricas
    implementation("io.quarkus:quarkus-micrometer-registry-prometheus")
    implementation("io.quarkus:quarkus-jackson")

    implementation("io.quarkus:quarkus-smallrye-health")

}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-parameters")

}