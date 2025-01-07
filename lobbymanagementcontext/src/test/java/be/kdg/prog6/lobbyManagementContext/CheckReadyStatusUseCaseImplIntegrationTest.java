package be.kdg.prog6.lobbyManagementContext;

import be.kdg.prog6.lobbyManagementContext.ports.in.CheckReadyStatusUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

@SpringBootTest
@ActiveProfiles("test")
class CheckReadyStatusUseCaseImplIntegrationTest extends AbstractDatabaseTest {

    @Autowired
    private CheckReadyStatusUseCase checkReadyStatusUseCase;


    @MockBean
    private RabbitTemplate rabbitTemplate;

    @MockBean
    private AmqpAdmin amqpAdmin;

    @Test
    void shouldCheckReadyStatusSuccessfully() {
        // Arrange
        UUID playerId = TestIds.PLAYER_ID;

        // Act & Assert
        Assertions.assertDoesNotThrow(() -> checkReadyStatusUseCase.checkReadyStatus(playerId));
    }

    @Test
    void shouldFailToCheckReadyStatus() {
        // Arrange
        UUID playerId = null;

        // Act & Assert
        Assertions.assertThrows(InvalidDataAccessApiUsageException.class, () -> checkReadyStatusUseCase.checkReadyStatus(playerId));
    }
}