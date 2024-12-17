package be.kdg.prog6.playerManagementContext;

import be.kdg.prog6.playerManagementContext.domain.GameId;
import be.kdg.prog6.playerManagementContext.domain.PlayerId;
import be.kdg.prog6.playerManagementContext.ports.in.FavoriteGameUseCase;
import be.kdg.prog6.playerManagementContext.adapters.out.db.GameOwnedJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
class FavoriteGameUseCaseImplIntegrationTest extends AbstractDatabaseTest {

    @Autowired
    private FavoriteGameUseCase favoriteGameUseCase;

    @Autowired
    private GameOwnedJpaRepository gameOwnedJpaRepository;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @MockBean
    private AmqpAdmin amqpAdmin;

    @Test
    void shouldToggleFavoriteGameSuccessfully() {
        // Arrange
        PlayerId playerId = new PlayerId(TestIds.PLAYER_ID);
        GameId gameId = new GameId(TestIds.GAME_ID);

        // Act & Assert
        assertDoesNotThrow(() -> favoriteGameUseCase.toggleFavoriteGame(playerId, gameId), "Expected no exception to be thrown for toggling a favorite game owned by the player");
    }

    @Test
    void shouldFailToToggleFavoriteGameNotOwned() {
        // Arrange
        PlayerId playerId = new PlayerId(TestIds.PLAYER_ID);
        GameId gameId = new GameId(TestIds.GAME_ID);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> favoriteGameUseCase.toggleFavoriteGame(playerId, gameId), "Expected an IllegalArgumentException to be thrown for toggling a game not owned by the player");
    }
}