package be.kdg.prog6.storeContext.adapters.in;

import be.kdg.prog6.common.events.UserRegistrationEvent;
import be.kdg.prog6.storeContext.port.in.RegisterCustomerUseCase;
import be.kdg.prog6.storeContext.port.in.RegisterUserCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class StoreRegisterUserEventListener {
    private static final Logger logger = LoggerFactory.getLogger(StoreRegisterUserEventListener.class);
    private final RegisterCustomerUseCase registerUseCaseImpl;

    public StoreRegisterUserEventListener(RegisterCustomerUseCase registerUseCaseImpl) {
        this.registerUseCaseImpl = registerUseCaseImpl;
    }

    @RabbitListener(queues = "user.registration.queue5")
    public void handleUserRegistrationEvent(UserRegistrationEvent event) {
        logger.info("Handling user registration event in the store for user: {} {} {}", event.getUserId(), event.getFirstName(), event.getLastName());

        String fullName = formatFullName(event.getFirstName(), event.getLastName());

        RegisterUserCommand command = new RegisterUserCommand( event.getUserId(),
                fullName
        );

        logger.info("Registering player store with full gameName: {}", fullName);

        registerUseCaseImpl.registerCustomer(command);
    }

    // Helper method to format the full gameName
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
