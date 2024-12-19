package be.kdg.prog6.playerManagementContext;

import be.kdg.prog6.playerManagementContext.domain.PlayerId;
import be.kdg.prog6.playerManagementContext.ports.in.DisplayOwnedGameUseCase;
import be.kdg.prog6.playerManagementContext.adapters.out.db.GameOwnedJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
class DisplayOwnedGameUseCaseImplIntegrationTest extends AbstractDatabaseTest {

    @Autowired
    private DisplayOwnedGameUseCase displayOwnedGameUseCase;

    @Autowired
    private GameOwnedJpaRepository gameOwnedJpaRepository;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @MockBean
    private AmqpAdmin amqpAdmin;

    @Test
    void shouldDisplayOwnedGamesSuccessfully() {
        // Arrange
        PlayerId playerId = new PlayerId(TestIds.PLAYER_ID);

        // Act
        var games = displayOwnedGameUseCase.displayOwnedGames(playerId);

        // Assert
        assertNotNull(games, "Expected the list of owned games to be not null");
        // Add more assertions to verify the owned games
    }
}