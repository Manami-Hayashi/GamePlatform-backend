package be.kdg.prog6.storeContext.core;

import be.kdg.prog6.storeContext.domain.Customer;
import be.kdg.prog6.storeContext.domain.CustomerId;

import be.kdg.prog6.storeContext.port.in.RegisterCustomerUseCase;
import be.kdg.prog6.storeContext.port.in.RegisterUserCommand;
import be.kdg.prog6.storeContext.port.out.CustomerCreatedPort;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class RegisterCustomerUseCaseImpl implements RegisterCustomerUseCase {
    private final Logger logger = org.slf4j.LoggerFactory.getLogger(RegisterCustomerUseCaseImpl.class);
    private final CustomerCreatedPort customerCreatedPort;



    public RegisterCustomerUseCaseImpl(CustomerCreatedPort customerCreatedPort) {
        this.customerCreatedPort = customerCreatedPort;
    }

    @Override
    public void registerCustomer(RegisterUserCommand command) {
        logger.info("Registering customer with gameName on store: {}", command.gameName());
        CustomerId customerId = new CustomerId(command.customerId());
        Customer customer = new Customer(customerId, command.gameName());

        customerCreatedPort.createPlayer(customer);
        logger.info("Player registered with id and gameName: {} {}", customerId, customer.getName());
    }

}
