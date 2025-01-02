package be.kdg.prog6.gameManagementContext;

import be.kdg.prog6.gameManagementContext.adapters.out.db.GameJpaEntity;
import be.kdg.prog6.gameManagementContext.adapters.out.db.GameJpaRepository;
import be.kdg.prog6.gameManagementContext.domain.Game;
import be.kdg.prog6.gameManagementContext.domain.GameId;
import be.kdg.prog6.gameManagementContext.ports.in.AddGameCommand;
import be.kdg.prog6.gameManagementContext.ports.in.AddGameUseCase;
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
class AddGameUseCaseImplIntegrationTest extends AbstractDatabaseTest {

    @Autowired
    private AddGameUseCase addGameUseCase;

    @Autowired
    private GameJpaRepository gameJpaRepository;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @MockBean
    private AmqpAdmin amqpAdmin;


    @Test
    void shouldAddGameSuccessfully() {
        // Arrange
        AddGameCommand command = new AddGameCommand(TestIds.GAME_NAME, TestIds.GAME_PRICE, TestIds.GAME_DESCRIPTION);

        // Act
        addGameUseCase.addGame(command);

        // Assert
        Optional<Game> savedGame = gameJpaRepository.findByGameName(TestIds.GAME_NAME).map(this::toGame);
        assertTrue(savedGame.isPresent(), "Expected the game to be present in the repository");
        assertEquals(TestIds.GAME_NAME, savedGame.get().getGameName(), "Expected game name to match");
        assertEquals(TestIds.GAME_PRICE, savedGame.get().getPrice(), "Expected game price to match");
        assertEquals(TestIds.GAME_DESCRIPTION, savedGame.get().getDescription(), "Expected game description to match");

        // Cleanup
        gameJpaRepository.deleteAll();
    }

    @Test
    void shouldFailToAddGameWithNegativePrice() {
        // Arrange
        AddGameCommand command = new AddGameCommand(TestIds.GAME_NAME, new BigDecimal("-1"), TestIds.GAME_DESCRIPTION);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> addGameUseCase.addGame(command), "Expected an IllegalArgumentException to be thrown for negative price");

        // Cleanup
        gameJpaRepository.deleteAll();
    }

    private Game toGame(GameJpaEntity gameJpaEntity) {
        return new Game(
                new GameId(gameJpaEntity.getGameId()),
                gameJpaEntity.getGameName(),
                gameJpaEntity.getPrice(),
                gameJpaEntity.getDescription()
        );
    }
}
