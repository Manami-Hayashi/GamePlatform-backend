package be.kdg.prog6.gameStatisticsContext;

import be.kdg.prog6.gameStatisticsContext.adapter.out.GameStatisticsJpaEntity;
import be.kdg.prog6.gameStatisticsContext.adapter.out.GameStatisticsRepository;
import be.kdg.prog6.gameStatisticsContext.adapter.out.MatchSessionRepository;
import be.kdg.prog6.gameStatisticsContext.domain.GameId;
import be.kdg.prog6.gameStatisticsContext.domain.GameStatistics;
import be.kdg.prog6.gameStatisticsContext.domain.PlayerId;
import be.kdg.prog6.gameStatisticsContext.port.in.UpdateGameStatisticsCommand;
import be.kdg.prog6.gameStatisticsContext.port.in.UpdateGameStatisticsUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class UpdateGameStatisticsUseCaseImplIntegrationTest extends AbstractDatabaseTest {

    @Autowired
    private UpdateGameStatisticsUseCase updateGameStatisticsUseCase;

    @Autowired
    private MatchSessionRepository matchSessionRepository;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @MockBean
    private AmqpAdmin amqpAdmin;
    @Autowired
    private GameStatisticsRepository gameStatisticsRepository;


    @Test
    void shouldUpdateGameStatisticsSuccessfully() {
        // Arrange
        UpdateGameStatisticsCommand command = new UpdateGameStatisticsCommand(UUID.randomUUID(), TestIds.GAME_ID, List.of(TestIds.PLAYER_ID, TestIds.PLAYER2_ID), TestIds.START_TIME, TestIds.END_TIME, true, TestIds.WINNER.toString(), TestIds.SCORE_P1, TestIds.SCORE_P2, TestIds.MOVES_MADE_P1, TestIds.MOVES_MADE_P2);
        GameStatistics gameStatistics = gameStatisticsRepository.findByPlayerIdAndGameId(TestIds.PLAYER_ID, TestIds.GAME_ID).map(this::toGameStatistics).orElse(null);

        // Act
        updateGameStatisticsUseCase.updateGameStatistics(command);

        // Assert
        GameStatistics newGameStatistics = gameStatisticsRepository.findByPlayerIdAndGameId(TestIds.PLAYER_ID, TestIds.GAME_ID).map(this::toGameStatistics).orElse(null);
        assert newGameStatistics != null;
        assertEquals(newGameStatistics.getTotalScore(), gameStatistics.getTotalScore() + TestIds.SCORE_P1, "Expected total score to be updated");
    }

    @Test
    void shouldFailToUpdateGameStatisticsToSelf() {
        // Arrange
        PlayerId playerId = new PlayerId(TestIds.PLAYER_ID);
        PlayerId player2Id = new PlayerId(TestIds.PLAYER2_ID);
        UpdateGameStatisticsCommand command = new UpdateGameStatisticsCommand(UUID.randomUUID(), TestIds.GAME_ID, List.of(playerId.id(), player2Id.id()), TestIds.START_TIME, TestIds.END_TIME, true, TestIds.WINNER.toString(), TestIds.SCORE_P1, TestIds.SCORE_P2, TestIds.MOVES_MADE_P1, TestIds.MOVES_MADE_P2);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> updateGameStatisticsUseCase.updateGameStatistics(command), "Fail");
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
