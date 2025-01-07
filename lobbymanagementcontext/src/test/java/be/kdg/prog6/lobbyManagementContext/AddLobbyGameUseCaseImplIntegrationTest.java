package be.kdg.prog6.lobbyManagementContext;

import be.kdg.prog6.lobbyManagementContext.adapters.out.db.LobbyGameJpaRepository;
import be.kdg.prog6.lobbyManagementContext.domain.Game;
import be.kdg.prog6.lobbyManagementContext.domain.GameId;
import be.kdg.prog6.lobbyManagementContext.ports.in.AddLobbyGameUseCase;
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
class AddLobbyGameUseCaseImplIntegrationTest extends AbstractDatabaseTest {

    @Autowired
    private AddLobbyGameUseCase addLobbyGameUseCase;

    @Autowired
    private LobbyGameJpaRepository lobbyGameJpaRepository;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @MockBean
    private AmqpAdmin amqpAdmin;

    @Test
    void shouldAddLobbyGameSuccessfully() {


        // Arrange
        Game game = new Game(new GameId(TestIds.GAME_ID), "Chess");

        // Act & Assert
        Assertions.assertDoesNotThrow(() -> addLobbyGameUseCase.addLobbyGame(game));
        lobbyGameJpaRepository.deleteAll();
    }

    @Test
    void shouldFailToAddLobbyGame() {
        // Arrange

        Game game = new Game(null, "Chess");

        // Act & Assert
        Assertions.assertThrows(NullPointerException.class, () -> addLobbyGameUseCase.addLobbyGame(game));
    }
}