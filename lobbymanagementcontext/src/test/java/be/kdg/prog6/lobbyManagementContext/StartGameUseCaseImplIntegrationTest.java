package be.kdg.prog6.lobbyManagementContext;

import be.kdg.prog6.common.exceptions.GameSessionNotReadyException;
import be.kdg.prog6.lobbyManagementContext.adapters.out.db.*;
import be.kdg.prog6.lobbyManagementContext.domain.Game;
import be.kdg.prog6.lobbyManagementContext.domain.GameId;
import be.kdg.prog6.lobbyManagementContext.domain.Lobby;
import be.kdg.prog6.lobbyManagementContext.domain.Player;
import be.kdg.prog6.lobbyManagementContext.domain.PlayerId;
import be.kdg.prog6.lobbyManagementContext.ports.in.StartGameUseCase;
import be.kdg.prog6.lobbyManagementContext.ports.in.ReadyUpResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@ActiveProfiles("test")
class StartGameUseCaseImplIntegrationTest extends AbstractDatabaseTest {

    @Autowired
    private StartGameUseCase startGameUseCase;

    @Autowired
    private LobbyJpaRepository lobbyJpaRepository;

    @Autowired
    private LobbyPlayerJpaRepository lobbyPlayerJpaRepository;

    @Autowired
    private LobbyGameJpaRepository lobbyGameJpaRepository;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @MockBean
    private AmqpAdmin amqpAdmin;

    private UUID lobbyId;
    private UUID gameId;
    private UUID playerId;
    private UUID friendId;

    @BeforeEach
    void setUp() {
        playerId = UUID.randomUUID();
        friendId = UUID.randomUUID();
        gameId = UUID.randomUUID();
        lobbyId = UUID.randomUUID(); // Initialize lobbyId here

        // Create and save a game
        Game game = new Game(new GameId(gameId), "Test Game");
        LobbyGameJpaEntity gameEntity = new LobbyGameJpaEntity(game.getGameId().id(), game.getGameName());
        lobbyGameJpaRepository.save(gameEntity);

        // Create and save a player who is online
        Player player = new Player(new PlayerId(playerId), "Player1", Instant.now(), null, null, true, List.of());
        LobbyPlayerJpaEntity playerEntity = new LobbyPlayerJpaEntity(player.getPlayerId().id(), player.getName());
        lobbyPlayerJpaRepository.save(playerEntity);

        // Create and save a friend who is online
        Player friend = new Player(new PlayerId(friendId), "Friend1", Instant.now(), null, null, true, List.of());
        LobbyPlayerJpaEntity friendEntity = new LobbyPlayerJpaEntity(friend.getPlayerId().id(), friend.getName());
        lobbyPlayerJpaRepository.save(friendEntity);

        // Create and save a lobby
        Lobby lobby = new Lobby(lobbyId, List.of(player.getPlayerId(), friend.getPlayerId()), game.getGameId());
        LobbyJpaEntity lobbyEntity = new LobbyJpaEntity(lobby.getLobbyId(), Instant.now(), List.of(playerEntity, friendEntity), gameEntity);
        lobbyJpaRepository.save(lobbyEntity);

        // Update players with lobby information
        player.setLobbyId(lobby.getLobbyId());
        friend.setLobbyId(lobby.getLobbyId());
        lobbyPlayerJpaRepository.save(playerEntity);
        lobbyPlayerJpaRepository.save(friendEntity);
    }

//    @Test
//    void shouldStartGameSuccessfully() {
//        // Act & Assert
//        Assertions.assertDoesNotThrow(() -> {
//            ReadyUpResponse response = startGameUseCase.readyUp(lobbyId);
//            Assertions.assertTrue(response.isGameSessionStarted());
//        });
//    }

    @Test
    void shouldFailToStartGame() {
        // Arrange
        UUID invalidLobbyId = UUID.randomUUID();

        // Act & Assert
        Assertions.assertThrows(GameSessionNotReadyException.class, () -> startGameUseCase.readyUp(invalidLobbyId));
    }
}