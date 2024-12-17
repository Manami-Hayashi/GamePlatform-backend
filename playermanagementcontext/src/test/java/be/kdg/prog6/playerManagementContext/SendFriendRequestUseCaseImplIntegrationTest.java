package be.kdg.prog6.playerManagementContext;

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
    private PlayerJpaRepository playerJpaRepository;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @MockBean
    private AmqpAdmin amqpAdmin;


    @Test
    void shouldSendFriendRequestSuccessfully() {
        // Arrange
        PlayerId senderId = new PlayerId(TestIds.SENDER_ID);
        PlayerId receiverId = new PlayerId(TestIds.RECEIVER_ID);

        // Act & Assert
        assertDoesNotThrow(() -> sendFriendRequestUseCase.sendFriendRequest(senderId, receiverId), "Expected no exception to be thrown for sending a valid friend request");
    }

    @Test
    void shouldFailToSendFriendRequestToSelf() {
        // Arrange
        PlayerId playerId = new PlayerId(TestIds.SENDER_ID);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> sendFriendRequestUseCase.sendFriendRequest(playerId, playerId), "Expected an IllegalArgumentException to be thrown for sending friend request to self");
    }
}