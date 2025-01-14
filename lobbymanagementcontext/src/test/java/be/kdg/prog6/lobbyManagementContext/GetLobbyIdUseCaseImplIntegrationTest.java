package be.kdg.prog6.lobbyManagementContext;

import be.kdg.prog6.lobbyManagementContext.adapters.out.db.LobbyJpaEntity;
import be.kdg.prog6.lobbyManagementContext.adapters.out.db.LobbyJpaRepository;
import be.kdg.prog6.lobbyManagementContext.ports.in.GetLobbyIdUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ActiveProfiles;

import java.time.Instant;
import java.util.UUID;

@SpringBootTest
@ActiveProfiles("test")
class GetLobbyIdUseCaseImplIntegrationTest extends AbstractDatabaseTest {

    @Autowired
    private GetLobbyIdUseCase getLobbyIdUseCase;

    @Autowired
    private LobbyJpaRepository lobbyJpaRepository;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @MockBean
    private AmqpAdmin amqpAdmin;

    @BeforeEach
    void setUp() {
        // Create a lobby in the database
        UUID lobbyId = UUID.randomUUID();
        LobbyJpaEntity lobby = new LobbyJpaEntity();
        lobby.setLobbyId(lobbyId);
        lobby.setCreationTime(Instant.now()); // Set the creation time
        lobbyJpaRepository.save(lobby);
    }

    @Test
    void shouldGetLobbyIdSuccessfully() {
        // Arrange
        UUID playerId = UUID.randomUUID();

        // Act & Assert
        Assertions.assertDoesNotThrow(() -> getLobbyIdUseCase.getLobbyId(playerId));
    }

    @Test
    void shouldFailToGetLobbyId() {
        // Arrange
        UUID playerId = null;

        // Act & Assert
        Assertions.assertThrows(InvalidDataAccessApiUsageException.class, () -> getLobbyIdUseCase.getLobbyId(playerId));
    }
}