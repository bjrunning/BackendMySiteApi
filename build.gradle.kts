import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent

plugins {
    application
    checkstyle
    jacoco
    id("io.freefair.lombok") version "8.6"
    id("io.sentry.jvm.gradle") version "4.6.0"
    id("org.springframework.boot") version "3.3.0"
    id("io.spring.dependency-management") version "1.1.5"
}

group = "org.example"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_21
}

application {
    mainClass.set("org.example.BackendMySiteApiApplication")
}

repositories {
    mavenCentral()
}

buildscript {
    repositories {
        mavenCentral()
    }
}

sentry {
    includeSourceContext.set(true)

    org.set("bjrunning")
    projectName.set("backendmysiteapi")
    authToken.set(System.getenv("SENTRY_AUTH_TOKEN"))
}

dependencies {
    implementation("org.springframework.boot:spring-boot-devtools")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-configuration-processor")

    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.5.0")

    implementation("org.openapitools:jackson-databind-nullable:0.2.6")

    implementation("org.mapstruct:mapstruct:1.6.0.Beta1")
    annotationProcessor("org.mapstruct:mapstruct-processor:1.6.0.Beta1")

    implementation("org.instancio:instancio-junit:4.7.0")
    implementation("net.javacrumbs.json-unit:json-unit-assertj:3.2.7")
    implementation("net.datafaker:datafaker:2.2.2")

    runtimeOnly("com.h2database:h2")
    runtimeOnly("org.postgresql:postgresql")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
    testLogging {
        exceptionFormat = TestExceptionFormat.FULL
        events = mutableSetOf(TestLogEvent.FAILED, TestLogEvent.PASSED, TestLogEvent.SKIPPED)
        showStandardStreams = true
    }
}

tasks.jacocoTestReport {
    reports {
        xml.required = true
    }
}

tasks.sentryBundleSourcesJava {
    enabled = System.getenv("SENTRY_AUTH_TOKEN") != null
}
