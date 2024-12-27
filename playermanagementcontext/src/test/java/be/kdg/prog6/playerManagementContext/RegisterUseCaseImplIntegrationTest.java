package be.kdg.prog6.playerManagementContext;

import be.kdg.prog6.playerManagementContext.adapters.out.db.PlayerJpaEntity;
import be.kdg.prog6.playerManagementContext.adapters.out.db.PlayerJpaRepository;
import be.kdg.prog6.playerManagementContext.domain.Player;
import be.kdg.prog6.playerManagementContext.domain.PlayerId;
import be.kdg.prog6.playerManagementContext.ports.in.RegisterUseCase;
import be.kdg.prog6.playerManagementContext.ports.in.RegisterUserCommand;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class RegisterUseCaseImplIntegrationTest extends AbstractDatabaseTest {

    @Autowired
    private RegisterUseCase registerUseCase;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @MockBean
    private AmqpAdmin amqpAdmin;

    @Autowired
    private PlayerJpaRepository playerJpaRepository;

    @Test
    void shouldRegisterSuccessfully() {
        // Arrange
        Player player = new Player(new PlayerId(TestIds.PLAYER_ID), "Noah");
        RegisterUserCommand registerUserCommand = new RegisterUserCommand(player.getPlayerId().id(), player.getName());

        // Act & Assert
        Assertions.assertDoesNotThrow(() -> registerUseCase.registerPlayer(registerUserCommand));

        // Cleanup
        playerJpaRepository.deleteAll();
    }

    @Test
    void shouldFailToRegister() {
        // Arrange
        Player player = new Player(new PlayerId(TestIds.PLAYER_ID), "Noah");
        RegisterUserCommand registerUserCommand = new RegisterUserCommand(player.getPlayerId().id(), player.getName());
        playerJpaRepository.save(toPlayerJpaEntity(player));

        // Act & Assert
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                registerUseCase.registerPlayer(registerUserCommand));

        // Cleanup
        playerJpaRepository.deleteAll();
    }

    private PlayerJpaEntity toPlayerJpaEntity(Player player) {
        return new PlayerJpaEntity(player.getPlayerId().id(), player.getName());
    }
}
