package be.kdg.prog6.gameManagementContext;

import be.kdg.prog6.gameManagementContext.adapters.out.db.GameJpaEntity;
import be.kdg.prog6.gameManagementContext.adapters.out.db.GameJpaRepository;
import be.kdg.prog6.gameManagementContext.adapters.out.db.GameMngPlayerJpaRepository;
import be.kdg.prog6.gameManagementContext.domain.Game;
import be.kdg.prog6.gameManagementContext.domain.GameId;
import be.kdg.prog6.gameManagementContext.ports.in.RegisterUseCase;
import be.kdg.prog6.gameManagementContext.ports.in.RegisterUserCommand;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class GamePlayerRegisterUseCaseImplIntegrationTest extends AbstractDatabaseTest {

    @Autowired
    private RegisterUseCase registerUseCase;

    @Autowired
    private GameJpaRepository gameJpaRepository;

    @Autowired
    private GameMngPlayerJpaRepository gameMngPlayerJpaRepository;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @MockBean
    private AmqpAdmin amqpAdmin;


    @Test
    void shouldGamePlayerRegisterSuccessfully() {
        // Arrange
        RegisterUserCommand registerUserCommand = new RegisterUserCommand(TestIds.PLAYER_ID, "TestPlayer");

        // Act
        registerUseCase.registerPlayer(registerUserCommand);

        // Assert
        assertTrue(gameMngPlayerJpaRepository.findById(TestIds.PLAYER_ID).isPresent());

        // Cleanup
        gameMngPlayerJpaRepository.deleteAll();
    }

    @Test
    void shouldFailToRegisterGamePlayer() {
        // Arrange
        RegisterUserCommand registerUserCommand = new RegisterUserCommand(TestIds.PLAYER_ID, "TestPlayer");

        // Act & Assert
        registerUseCase.registerPlayer(registerUserCommand);
        assertThrows(IllegalArgumentException.class, () -> {
            registerUseCase.registerPlayer(registerUserCommand);
        });

        // Cleanup
        gameMngPlayerJpaRepository.deleteAll();
    }

}
