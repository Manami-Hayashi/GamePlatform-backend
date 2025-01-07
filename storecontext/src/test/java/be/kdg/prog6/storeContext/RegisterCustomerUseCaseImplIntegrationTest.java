package be.kdg.prog6.storeContext;

import be.kdg.prog6.storeContext.adapters.out.db.StoreGameJpaEntity;
import be.kdg.prog6.storeContext.adapters.out.db.StoreGameJpaRepository;
import be.kdg.prog6.storeContext.domain.GameId;
import be.kdg.prog6.storeContext.domain.StoreGame;
import be.kdg.prog6.storeContext.port.in.RegisterCustomerUseCase;
import be.kdg.prog6.storeContext.port.in.RegisterUserCommand;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
class RegisterCustomerUseCaseImplIntegrationTest extends AbstractDatabaseTest {

    @Autowired
    private RegisterCustomerUseCase registerCustomerUseCase;

    @Autowired
    private StoreGameJpaRepository storeGameJpaRepository;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @MockBean
    private AmqpAdmin amqpAdmin;


    @Test
    void shouldRegisterCustomerSuccessfully() {
        // Arrange
        StoreGame storeGame = new StoreGame(new GameId(TestIds.GAME_ID), "Checkers", BigDecimal.valueOf(2), "A game of strategy", List.of());
        RegisterUserCommand registerUserCommand = new RegisterUserCommand(TestIds.PLAYER_ID, storeGame.getGameName());

        // Act & Assert
        storeGameJpaRepository.save(toStoreGameJpaEntity(storeGame));
        assertDoesNotThrow(() -> registerCustomerUseCase.registerCustomer(registerUserCommand));

        // Cleanup
        storeGameJpaRepository.deleteAll();
    }

    @Test
    void shouldFailToRegisterCustomer() {
        // Arrange
        StoreGame storeGame = new StoreGame(new GameId(TestIds.GAME_ID), "", BigDecimal.valueOf(2), "A game of strategy", List.of());
        RegisterUserCommand registerUserCommand = new RegisterUserCommand(TestIds.PLAYER_ID, storeGame.getGameName());

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            registerCustomerUseCase.registerCustomer(registerUserCommand);
        });

        // Cleanup
        storeGameJpaRepository.deleteAll();
    }

    private StoreGameJpaEntity toStoreGameJpaEntity(StoreGame storeGame) {
        return new StoreGameJpaEntity(storeGame.getGameId().id(), storeGame.getGameName(), storeGame.getPrice(), storeGame.getDescription());
    }
}
