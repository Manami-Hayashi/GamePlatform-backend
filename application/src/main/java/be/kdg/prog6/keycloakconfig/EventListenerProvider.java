package be.kdg.prog6.keycloakconfig;

import org.keycloak.events.Event;
import org.keycloak.events.EventType;
import org.keycloak.events.admin.AdminEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import be.kdg.prog6.common.events.UserRegistrationEvent;

import java.util.UUID;

public class EventListenerProvider implements org.keycloak.events.EventListenerProvider {
    private final RabbitTemplate rabbitTemplate;
    private final Logger logger = LoggerFactory.getLogger(EventListenerProvider.class);

    public EventListenerProvider(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void onEvent(Event event) {
        logger.info("Processing event: {}", event.getType());

        if (event.getType() == null) {
            logger.error("Event type is null");
            return;
        }

        if (event.getType().toString().equals("REGISTER")) {
            System.out.println("Event Occurred:" + toString(event));
        }

        // Listen for registration events
        if (EventType.REGISTER.equals(event.getType())) {
            try {
                UUID userId = UUID.fromString(event.getUserId());
                String username = event.getDetails().get("username");
                String email = event.getDetails().get("email");
                String firstName = event.getDetails().getOrDefault("firstName", ""); // Fallback to empty if not present
                String lastName = event.getDetails().getOrDefault("lastName", "");   // Fallback to empty if not present

                if (username == null || email == null) {
                    logger.error("Username or email is null");
                    return;
                }

                logger.info("Processing registration event for user: {}", username);
                logger.info("Event details: userId={} email={}", userId, email);

                // Send event to RabbitMQ
                UserRegistrationEvent registrationEvent = new UserRegistrationEvent(userId, username, email, firstName, lastName);
                rabbitTemplate.convertAndSend("user.registration.exchange", "user.registration", registrationEvent);

                logger.info("User registration event sent to RabbitMQ: {}", registrationEvent);
            } catch (IllegalArgumentException e) {
                logger.error("Invalid UUID string: {}", event.getUserId(), e);
            } catch (Exception e) {
                logger.error("Error processing registration event", e);
            }
        } else {
            logger.warn("Unhandled event type: {}", event.getType());
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
        // No resources to close
    }
}