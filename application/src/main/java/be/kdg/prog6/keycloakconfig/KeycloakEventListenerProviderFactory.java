package be.kdg.prog6.keycloakconfig;

import be.kdg.prog6.messaging.RabbitMQTopology;
import org.keycloak.Config;
import org.keycloak.events.EventListenerProviderFactory;

import org.keycloak.models.KeycloakSession;
import org.slf4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;

@Component
public class KeycloakEventListenerProviderFactory implements EventListenerProviderFactory {

    private RabbitTemplate rabbitTemplate;
    private Logger logger = org.slf4j.LoggerFactory.getLogger(KeycloakEventListenerProviderFactory.class);

    @Autowired
    private AnnotationConfigApplicationContext springContext;

    public KeycloakEventListenerProviderFactory() {
    }

    @Override
    public org.keycloak.events.EventListenerProvider create(KeycloakSession keycloakSession) {
        if (rabbitTemplate == null) {
            // Initialize Spring context and get RabbitTemplate bean
            if (springContext == null) {
                springContext = new AnnotationConfigApplicationContext(RabbitMQTopology.class); // Load your Spring config class
            }
            rabbitTemplate = springContext.getBean("internalRabbitTemplate", RabbitTemplate.class); // Get the RabbitTemplate bean from Spring context
        }
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
