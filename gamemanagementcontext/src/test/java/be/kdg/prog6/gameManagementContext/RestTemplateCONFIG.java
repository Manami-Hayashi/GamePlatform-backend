package be.kdg.prog6.gameManagementContext;

import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

public class RestTemplateCONFIG {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
