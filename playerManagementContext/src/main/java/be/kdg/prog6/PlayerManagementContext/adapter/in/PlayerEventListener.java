package be.kdg.prog6.PlayerManagementContext.adapter.in;

import be.kdg.prog6.PlayerManagementContext.core.RegisterUseCaseImpl;
import be.kdg.prog6.PlayerManagementContext.port.in.RegisterUserCommand;
import be.kdg.prog6.common.events.UserRegistrationEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PlayerEventListener {
    private static final Logger logger = LoggerFactory.getLogger(PlayerEventListener.class);
    private final RegisterUseCaseImpl registerUseCaseImpl;

    public PlayerEventListener(RegisterUseCaseImpl registerUseCaseImpl) {
        logger.info("PlayerEventListener created");
        this.registerUseCaseImpl = registerUseCaseImpl;
    }

    @RabbitListener(queues = "user.registration.queue")
    public void handleUserRegistrationEvent(UserRegistrationEvent event) {
        logger.info("Handling user registration event for user: {} {} {}", event.getUserId(), event.getFirstName(), event.getLastName());

        String fullName = formatFullName(event.getFirstName(), event.getLastName());

        RegisterUserCommand command = new RegisterUserCommand( event.getUserId(),
                fullName
        );

        logger.info("Registering player with full name: {}", fullName);

        registerUseCaseImpl.registerPlayer(command);
    }

    // Helper method to format the full name
    private String formatFullName(String firstName, String lastName) {
        if (firstName == null || firstName.isEmpty()) {
            return lastName != null ? lastName : "";
        }
        if (lastName == null || lastName.isEmpty()) {
            return firstName;
        }
        return firstName + " " + lastName;
    }

}
