package be.kdg.prog6.playerManagementContext;

import be.kdg.prog6.playerManagementContext.adapters.out.db.PlayerJpaEntity;
import be.kdg.prog6.playerManagementContext.domain.PlayerId;
import be.kdg.prog6.playerManagementContext.ports.in.AcceptFriendRequestUseCase;
import be.kdg.prog6.playerManagementContext.adapters.out.db.PlayerJpaRepository;
import be.kdg.prog6.playerManagementContext.ports.in.SendFriendRequestUseCase;
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
    private SendFriendRequestUseCase sendFriendRequestUseCase;

    @Autowired
    private AcceptFriendRequestUseCase acceptFriendRequestUseCase;

    @Autowired
    private PlayerJpaRepository playerRepository;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @MockBean
    private AmqpAdmin amqpAdmin;

    @Test
    void shouldAcceptFriendRequestSuccessfully() {
        // Arrange
        playerRepository.save(new PlayerJpaEntity(TestIds.PLAYER_ID, "Noah"));
        playerRepository.save(new PlayerJpaEntity(TestIds.PLAYER2_ID, "Manami"));

        // Act & Assert
        sendFriendRequestUseCase.sendFriendRequest(new PlayerId(TestIds.PLAYER_ID), new PlayerId(TestIds.PLAYER2_ID));
        assertDoesNotThrow(() -> acceptFriendRequestUseCase.acceptFriendRequest(new PlayerId(TestIds.PLAYER_ID), new PlayerId(TestIds.PLAYER2_ID)), "Expected no exception to be thrown for accepting a valid friend request");

        // Cleanup
        playerRepository.deleteAll();
    }

    @Test
    void shouldFailToAcceptOwnFriendRequest() {
        // Arrange
        playerRepository.save(new PlayerJpaEntity(TestIds.PLAYER_ID, "Noah"));

        // Act & Assert
        assertThrows(RuntimeException.class, () -> acceptFriendRequestUseCase.acceptFriendRequest(new PlayerId(TestIds.PLAYER_ID), new PlayerId(TestIds.PLAYER2_ID)), "Expected an IllegalArgumentException to be thrown for accepting own friend request");

        // Cleanup
        playerRepository.deleteAll();
    }
}
