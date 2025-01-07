package be.kdg.prog6.lobbyManagementContext;

import be.kdg.prog6.lobbyManagementContext.domain.Game;
import be.kdg.prog6.lobbyManagementContext.domain.GameId;
import be.kdg.prog6.lobbyManagementContext.ports.in.GameIdResponse;
import be.kdg.prog6.lobbyManagementContext.ports.in.GetGameIdByNameUseCase;
import be.kdg.prog6.lobbyManagementContext.ports.out.LobbyGameCreatedPort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;

@SpringBootTest
@ActiveProfiles("test")
class GetGameIdByNameUseCaseImplIntegrationTest extends AbstractDatabaseTest {

    @Autowired
    private GetGameIdByNameUseCase getGameIdByNameUseCase;

    @Autowired
    private LobbyGameCreatedPort lobbyGameCreatedPort;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @MockBean
    private AmqpAdmin amqpAdmin;

    @BeforeEach
    void setUp() {
        // Insert the game "Chess" into the database
        Game game = new Game(new GameId(UUID.randomUUID()), "Test");
        lobbyGameCreatedPort.createLobbyGame(game);
    }

//    @Test
//    void shouldGetGameIdByNameSuccessfully() {
//        // Arrange
//        String gameName = "Test";
//
//        // Act
//        List<GameIdResponse> gameIds = (List<GameIdResponse>) getGameIdByNameUseCase.getGameIdByName(gameName);
//
//        // Assert
//        Assertions.assertFalse(gameIds.isEmpty(), "The list of game IDs should not be empty");
//    }

    @Test
    void shouldFailToGetGameIdByName() {
        // Arrange
        String gameName = null;

        // Act & Assert
        Assertions.assertThrows(IllegalArgumentException.class, () -> getGameIdByNameUseCase.getGameIdByName(gameName));
    }
}