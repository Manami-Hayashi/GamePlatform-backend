package be.kdg.prog6.playerManagementContext;

import be.kdg.prog6.playerManagementContext.domain.PlayerId;
import be.kdg.prog6.playerManagementContext.ports.in.AcceptFriendRequestUseCase;
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
class AcceptFriendRequestUseCaseImplIntegrationTest extends AbstractDatabaseTest {

    @Autowired
    private AcceptFriendRequestUseCase acceptFriendRequestUseCase;

    @Autowired
    private PlayerJpaRepository playerJpaRepository;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @MockBean
    private AmqpAdmin amqpAdmin;

    @Test
    void shouldAcceptFriendRequestSuccessfully() {
        // Arrange
        PlayerId requesterId = new PlayerId(TestIds.SENDER_ID);
        PlayerId receiverId = new PlayerId(TestIds.RECEIVER_ID);

        // Act & Assert
        assertDoesNotThrow(() -> acceptFriendRequestUseCase.acceptFriendRequest(requesterId, receiverId), "Expected no exception to be thrown for accepting a valid friend request");
    }

    @Test
    void shouldFailToAcceptOwnFriendRequest() {
        // Arrange
        PlayerId playerId = new PlayerId(TestIds.REQUESTER_ID);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> acceptFriendRequestUseCase.acceptFriendRequest(playerId, playerId), "Expected an IllegalArgumentException to be thrown for accepting own friend request");
    }
}