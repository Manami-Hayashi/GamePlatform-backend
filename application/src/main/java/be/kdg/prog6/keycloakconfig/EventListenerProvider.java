package be.kdg.prog6.keycloakconfig;

import org.keycloak.events.Event;
import org.keycloak.events.EventType;
import org.keycloak.events.admin.AdminEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import be.kdg.prog6.common.events.UserRegistrationEvent;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.UUID;

public class EventListenerProvider implements org.keycloak.events.EventListenerProvider {

    public static final String USER_REGISTRATION_EXCHANGE = "user.registration.exchange";
    public static final String USER_REGISTRATION_ROUTING_KEY = "user.registration";

    private final RabbitTemplate rabbitTemplate;
    private final Logger logger = LoggerFactory.getLogger(EventListenerProvider.class);

    public EventListenerProvider(@Qualifier("internalRabbitTemplate") RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void onEvent(Event event) {
        logger.info("Processing event: {}", event.getType());
        // Listen for registration events
        if (EventType.REGISTER.equals(event.getType())) {

            UUID userId = UUID.fromString(event.getUserId());
            String username = event.getDetails().get("username");
            String email = event.getDetails().get("email");
            String firstName = event.getDetails().getOrDefault("first_name", ""); // Fallback to empty if not present
            String lastName = event.getDetails().getOrDefault("last_name", "");   // Fallback to empty if not present

            logger.info("Event details: {}", event.getDetails());

            logger.info("Processing registration event for user: {}", username);
            logger.info("Event details: userId={} email={}", userId, email);

            // Send event to RabbitMQ
            UserRegistrationEvent registrationEvent = new UserRegistrationEvent(userId, username, email, firstName, lastName);
            try {
                rabbitTemplate.convertAndSend(USER_REGISTRATION_EXCHANGE, USER_REGISTRATION_ROUTING_KEY, registrationEvent);
                logger.info("User registration event sent to RabbitMQ: {}", registrationEvent);
            } catch (Exception e) {
                logger.error("Failed to send event to RabbitMQ", e);
            }

        }
    }

    private String toString(Event event) {
        return "Event{" +
                "type=" + event.getType() +
                ", realmId='" + event.getRealmId() + '\'' +
                ", clientId='" + event.getClientId() + '\'' +
                ", userId='" + event.getUserId() + '\'' +
                ", ipAddress='" + event.getIpAddress() + '\'' +
                ", details=" + event.getDetails() +
                '}';
    }

    @Override
    public void onEvent(AdminEvent adminEvent, boolean b) {
        logger.info("Processing registration event for admin: {}", adminEvent.getId());

    }

    @Override
    public void close() {
    }
}
