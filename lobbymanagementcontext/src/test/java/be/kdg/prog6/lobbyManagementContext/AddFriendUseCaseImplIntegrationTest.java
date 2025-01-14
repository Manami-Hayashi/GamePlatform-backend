package be.kdg.prog6.lobbyManagementContext;

import be.kdg.prog6.lobbyManagementContext.adapters.out.db.LobbyPlayerJpaEntity;
import be.kdg.prog6.lobbyManagementContext.adapters.out.db.LobbyPlayerJpaRepository;
import be.kdg.prog6.lobbyManagementContext.ports.in.AddFriendUseCase;
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
class AddFriendUseCaseImplIntegrationTest extends AbstractDatabaseTest {

    @Autowired
    private AddFriendUseCase addFriendUseCase;

    @Autowired
    private LobbyPlayerJpaRepository lobbyPlayerJpaRepository;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @MockBean
    private AmqpAdmin amqpAdmin;

    @Test
    void shouldAddFriendSuccessfully() {
        // Arrange
        lobbyPlayerJpaRepository.save(new LobbyPlayerJpaEntity(TestIds.PLAYER_ID, "Noah"));
        lobbyPlayerJpaRepository.save(new LobbyPlayerJpaEntity(TestIds.PLAYER2_ID, "Manami"));

        // Act & Assert
        assertDoesNotThrow(() -> addFriendUseCase.addFriend(TestIds.PLAYER_ID, TestIds.PLAYER2_ID));

        // Cleanup
        lobbyPlayerJpaRepository.deleteAll();
    }

    @Test
    void shouldFailToAddOwnFriend() {
        // Arrange

        // Act & Assert
        assertThrows(NullPointerException.class, () -> addFriendUseCase.addFriend(TestIds.PLAYER_ID, TestIds.PLAYER_ID));

    }
}
