// MatchWithFriendUseCaseImplIntegrationTest.java
package be.kdg.prog6.lobbyManagementContext;

import be.kdg.prog6.lobbyManagementContext.adapters.out.db.LobbyGameJpaEntity;
import be.kdg.prog6.lobbyManagementContext.adapters.out.db.LobbyGameJpaRepository;
import be.kdg.prog6.lobbyManagementContext.adapters.out.db.LobbyPlayerJpaEntity;
import be.kdg.prog6.lobbyManagementContext.adapters.out.db.LobbyPlayerJpaRepository;
import be.kdg.prog6.lobbyManagementContext.domain.Game;
import be.kdg.prog6.lobbyManagementContext.domain.GameId;
import be.kdg.prog6.lobbyManagementContext.domain.Player;
import be.kdg.prog6.lobbyManagementContext.domain.PlayerId;
import be.kdg.prog6.lobbyManagementContext.ports.in.MatchPlayersCommand;
import be.kdg.prog6.lobbyManagementContext.ports.in.MatchWithFriendUseCase;
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
class MatchWithFriendUseCaseImplIntegrationTest extends AbstractDatabaseTest {

    @Autowired
    private MatchWithFriendUseCase matchWithFriendUseCase;

    @Autowired
    private LobbyPlayerJpaRepository lobbyPlayerJpaRepository;

    @Autowired
    private LobbyGameJpaRepository lobbyGameJpaRepository;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @MockBean
    private AmqpAdmin amqpAdmin;

    private UUID playerId;
    private UUID friendId;
    private UUID gameId;

    @BeforeEach
    void setUp() {
        playerId = UUID.randomUUID();
        friendId = UUID.randomUUID();
        gameId = UUID.randomUUID();

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
    }

    @Test
    void shouldMatchWithFriendSuccessfully() {
        // Arrange
        MatchPlayersCommand command = new MatchPlayersCommand(playerId, friendId, gameId);

        // Act & Assert
        Assertions.assertDoesNotThrow(() -> matchWithFriendUseCase.matchPlayers(command));
    }

    @Test
    void shouldFailToMatchWithFriend() {
        // Arrange
        MatchPlayersCommand command = new MatchPlayersCommand(null, friendId, gameId);

        // Act & Assert
        Assertions.assertThrows(InvalidDataAccessApiUsageException.class, () -> matchWithFriendUseCase.matchPlayers(command));
    }
}