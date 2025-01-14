package be.kdg.prog6.lobbyManagementContext;

import be.kdg.prog6.lobbyManagementContext.adapters.out.db.LobbyPlayerJpaEntity;
import be.kdg.prog6.lobbyManagementContext.adapters.out.db.LobbyPlayerJpaRepository;
import be.kdg.prog6.lobbyManagementContext.ports.in.GetFriendsUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
class GetFriendsUseCaseImplIntegrationTest extends AbstractDatabaseTest {

    @Autowired
    private GetFriendsUseCase getFriendsUseCase;

    @Autowired
    private LobbyPlayerJpaRepository lobbyPlayerJpaRepository;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @MockBean
    private AmqpAdmin amqpAdmin;

    @BeforeEach
    void setUp() {
        // Create a player in the database
        UUID playerId = TestIds.PLAYER2_ID;
        LobbyPlayerJpaEntity player = new LobbyPlayerJpaEntity();
        player.setPlayerId(playerId);
        player.setName("Test Player");
        lobbyPlayerJpaRepository.save(player);
    }

    @Test
    void shouldGetFriendsSuccessfully() {
        // Arrange
        UUID playerId = TestIds.PLAYER2_ID;

        // Act & Assert
        Assertions.assertDoesNotThrow(() -> getFriendsUseCase.getFriends(playerId));
    }

    @Test
    void shouldFailToGetFriends() {
        // Arrange
        UUID playerId = null;

        // Act & Assert
        Assertions.assertThrows(InvalidDataAccessApiUsageException.class, () -> getFriendsUseCase.getFriends(playerId));
    }
}