/*package be.kdg.prog6.storeContext;

import be.kdg.prog6.storeContext.adapters.out.db.StoreGameJpaEntity;
import be.kdg.prog6.storeContext.adapters.out.db.StoreGameJpaRepository;
import be.kdg.prog6.storeContext.domain.CustomerId;
import be.kdg.prog6.storeContext.domain.GameId;
import be.kdg.prog6.storeContext.domain.Review;
import be.kdg.prog6.storeContext.domain.StoreGame;
import be.kdg.prog6.storeContext.port.in.AddStoreGameUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
class AddStoreGameUseCaseImplIntegrationTest extends AbstractDatabaseTest {

    @Autowired
    private AddStoreGameUseCase addStoreGameUseCase;

    @Autowired
    private StoreGameJpaRepository storeGameJpaRepository;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @MockBean
    private AmqpAdmin amqpAdmin;


    @Test
    void shouldAddStoreGameSuccessfully() {
        // Arrange
        List<Review> reviews = List.of(new Review(UUID.randomUUID(), new CustomerId(UUID.randomUUID()), new GameId(TestIds.GAME_ID), 5, "Great game", LocalDateTime.now()));
        StoreGame storeGame = new StoreGame(new GameId(TestIds.GAME_ID), "Checkers", BigDecimal.valueOf(2), "A game of strategy", reviews);

        // Act
        addStoreGameUseCase.addStoreGame(storeGame);

        // Assert
        Optional<StoreGameJpaEntity> savedStoreGameJpaEntity = storeGameJpaRepository.findById(TestIds.GAME_ID);
        assertTrue(savedStoreGameJpaEntity.isPresent(), "Expected the store game to be present in the repository");
    }

    @Test
    void shouldFailToAddStoreGame() {
        // Arrange
        List<Review> reviews = List.of(new Review(UUID.randomUUID(), new CustomerId(UUID.randomUUID()), new GameId(TestIds.GAME_ID), 5, "Great game", LocalDateTime.now()));
        StoreGame storeGame = new StoreGame(new GameId(TestIds.GAME_ID), "Checkers", BigDecimal.valueOf(2), "A game of strategy", reviews);

        // Act & Assert
        addStoreGameUseCase.addStoreGame(storeGame);
        assertThrows(IllegalArgumentException.class, () -> {
            addStoreGameUseCase.addStoreGame(storeGame);
        });
    }

}

 */
