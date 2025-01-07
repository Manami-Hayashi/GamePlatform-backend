package be.kdg.prog6.gameStatisticsContext;

import be.kdg.prog6.gameStatisticsContext.adapter.out.*;
import be.kdg.prog6.gameStatisticsContext.domain.GameId;
import be.kdg.prog6.gameStatisticsContext.domain.GameStatistics;
import be.kdg.prog6.gameStatisticsContext.domain.PlayerId;
import be.kdg.prog6.gameStatisticsContext.port.in.GetAdminStatisticsUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class GetAdminStatisticsUseCaseImplIntegrationTest extends AbstractDatabaseTest {

    @Autowired
    private GetAdminStatisticsUseCase getAdminStatisticsUseCase;

    @Autowired
    private MatchSessionRepository matchSessionRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private GameStatisticsRepository gameStatisticsRepository;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @MockBean
    private AmqpAdmin amqpAdmin;

    @Test
    void shouldGetAdminStatisticsSuccessfully() {
        // Arrange
        playerRepository.save(new StatsPlayerJpaEntity(TestIds.PLAYER_ID, "Noah", "1990-01-01", "MALE", "Antwerp"));
        playerRepository.save(new StatsPlayerJpaEntity(TestIds.PLAYER2_ID, "Manami", "1995-01-01", "MALE", "Antwerp"));
        gameStatisticsRepository.save(new GameStatisticsJpaEntity(TestIds.PLAYER_ID, TestIds.GAME_ID, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
        gameStatisticsRepository.save(new GameStatisticsJpaEntity(TestIds.PLAYER2_ID, TestIds.GAME_ID, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));

        // Act & Assert
        assertDoesNotThrow(() -> getAdminStatisticsUseCase.getAdminStatistics());
        assertEquals(1, getAdminStatisticsUseCase.getAdminStatistics().size());

        // Cleanup
        gameStatisticsRepository.deleteAll();
        playerRepository.deleteAll();
    }

    @Test
    void shouldFailToGetAdminStatisticsToSelf() {
        // Arrange
        playerRepository.save(new StatsPlayerJpaEntity(TestIds.PLAYER_ID, "Noah", "1990-01-01", "MALE", "Antwerp"));
        playerRepository.save(new StatsPlayerJpaEntity(TestIds.PLAYER2_ID, "Manami", "1995-01-01", "MALE", "Antwerp"));

        // Act & Assert
        assertThrows(NullPointerException.class, () -> getAdminStatisticsUseCase.getAdminStatistics());

        // Cleanup
        playerRepository.deleteAll();
    }

    private GameStatistics toGameStatistics(GameStatisticsJpaEntity gameStatsEntity) {
        return new GameStatistics(
                new PlayerId(gameStatsEntity.getPlayerId()),
                new GameId(gameStatsEntity.getGameId()),
                gameStatsEntity.getTotalScore(),
                gameStatsEntity.getTotalGamesPlayed(),
                gameStatsEntity.getWins(),
                gameStatsEntity.getLosses(),
                gameStatsEntity.getDraws(),
                gameStatsEntity.getWinLossRatio(),
                gameStatsEntity.getTotalTimePlayed(),
                gameStatsEntity.getHighestScore(),
                gameStatsEntity.getMovesMade(),
                gameStatsEntity.getAverageGameDuration()
        );
    }
}
