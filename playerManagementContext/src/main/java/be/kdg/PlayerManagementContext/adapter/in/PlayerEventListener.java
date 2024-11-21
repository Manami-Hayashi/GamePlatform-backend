package be.kdg.PlayerManagementContext.adapter.in;

import be.kdg.PlayerManagementContext.core.RegisterUseCaseImpl;
import be.kdg.PlayerManagementContext.port.in.RegisterUserCommand;
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
        this.registerUseCaseImpl = registerUseCaseImpl;
    }

    @RabbitListener(queues = "user.registration.queue", containerFactory = "rabbitListenerContainerFactory")
    public void handleUserRegistrationEvent(UserRegistrationEvent event) {
        logger.info("Handling user registration event for user: {}", event.getUserId());

        RegisterUserCommand command = new RegisterUserCommand( event.getUserId(),
                event.getFirstName() + event.getLastName()
        );

        registerUseCaseImpl.registerPlayer(command);
    }

}
