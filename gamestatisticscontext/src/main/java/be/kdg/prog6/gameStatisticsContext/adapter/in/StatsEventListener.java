package be.kdg.prog6.gameStatisticsContext.adapter.in;

import be.kdg.prog6.common.events.UserRegistrationEvent;
import be.kdg.prog6.gameStatisticsContext.port.in.RegisterPlayerUseCase;
import be.kdg.prog6.gameStatisticsContext.port.in.RegisterUserCommand;
import org.slf4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class StatsEventListener {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(StatsEventListener.class);
    private final RegisterPlayerUseCase registerPlayerUseCase;

    public StatsEventListener(RegisterPlayerUseCase registerPlayerUseCase) {
        this.registerPlayerUseCase = registerPlayerUseCase;
    }

    @RabbitListener(queues = "user.registration.queue4")
    public void handleUserRegistrationEvent(UserRegistrationEvent event){
        logger.info("Handling user registration event in the stats for user: {} {} {}", event.getUserId(), event.getFirstName(), event.getLastName());
        String fullName = formatFullName(event.getFirstName(), event.getLastName());

        RegisterUserCommand command = new RegisterUserCommand(event.getUserId(), fullName);

        logger.info("Registering player stats with full name: {}", fullName);

        registerPlayerUseCase.registerPlayer(command);
    }

    private String formatFullName(String firstName, String lastName){
        if(firstName == null || firstName.isEmpty()){
            return lastName != null ? lastName : "";
        }
        if(lastName == null || lastName.isEmpty()){
            return firstName;
        }
        return firstName + " " + lastName;
    }

}
