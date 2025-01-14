package be.kdg.prog6.lobbyManagementContext;

import be.kdg.prog6.lobbyManagementContext.ports.in.RegisterUseCase;
import be.kdg.prog6.lobbyManagementContext.ports.in.RegisterUserCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

@SpringBootTest
@ActiveProfiles("test")
class LobbyRegisterUseCaseImplIntegrationTest extends AbstractDatabaseTest {

    @Autowired
    private RegisterUseCase registerUseCase;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @MockBean
    private AmqpAdmin amqpAdmin;

    @Test
    void shouldRegisterPlayerSuccessfully() {
        // Arrange
        RegisterUserCommand command = new RegisterUserCommand(UUID.randomUUID(), "John");

        // Act & Assert
        Assertions.assertDoesNotThrow(() -> registerUseCase.registerPlayer(command));
    }

    @Test
    void shouldFailToRegisterPlayer() {
        // Arrange
        RegisterUserCommand command = new RegisterUserCommand(null, "John");

        // Act & Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> registerUseCase.registerPlayer(command));
    }
}