package be.kdg.prog6.gameStatisticsContext;

import be.kdg.prog6.gameStatisticsContext.adapter.out.MatchSessionRepository;
import be.kdg.prog6.gameStatisticsContext.domain.PlayerId;
import be.kdg.prog6.gameStatisticsContext.port.in.GetMatchHistoryUseCase;
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
class GetMatchHistoryUseCaseImplIntegrationTestStats extends StatsAbstractDatabaseTest {

    @Autowired
    private GetMatchHistoryUseCase getMatchHistoryUseCase;

    @Autowired
    private MatchSessionRepository matchSessionRepository;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @MockBean
    private AmqpAdmin amqpAdmin;


    @Test
    void shouldGetMatchHistorySuccessfully() {
        // Arrange
        PlayerId playerId = new PlayerId(TestIds.PLAYER_ID);

        // Act & Assert
        assertDoesNotThrow(() -> getMatchHistoryUseCase.getMatchHistory(playerId.id()), "Expected no exception to be thrown for sending a valid friend request");
    }

    @Test
    void shouldFailToGetMatchHistoryToSelf() {
        // Arrange
        PlayerId playerId = new PlayerId(TestIds.PLAYER_ID);

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> getMatchHistoryUseCase.getMatchHistory(playerId.id()), "Expected an IllegalArgumentException to be thrown for sending friend request to self");
    }
}
