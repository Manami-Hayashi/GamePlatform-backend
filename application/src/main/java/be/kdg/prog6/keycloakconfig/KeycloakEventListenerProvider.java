package be.kdg.prog6.keycloakconfig;

import org.keycloak.events.Event;
import org.keycloak.events.EventListenerProvider;
import org.keycloak.events.EventType;
import org.keycloak.events.admin.AdminEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import be.kdg.prog6.common.events.UserRegistrationEvent;

import java.util.UUID;

public class KeycloakEventListenerProvider implements EventListenerProvider {
    private final RabbitTemplate rabbitTemplate;

    public KeycloakEventListenerProvider(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void onEvent(Event event) {
        // Listen for registration events
        if (EventType.REGISTER.equals(event.getType())) {
            UUID userId = UUID.fromString(event.getUserId());
            String username = event.getDetails().get("username");
            String email = event.getDetails().get("email");
            String firstName = event.getDetails().getOrDefault("firstName", ""); // Fallback to empty if not present
            String lastName = event.getDetails().getOrDefault("lastName", "");   // Fallback to empty if not present

            System.out.println("Processing registration event for user: " + username);
            System.out.println("Event details: userId=" + userId + ", email=" + email);

            // Send event to RabbitMQ
            UserRegistrationEvent registrationEvent = new UserRegistrationEvent(userId, username, email, firstName, lastName);
            rabbitTemplate.convertAndSend("user.registration.exchange", "user.registration", registrationEvent);

            System.out.println("User registration event sent to RabbitMQ: " + registrationEvent);
        }
    }

    @Override
    public void onEvent(AdminEvent adminEvent, boolean b) {

    }

    @Override
    public void close() {

    }
}
