package be.kdg.prog6.playerManagementContext;

import be.kdg.prog6.playerManagementContext.domain.PlayerId;
import be.kdg.prog6.playerManagementContext.ports.in.UpdateProfileCommand;
import be.kdg.prog6.playerManagementContext.ports.in.UpdateProfileUseCase;
import be.kdg.prog6.playerManagementContext.adapters.out.db.ProfileJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
class UpdateProfileUseCaseImplIntegrationTest extends AbstractDatabaseTest {

    @Autowired
    private UpdateProfileUseCase updateProfileUseCase;

    @Autowired
    private ProfileJpaRepository profileJpaRepository;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @MockBean
    private AmqpAdmin amqpAdmin;

    @Test
    void shouldUpdateProfileSuccessfully() {
        // Arrange
        PlayerId playerId = new PlayerId(TestIds.PLAYER_ID);
        UpdateProfileCommand command = new UpdateProfileCommand(playerId, "New Bio", "New Avatar", "New Location", LocalDate.now());

        // Act & Assert
        assertDoesNotThrow(() -> updateProfileUseCase.updateProfile(command), "Expected no exception to be thrown for updating profile with valid data");
    }

    @Test
    void shouldFailToUpdateProfileWithInvalidData() {
        // Arrange
        PlayerId playerId = new PlayerId(TestIds.PLAYER_ID);
        UpdateProfileCommand command = new UpdateProfileCommand(playerId, "", "", "", null);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> updateProfileUseCase.updateProfile(command), "Expected an IllegalArgumentException to be thrown for invalid profile data");
    }
}