package be.kdg.prog6.lobbyManagementContext.adapters.in;

import be.kdg.prog6.common.events.UserRegistrationEvent;
import be.kdg.prog6.lobbyManagementContext.ports.in.RegisterUseCase;
import be.kdg.prog6.lobbyManagementContext.ports.in.RegisterUserCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class LobbyEventListener {
    private static final Logger logger = LoggerFactory.getLogger(LobbyEventListener.class);
    private final RegisterUseCase registerUseCaseImpl;

    public LobbyEventListener(RegisterUseCase registerUseCaseImpl) {
        this.registerUseCaseImpl = registerUseCaseImpl;
    }

    @RabbitListener(queues = "user.registration.queue2")
    public void handleUserRegistrationEvent(UserRegistrationEvent event) {
        logger.info("Handling user registration event in the lobby for user: {} {} {}", event.getUserId(), event.getFirstName(), event.getLastName());

        String fullName = formatFullName(event.getFirstName(), event.getLastName());

        RegisterUserCommand command = new RegisterUserCommand( event.getUserId(),
                fullName
        );

        logger.info("Registering player lobby with full name: {}", fullName);

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
