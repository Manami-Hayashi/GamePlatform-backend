package be.kdg.prog6.chatbotContext.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {
    @Bean
    public RestTemplate chatRestTemplate() {
        return new RestTemplate();
    }
}
