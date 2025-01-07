//package be.kdg.prog6.storeContext;
//
//import be.kdg.prog6.storeContext.adapters.out.db.StoreGameJpaEntity;
//import be.kdg.prog6.storeContext.adapters.out.db.StoreGameJpaRepository;
//import be.kdg.prog6.storeContext.domain.GameId;
//import be.kdg.prog6.storeContext.domain.StoreGame;
//import be.kdg.prog6.storeContext.port.in.PurchaseGameCommand;
//import be.kdg.prog6.storeContext.port.in.PurchaseGameUseCase;
//import org.junit.jupiter.api.Test;
//import org.springframework.amqp.core.AmqpAdmin;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.ActiveProfiles;
//
//import java.math.BigDecimal;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//@SpringBootTest
//@ActiveProfiles("test")
//class PurchaseGameUseCaseImplIntegrationTest extends AbstractDatabaseTest {
//
//    @Autowired
//    private PurchaseGameUseCase purchaseGameUseCase;
//
//    @Autowired
//    private StoreGameJpaRepository storeGameJpaRepository;
//
//    @MockBean
//    private RabbitTemplate rabbitTemplate;
//
//    @MockBean
//    private AmqpAdmin amqpAdmin;
//
//    @Test
//    void shouldFailToPurchaseGame() {
//        // Arrange
//        StoreGame storeGame = new StoreGame(new GameId(TestIds.GAME_ID), "Checkers", BigDecimal.valueOf(2), "A game of strategy", List.of());
//        PurchaseGameCommand command = new PurchaseGameCommand(TestIds.GAME_ID, TestIds.PLAYER_ID, "tok_visa");
//
//        // Act & Assert
//        assertThrows(IllegalArgumentException.class, () -> {
//            purchaseGameUseCase.purchaseGame(command);
//        });
//
//        // Cleanup
//        storeGameJpaRepository.deleteAll();
//    }
//
//    private StoreGameJpaEntity toStoreGameJpaEntity(StoreGame storeGame) {
//        return new StoreGameJpaEntity(storeGame.getGameId().id(), storeGame.getGameName(), storeGame.getPrice(), storeGame.getDescription());
//    }
//}
