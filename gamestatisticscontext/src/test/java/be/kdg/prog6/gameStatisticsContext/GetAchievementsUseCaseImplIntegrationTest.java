package be.kdg.prog6.gameStatisticsContext;

import be.kdg.prog6.gameStatisticsContext.adapter.out.*;
import be.kdg.prog6.gameStatisticsContext.port.in.GetAchievementsUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class GetAchievementsUseCaseImplIntegrationTest extends AbstractDatabaseTest {

    @Autowired
    private GetAchievementsUseCase getAchievementsUseCase;

    @Autowired
    private AchievementRepository achievementRepository;

    @Test
    void shouldGetAchievementsSuccessfully() {
        // Arrange
        achievementRepository.save(new AchievementJpaEntity(TestIds.PLAYER_ID, TestIds.GAME_ID, "", "Description 1", true));

        // Act & Assert
        assertDoesNotThrow(() -> getAchievementsUseCase.getAchievements(TestIds.PLAYER_ID, TestIds.GAME_ID));

        // Cleanup
        achievementRepository.deleteAll();
    }

    @Test
    void shouldFailToGetAchievementsToSelf() {
        // Assert
        assertThrows(RuntimeException.class, () -> getAchievementsUseCase.getAchievements(TestIds.PLAYER_ID, TestIds.GAME_ID));
    }
}
