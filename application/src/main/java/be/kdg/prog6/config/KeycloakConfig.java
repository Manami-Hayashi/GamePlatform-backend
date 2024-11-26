package be.kdg.prog6.config;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfig {
    @Bean
    public AnnotationConfigApplicationContext annotationConfigApplicationContext() {
        return new AnnotationConfigApplicationContext();
    }
}
