package be.kdg.prog6.gameStatisticsContext;

import be.kdg.prog6.gameStatisticsContext.adapter.out.AchievementRepository;
import be.kdg.prog6.gameStatisticsContext.adapter.out.GameStatisticsRepository;
import be.kdg.prog6.gameStatisticsContext.adapter.out.MatchSessionRepository;
import be.kdg.prog6.gameStatisticsContext.adapter.out.PlayerRepository;
import be.kdg.prog6.gameStatisticsContext.port.in.DataGenerationUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
class DataGenerationUseCaseImplIntegrationTest extends AbstractDatabaseTest {

    @Autowired
    private DataGenerationUseCase dataGenerationUseCase;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private GameStatisticsRepository gameStatisticsRepository;
    @Autowired
    private MatchSessionRepository matchSessionRepository;

    @Test
    void shouldGeneratePlayersSuccessfully() {
        // Assert
        assertDoesNotThrow(() -> dataGenerationUseCase.generatePlayers(2));

        // Cleanup
        playerRepository.deleteAll();
    }

    @Test
    void shouldFailToGeneratePlayersToSelf() {
        // Assert
        assertThrows(IllegalArgumentException.class, () -> dataGenerationUseCase.generatePlayers(-1));
    }

    @Test
    void shouldGenerateGameStatisticsSuccessfully() {
        // Assert
        assertDoesNotThrow(() -> dataGenerationUseCase.generateGameStatistics(2));

        // Cleanup
        matchSessionRepository.deleteAll();
        gameStatisticsRepository.deleteAll();
        playerRepository.deleteAll();
    }

    @Test
    void shouldFailToGenerateGameStatisticsToSelf() {
        // Assert
        assertThrows(IllegalArgumentException.class, () -> dataGenerationUseCase.generateGameStatistics(-1));
    }
}
