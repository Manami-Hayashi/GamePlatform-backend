// MatchWithRandomPlayerUseCaseImplIntegrationTest.java
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
import be.kdg.prog6.lobbyManagementContext.ports.in.MatchWithRandomPlayerUseCase;
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
class MatchWithRandomPlayerUseCaseImplIntegrationTest extends AbstractDatabaseTest {

    @Autowired
    private MatchWithRandomPlayerUseCase matchWithRandomPlayerUseCase;

    @Autowired
    private LobbyPlayerJpaRepository lobbyPlayerJpaRepository;

    @Autowired
    private LobbyGameJpaRepository lobbyGameJpaRepository;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @MockBean
    private AmqpAdmin amqpAdmin;

    private UUID playerId;
    private UUID gameId;

    @BeforeEach
    void setUp() {
        playerId = UUID.randomUUID();
        gameId = UUID.randomUUID();

        Game game = new Game(new GameId(gameId), "Test Game");
        LobbyGameJpaEntity gameEntity = new LobbyGameJpaEntity(game.getGameId().id(), game.getGameName());
        lobbyGameJpaRepository.save(gameEntity);


        // Create and save a player who is online
        Player player = new Player(new PlayerId(playerId), "RandomPlayer", Instant.now(), null, null, true, List.of());
        LobbyPlayerJpaEntity playerEntity = new LobbyPlayerJpaEntity(player.getPlayerId().id(), player.getName());
        playerEntity.setLastActive(Instant.now());
        lobbyPlayerJpaRepository.save(playerEntity);
    }

    @Test
    void shouldMatchWithRandomPlayerSuccessfully() {
        // Arrange
        MatchPlayersCommand command = new MatchPlayersCommand(playerId, gameId);

        // Act & Assert
        Assertions.assertDoesNotThrow(() -> matchWithRandomPlayerUseCase.matchPlayers(command));
    }

    @Test
    void shouldFailToMatchWithRandomPlayer() {
        // Arrange
        MatchPlayersCommand command = new MatchPlayersCommand(null, UUID.randomUUID());

        // Act & Assert
        Assertions.assertThrows(InvalidDataAccessApiUsageException.class, () -> matchWithRandomPlayerUseCase.matchPlayers(command));
    }
}