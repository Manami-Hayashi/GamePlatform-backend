package be.kdg.prog6.playerManagementContext;

import be.kdg.prog6.playerManagementContext.adapters.out.db.PlayerJpaEntity;
import be.kdg.prog6.playerManagementContext.domain.PlayerId;
import be.kdg.prog6.playerManagementContext.ports.in.SendFriendRequestUseCase;
import be.kdg.prog6.playerManagementContext.adapters.out.db.PlayerJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
class SendFriendRequestUseCaseImplIntegrationTest extends AbstractDatabaseTest {

    @Autowired
    private SendFriendRequestUseCase sendFriendRequestUseCase;

    @Autowired
    private PlayerJpaRepository playerRepository;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @MockBean
    private AmqpAdmin amqpAdmin;

    @Test
    void shouldSendFriendRequestSuccessfully() {
        // Arrange
        playerRepository.save(new PlayerJpaEntity(TestIds.PLAYER_ID, "Noah"));
        playerRepository.save(new PlayerJpaEntity(TestIds.PLAYER2_ID, "Manami"));

        // Act & Assert
        assertDoesNotThrow(() -> sendFriendRequestUseCase.sendFriendRequest(new PlayerId(TestIds.PLAYER_ID), new PlayerId(TestIds.PLAYER2_ID)), "Expected no exception to be thrown for sending a valid friend request");

        // Cleanup
        playerRepository.deleteAll();
    }

    @Test
    void shouldFailToSendFriendRequestToSelf() {
        // Arrange
        playerRepository.save(new PlayerJpaEntity(TestIds.PLAYER_ID, "Noah"));

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> sendFriendRequestUseCase.sendFriendRequest(new PlayerId(TestIds.PLAYER_ID), new PlayerId(TestIds.PLAYER_ID)), "Expected an IllegalArgumentException to be thrown for sending friend request to self");

        // Cleanup
        playerRepository.deleteAll();
    }
}
