package be.kdg.prog6.gameStatisticsContext;

import be.kdg.prog6.gameStatisticsContext.adapter.out.GameStatisticsJpaEntity;
import be.kdg.prog6.gameStatisticsContext.adapter.out.GameStatisticsRepository;
import be.kdg.prog6.gameStatisticsContext.adapter.out.PlayerRepository;
import be.kdg.prog6.gameStatisticsContext.adapter.out.StatsPlayerJpaEntity;
import be.kdg.prog6.gameStatisticsContext.domain.GameId;
import be.kdg.prog6.gameStatisticsContext.domain.GameStatistics;
import be.kdg.prog6.gameStatisticsContext.domain.PlayerId;
import be.kdg.prog6.gameStatisticsContext.port.in.AddPartialSessionCommand;
import be.kdg.prog6.gameStatisticsContext.port.in.AddPartialSessionUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class AddPartialSessionUseCaseImplIntegrationTest extends AbstractDatabaseTest {

    @Autowired
    private AddPartialSessionUseCase addPartialSessionUseCase;

    @Autowired
    private GameStatisticsRepository gameStatisticsRepository;

    @Autowired
    private PlayerRepository playerRepository;


    @Test
    void shouldAddPartialSessionSuccessfully() {
        // Arrange
        playerRepository.save(new StatsPlayerJpaEntity(TestIds.PLAYER_ID, "Noah", "1990-01-01", "MALE", "Antwerp"));
        playerRepository.save(new StatsPlayerJpaEntity(TestIds.PLAYER2_ID, "Manami", "1995-01-01", "MALE", "Antwerp"));
        gameStatisticsRepository.save(new GameStatisticsJpaEntity(TestIds.PLAYER_ID, TestIds.GAME_ID, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
        gameStatisticsRepository.save(new GameStatisticsJpaEntity(TestIds.PLAYER2_ID, TestIds.GAME_ID, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
        AddPartialSessionCommand command = new AddPartialSessionCommand(UUID.randomUUID(), TestIds.GAME_ID, TestIds.START_TIME, true, TestIds.PLAYER_ID, TestIds.PLAYER2_ID);
        GameStatistics gameStatistics = gameStatisticsRepository.findByPlayerIdAndGameId(TestIds.PLAYER_ID, TestIds.GAME_ID).map(this::toGameStatistics).orElse(null);

        // Act
        addPartialSessionUseCase.addPartialSession(command);

        // Assert
        GameStatistics newGameStatistics = gameStatisticsRepository.findByPlayerIdAndGameId(TestIds.PLAYER_ID, TestIds.GAME_ID).map(this::toGameStatistics).orElse(null);
        assert newGameStatistics != null;
        assert gameStatistics != null;
        assertDoesNotThrow(() -> addPartialSessionUseCase.addPartialSession(command));
    }

    @Test
    void shouldFailToAddPartialSessionToSelf() {
        // Arrange
        PlayerId playerId = new PlayerId(TestIds.PLAYER_ID);
        PlayerId player2Id = new PlayerId(TestIds.PLAYER2_ID);
        AddPartialSessionCommand command = new AddPartialSessionCommand(UUID.randomUUID(), TestIds.GAME_ID, TestIds.START_TIME, true, playerId.id(), player2Id.id());

        // Assert
        assertThrows(IllegalArgumentException.class, () -> addPartialSessionUseCase.addPartialSession(command), "Fail");
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
