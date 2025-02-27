package be.kdg.prog6.lobbyManagementContext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConf {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}