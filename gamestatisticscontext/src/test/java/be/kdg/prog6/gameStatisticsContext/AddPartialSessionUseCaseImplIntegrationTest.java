package be.kdg.prog6.gameStatisticsContext;

import be.kdg.prog6.gameStatisticsContext.adapter.out.*;
import be.kdg.prog6.gameStatisticsContext.domain.GameId;
import be.kdg.prog6.gameStatisticsContext.domain.GameStatistics;
import be.kdg.prog6.gameStatisticsContext.domain.PlayerId;
import be.kdg.prog6.gameStatisticsContext.port.in.AddPartialSessionCommand;
import be.kdg.prog6.gameStatisticsContext.port.in.AddPartialSessionUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
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
    @Autowired
    private MatchSessionRepository matchSessionRepository;


    @Test
    void shouldAddPartialSessionSuccessfully() {
        // Arrange
        PlayerId playerId = new PlayerId(TestIds.PLAYER_ID);
        PlayerId player2Id = new PlayerId(TestIds.PLAYER2_ID);
        playerRepository.save(new StatsPlayerJpaEntity(playerId.id(), "Noah", "1990-01-01", "MALE", "Antwerp"));
        playerRepository.save(new StatsPlayerJpaEntity(player2Id.id(), "Manami", "1995-01-01", "MALE", "Antwerp"));
        gameStatisticsRepository.save(new GameStatisticsJpaEntity(playerId.id(), TestIds.GAME_ID, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
        gameStatisticsRepository.save(new GameStatisticsJpaEntity(player2Id.id(), TestIds.GAME_ID, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
        AddPartialSessionCommand command = new AddPartialSessionCommand(UUID.randomUUID(), TestIds.GAME_ID, TestIds.START_TIME, true, TestIds.PLAYER_ID, TestIds.PLAYER2_ID);
        GameStatistics gameStatistics = gameStatisticsRepository.findByPlayerIdAndGameId(playerId.id(), TestIds.GAME_ID).map(this::toGameStatistics).orElse(null);

        // Act & Assert
        addPartialSessionUseCase.addPartialSession(command);
        GameStatistics newGameStatistics = gameStatisticsRepository.findByPlayerIdAndGameId(TestIds.PLAYER_ID, TestIds.GAME_ID).map(this::toGameStatistics).orElse(null);
        assert newGameStatistics != null;
        assert gameStatistics != null;
        assertDoesNotThrow(() -> addPartialSessionUseCase.addPartialSession(command));

        // Cleanup
        matchSessionRepository.deleteAll();
        gameStatisticsRepository.deleteAll();
        playerRepository.deleteAll();
    }

    @Test
    void shouldFailToAddPartialSessionToSelf() {
        // Arrange
        PlayerId playerId = new PlayerId(TestIds.PLAYER_ID);
        PlayerId player2Id = new PlayerId(TestIds.PLAYER2_ID);
        AddPartialSessionCommand command = new AddPartialSessionCommand(UUID.randomUUID(), TestIds.GAME_ID, TestIds.START_TIME, true, playerId.id(), player2Id.id());

        // Act & Assert
        assertThrows(DataIntegrityViolationException.class, () -> addPartialSessionUseCase.addPartialSession(command), "Fail");

        // Cleanup
        matchSessionRepository.deleteAll();
        gameStatisticsRepository.deleteAll();
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
