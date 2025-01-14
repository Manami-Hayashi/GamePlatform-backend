package be.kdg.prog6.lobbyManagementContext;

import be.kdg.prog6.lobbyManagementContext.adapters.out.db.LobbyPlayerJpaEntity;
import be.kdg.prog6.lobbyManagementContext.adapters.out.db.LobbyPlayerJpaRepository;
import be.kdg.prog6.lobbyManagementContext.domain.Game;
import be.kdg.prog6.lobbyManagementContext.domain.GameId;
import be.kdg.prog6.lobbyManagementContext.domain.Player;
import be.kdg.prog6.lobbyManagementContext.domain.PlayerId;
import be.kdg.prog6.lobbyManagementContext.ports.in.PurchaseLobbyGameUseCase;
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
class PurchaseLobbyGameUseCaseImplIntegrationTest extends AbstractDatabaseTest {

    @Autowired
    private PurchaseLobbyGameUseCase purchaseLobbyGameUseCase;

    @Autowired
    private LobbyPlayerJpaRepository lobbyPlayerJpaRepository;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @MockBean
    private AmqpAdmin amqpAdmin;

    private PlayerId playerId;

    @BeforeEach
    void setUp() {
        playerId = new PlayerId(UUID.randomUUID());
        LobbyPlayerJpaEntity player = new LobbyPlayerJpaEntity(playerId.id(), "Test Player");
        lobbyPlayerJpaRepository.save(player);
    }

    @Test
    void shouldPurchaseLobbyGameSuccessfully() {
        // Arrange
        Game game = new Game(new GameId(UUID.randomUUID()), "Chess");

        // Act & Assert
        Assertions.assertDoesNotThrow(() -> purchaseLobbyGameUseCase.purchaseLobbyGame(playerId, game));
    }

    @Test
    void shouldFailToPurchaseLobbyGame() {
        // Arrange
        Game game = new Game(null, "Chess");

        // Act & Assert
        Assertions.assertThrows(NullPointerException.class, () -> purchaseLobbyGameUseCase.purchaseLobbyGame(playerId, game));
    }
}