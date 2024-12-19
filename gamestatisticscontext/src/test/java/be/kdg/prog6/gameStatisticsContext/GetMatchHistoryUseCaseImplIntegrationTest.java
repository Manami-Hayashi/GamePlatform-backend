package be.kdg.prog6.gameStatisticsContext;

import be.kdg.prog6.gameStatisticsContext.adapter.out.*;
import be.kdg.prog6.gameStatisticsContext.port.in.GetMatchHistoryUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class GetMatchHistoryUseCaseImplIntegrationTest extends AbstractDatabaseTest {

    @Autowired
    private GetMatchHistoryUseCase getMatchHistoryUseCase;

    @Autowired
    private MatchSessionRepository matchSessionRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private GameStatisticsRepository gameStatisticsRepository;

    @Test
    void shouldGetMatchHistorySuccessfully() {
        // Arrange
        playerRepository.save(new StatsPlayerJpaEntity(TestIds.PLAYER_ID, "Noah", "1990-01-01", "MALE", "Antwerp"));
        playerRepository.save(new StatsPlayerJpaEntity(TestIds.PLAYER2_ID, "Manami", "1995-01-01", "MALE", "Antwerp"));
        gameStatisticsRepository.save(new GameStatisticsJpaEntity(TestIds.PLAYER_ID, TestIds.GAME_ID, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
        gameStatisticsRepository.save(new GameStatisticsJpaEntity(TestIds.PLAYER2_ID, TestIds.GAME_ID, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
        GameStatisticsJpaEntity gameStatisticsJpaEntity1 = gameStatisticsRepository.findByPlayerIdAndGameId(TestIds.PLAYER_ID, TestIds.GAME_ID).orElse(null);
        GameStatisticsJpaEntity gameStatisticsJpaEntity2 = gameStatisticsRepository.findByPlayerIdAndGameId(TestIds.PLAYER2_ID, TestIds.GAME_ID).orElse(null);
        assert gameStatisticsJpaEntity1 != null;
        assert gameStatisticsJpaEntity2 != null;
        matchSessionRepository.save(new MatchSessionJpaEntity(UUID.randomUUID(), TestIds.GAME_ID, List.of(gameStatisticsJpaEntity1, gameStatisticsJpaEntity2), TestIds.START_TIME, TestIds.END_TIME, false, TestIds.WINNER.toString(), TestIds.SCORE_P1, TestIds.SCORE_P2, TestIds.MOVES_MADE_P1, TestIds.MOVES_MADE_P2));

        // Act & Assert
        assertDoesNotThrow(() -> getMatchHistoryUseCase.getMatchHistory(TestIds.PLAYER_ID));
        assertEquals(1, getMatchHistoryUseCase.getMatchHistory(TestIds.PLAYER_ID).size());

        // Cleanup
        matchSessionRepository.deleteAll();
        gameStatisticsRepository.deleteAll();
        playerRepository.deleteAll();
    }

    @Test
    void shouldFailToGetMatchHistoryToSelf() {
        // Arrange
        playerRepository.save(new StatsPlayerJpaEntity(TestIds.PLAYER_ID, "Noah", "1990-01-01", "MALE", "Antwerp"));
        playerRepository.save(new StatsPlayerJpaEntity(TestIds.PLAYER2_ID, "Manami", "1995-01-01", "MALE", "Antwerp"));
        gameStatisticsRepository.save(new GameStatisticsJpaEntity(TestIds.PLAYER_ID, TestIds.GAME_ID, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
        gameStatisticsRepository.save(new GameStatisticsJpaEntity(TestIds.PLAYER2_ID, TestIds.GAME_ID, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0));
        GameStatisticsJpaEntity gameStatisticsJpaEntity1 = gameStatisticsRepository.findByPlayerIdAndGameId(TestIds.PLAYER_ID, TestIds.GAME_ID).orElse(null);
        GameStatisticsJpaEntity gameStatisticsJpaEntity2 = gameStatisticsRepository.findByPlayerIdAndGameId(TestIds.PLAYER2_ID, TestIds.GAME_ID).orElse(null);
        assert gameStatisticsJpaEntity1 != null;
        assert gameStatisticsJpaEntity2 != null;
        matchSessionRepository.save(new MatchSessionJpaEntity(UUID.randomUUID(), TestIds.GAME_ID, List.of(gameStatisticsJpaEntity1, gameStatisticsJpaEntity2), TestIds.START_TIME, TestIds.END_TIME, false, null, TestIds.SCORE_P1, TestIds.SCORE_P2, TestIds.MOVES_MADE_P1, TestIds.MOVES_MADE_P2));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> getMatchHistoryUseCase.getMatchHistory(TestIds.PLAYER_ID));

        // Cleanup
        matchSessionRepository.deleteAll();
        gameStatisticsRepository.deleteAll();
        playerRepository.deleteAll();
    }
}
