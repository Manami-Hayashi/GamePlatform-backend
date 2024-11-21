package be.kdg.prog6.keycloakconfig;

import org.keycloak.Config;
import org.keycloak.events.EventListenerProviderFactory;

import org.keycloak.models.KeycloakSession;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class KeycloakEventListenerProviderFactory implements EventListenerProviderFactory {

    private final RabbitTemplate rabbitTemplate;

    public KeycloakEventListenerProviderFactory(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    @Override
    public org.keycloak.events.EventListenerProvider create(KeycloakSession keycloakSession) {
        return new EventListenerProvider(rabbitTemplate);
    }

    @Override
    public void init(Config.Scope config) {
    }

    @Override
    public void postInit(org.keycloak.models.KeycloakSessionFactory factory) {
    }

    @Override
    public void close() {
    }

    @Override
    public String getId() {
        return "custom-event-listener";
    }
}
