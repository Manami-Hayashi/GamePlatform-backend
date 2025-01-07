//package be.kdg.prog6.storeContext;
//
//import be.kdg.prog6.storeContext.adapters.out.db.ReviewJpaEntity;
//import be.kdg.prog6.storeContext.adapters.out.db.StoreGameJpaEntity;
//import be.kdg.prog6.storeContext.adapters.out.db.StoreGameJpaRepository;
//import be.kdg.prog6.storeContext.domain.CustomerId;
//import be.kdg.prog6.storeContext.domain.GameId;
//import be.kdg.prog6.storeContext.domain.Review;
//import be.kdg.prog6.storeContext.domain.StoreGame;
//import be.kdg.prog6.storeContext.port.in.DisplayGameCatalogUseCase;
//import org.junit.jupiter.api.Test;
//import org.springframework.amqp.core.AmqpAdmin;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.ActiveProfiles;
//
//import java.math.BigDecimal;
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.UUID;
//
//import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//@SpringBootTest
//@ActiveProfiles("test")
//class DisplayGameCatalogUseCaseImplIntegrationTest extends AbstractDatabaseTest {
//
//    @Autowired
//    private DisplayGameCatalogUseCase displayGameCatalogUseCase;
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
//
//    @Test
//    void shouldDisplayGameCatalogSuccessfully() {
//        // Arrange
//        StoreGame storeGame = new StoreGame(new GameId(TestIds.GAME_ID), "Checkers", BigDecimal.valueOf(2), "A game of strategy", List.of());
//
//        // Act & Assert
//        storeGameJpaRepository.save(toStoreGameJpaEntity(storeGame));
//        assertDoesNotThrow(() -> displayGameCatalogUseCase.getAvailableGames());
//
//        // Cleanup
//        storeGameJpaRepository.deleteAll();
//    }
//
//    @Test
//    void shouldFailToDisplayGameCatalog() {
//        // Arrange
//
//        // Act & Assert
//        assertThrows(IllegalArgumentException.class, () -> {
//            displayGameCatalogUseCase.getAvailableGames();
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
