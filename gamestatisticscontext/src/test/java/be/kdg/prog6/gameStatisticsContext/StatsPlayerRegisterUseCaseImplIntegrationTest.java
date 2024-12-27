package be.kdg.prog6.gameStatisticsContext;

import be.kdg.prog6.gameStatisticsContext.adapter.out.PlayerRepository;
import be.kdg.prog6.gameStatisticsContext.adapter.out.StatsPlayerJpaEntity;
import be.kdg.prog6.gameStatisticsContext.port.in.RegisterPlayerUseCase;
import be.kdg.prog6.gameStatisticsContext.port.in.RegisterUserCommand;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class StatsPlayerRegisterUseCaseImplIntegrationTest extends AbstractDatabaseTest {

    @Autowired
    private RegisterPlayerUseCase registerPlayerUseCase;

    @Autowired
    private PlayerRepository playerRepository;

    @Test
    void shouldStatsPlayerRegisterSuccessfully() {
        // Arrange
        RegisterUserCommand command = new RegisterUserCommand(TestIds.PLAYER_ID, "William");

        // Act & Assert
        assertDoesNotThrow(() -> registerPlayerUseCase.registerPlayer(command), "Expected no exception to be thrown for registering a player");

        // Cleanup
        playerRepository.deleteAll();
    }

    @Test
    void shouldFailToStatsPlayerRegisterToSelf() {
        // Arrange
        playerRepository.save(new StatsPlayerJpaEntity(TestIds.PLAYER_ID, "Noah", "1990-01-01", "MALE", "Antwerp"));
        RegisterUserCommand command = new RegisterUserCommand(TestIds.PLAYER_ID, "Noah");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> registerPlayerUseCase.registerPlayer(command), "Expected an exception to be thrown for registering a player twice");

        // Cleanup
        playerRepository.deleteAll();
    }
}
