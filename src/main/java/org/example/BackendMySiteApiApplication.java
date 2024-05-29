package org.example;

import io.sentry.Sentry;
import net.datafaker.Faker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BackendMySiteApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BackendMySiteApiApplication.class, args);
        try {
            throw new Exception("This is a test.");
        } catch (Exception e) {
            Sentry.captureException(e);
        }
    }

    @Bean
    public Faker getFaker() {
        return new Faker();
    }

}
