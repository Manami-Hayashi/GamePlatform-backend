// ReadyUpPlayerUseCaseImplIntegrationTest.java
package be.kdg.prog6.lobbyManagementContext;

import be.kdg.prog6.lobbyManagementContext.domain.GameId;
import be.kdg.prog6.lobbyManagementContext.domain.Lobby;
import be.kdg.prog6.lobbyManagementContext.domain.PlayerId;
import be.kdg.prog6.lobbyManagementContext.ports.in.ReadyUpResponse;
import be.kdg.prog6.lobbyManagementContext.ports.in.ReadyUpPlayerUseCase;
import be.kdg.prog6.lobbyManagementContext.ports.out.SaveLobbyPort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
class ReadyUpPlayerUseCaseImplIntegrationTest extends AbstractDatabaseTest {

    @Autowired
    private ReadyUpPlayerUseCase readyUpPlayerUseCase;

    @Autowired
    private SaveLobbyPort saveLobbyPort;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @MockBean
    private AmqpAdmin amqpAdmin;

    private UUID lobbyId;
    private PlayerId playerId;

//    @BeforeEach
//    void setUp() {
//        lobbyId = UUID.randomUUID();
//        playerId = new PlayerId(UUID.randomUUID());
//        Lobby lobby = new Lobby(lobbyId, new GameId(UUID.randomUUID()));
//        lobby.addPlayer(playerId);
//        saveLobbyPort.saveLobby(lobby);
//    }
//
//    @Test
//    void shouldReadyUpPlayerSuccessfully() {
//        // Act
//        ReadyUpResponse response = readyUpPlayerUseCase.readyUp(playerId);
//
//        // Assert
//        Assertions.assertTrue(response.isGameSessionStarted());
//    }

    @Test
    void shouldFailToReadyUpPlayerWhenLobbyNotFound() {
        // Arrange
        UUID playerId = UUID.randomUUID();

        // Act & Assert
        Assertions.assertThrows(NullPointerException.class, () -> readyUpPlayerUseCase.readyUp(new PlayerId(playerId)));
    }

    @Test
    void shouldFailToReadyUpPlayerWhenPlayerNotFound() {
        // Arrange
        UUID playerId = UUID.randomUUID();

        // Act & Assert
        Assertions.assertThrows(NullPointerException.class, () -> readyUpPlayerUseCase.readyUp(new PlayerId(playerId)));
    }
}