//package be.kdg.prog6.gameStatisticsContext;
//
//import be.kdg.prog6.gameStatisticsContext.adapter.out.*;
//import be.kdg.prog6.gameStatisticsContext.domain.GameId;
//import be.kdg.prog6.gameStatisticsContext.domain.GameStatistics;
//import be.kdg.prog6.gameStatisticsContext.domain.PlayerId;
//import be.kdg.prog6.gameStatisticsContext.port.in.UpdateGameStatisticsCommand;
//import be.kdg.prog6.gameStatisticsContext.port.in.UpdateGameStatisticsUseCase;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//
//import java.util.List;
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@ActiveProfiles("test")
//class UpdateGameStatisticsUseCaseImplIntegrationTest extends AbstractDatabaseTest {
//
//    @Autowired
//    private UpdateGameStatisticsUseCase updateGameStatisticsUseCase;
//
//    @Autowired
//    private GameStatisticsRepository gameStatisticsRepository;
//
//    @Autowired
//    private PlayerRepository playerRepository;
//
//    @Autowired
//    private MatchSessionRepository matchSessionRepository;
//
//
//    @Test
//    void shouldUpdateGameStatisticsSuccessfully() {
//        // Arrange
//        playerRepository.save(new StatsPlayerJpaEntity(TestIds.PLAYER_ID, "Noah", "1990-01-01", "MALE", "Antwerp"));
//        playerRepository.save(new StatsPlayerJpaEntity(TestIds.PLAYER2_ID, "Manami", "1995-01-01", "MALE", "Antwerp"));
//        gameStatisticsRepository.save(new GameStatisticsJpaEntity(TestIds.PLAYER_ID, TestIds.GAME_ID, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
//        gameStatisticsRepository.save(new GameStatisticsJpaEntity(TestIds.PLAYER2_ID, TestIds.GAME_ID, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
//        UpdateGameStatisticsCommand command = new UpdateGameStatisticsCommand(UUID.fromString("fb4f83cb-6e15-4842-9688-39d53c6a4547"), TestIds.GAME_ID, List.of(TestIds.PLAYER_ID, TestIds.PLAYER2_ID), TestIds.START_TIME, TestIds.END_TIME, true, TestIds.WINNER.toString(), TestIds.SCORE_P1, TestIds.SCORE_P2, TestIds.MOVES_MADE_P1, TestIds.MOVES_MADE_P2);
//        GameStatistics gameStatistics = gameStatisticsRepository.findByPlayerIdAndGameId(TestIds.PLAYER_ID, TestIds.GAME_ID).map(this::toGameStatistics).orElse(null);
//
//        // Act
//        updateGameStatisticsUseCase.updateGameStatistics(command);
//
//        // Act & Assert
//        GameStatistics newGameStatistics = gameStatisticsRepository.findByPlayerIdAndGameId(TestIds.PLAYER_ID, TestIds.GAME_ID).map(this::toGameStatistics).orElse(null);
//        assert newGameStatistics != null;
//        assert gameStatistics != null;
//        assertEquals(newGameStatistics.getTotalScore(), gameStatistics.getTotalScore() + TestIds.SCORE_P1, "Expected total score to be updated");
//
//        // Cleanup
//        matchSessionRepository.deleteAll();
//        gameStatisticsRepository.deleteAll();
//        playerRepository.deleteAll();
//    }
//
//    @Test
//    void shouldFailToUpdateGameStatisticsToSelf() {
//        // Arrange
//        PlayerId playerId = new PlayerId(TestIds.PLAYER_ID);
//        PlayerId player2Id = new PlayerId(TestIds.PLAYER2_ID);
//        UpdateGameStatisticsCommand command = new UpdateGameStatisticsCommand(UUID.randomUUID(), TestIds.GAME_ID, List.of(playerId.id(), player2Id.id()), TestIds.START_TIME, TestIds.END_TIME, true, TestIds.WINNER.toString(), TestIds.SCORE_P1, TestIds.SCORE_P2, TestIds.MOVES_MADE_P1, TestIds.MOVES_MADE_P2);
//
//        // Assert
//        assertThrows(IllegalArgumentException.class, () -> updateGameStatisticsUseCase.updateGameStatistics(command), "Fail");
//
//        // Cleanup
//        gameStatisticsRepository.deleteAll();
//        playerRepository.deleteAll();
//    }
//
//    private GameStatistics toGameStatistics(GameStatisticsJpaEntity gameStatsEntity) {
//        return new GameStatistics(
//                new PlayerId(gameStatsEntity.getPlayerId()),
//                new GameId(gameStatsEntity.getGameId()),
//                gameStatsEntity.getTotalScore(),
//                gameStatsEntity.getTotalGamesPlayed(),
//                gameStatsEntity.getWins(),
//                gameStatsEntity.getLosses(),
//                gameStatsEntity.getDraws(),
//                gameStatsEntity.getWinLossRatio(),
//                gameStatsEntity.getTotalTimePlayed(),
//                gameStatsEntity.getHighestScore(),
//                gameStatsEntity.getMovesMade(),
//                gameStatsEntity.getAverageGameDuration()
//        );
//    }
//}
