//package be.kdg.prog6.playerManagementContext;
//
//import be.kdg.prog6.playerManagementContext.adapters.out.db.GameOwnedJpaEntity;
//import be.kdg.prog6.playerManagementContext.adapters.out.db.GameOwnedJpaRepository;
//import be.kdg.prog6.playerManagementContext.adapters.out.db.PlayerJpaEntity;
//import be.kdg.prog6.playerManagementContext.adapters.out.db.PlayerJpaRepository;
//import be.kdg.prog6.playerManagementContext.domain.Game;
//import be.kdg.prog6.playerManagementContext.domain.GameId;
//import be.kdg.prog6.playerManagementContext.domain.Player;
//import be.kdg.prog6.playerManagementContext.domain.PlayerId;
//import be.kdg.prog6.playerManagementContext.ports.in.GamePurchasedUseCase;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.amqp.core.AmqpAdmin;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.ActiveProfiles;
//
//import java.util.List;
//
//@SpringBootTest
//@ActiveProfiles("test")
//class GamePurchasedUseCaseImplIntegrationTest extends AbstractDatabaseTest {
//
//    @Autowired
//    private GamePurchasedUseCase gamePurchasedUseCase;
//
//    @MockBean
//    private RabbitTemplate rabbitTemplate;
//
//    @MockBean
//    private AmqpAdmin amqpAdmin;
//
//    @Autowired
//    private PlayerJpaRepository playerJpaRepository;
//
//    @Autowired
//    private GameOwnedJpaRepository gameOwnedJpaRepository;
//
//    @Test
//    void shouldGamePurchasedSuccessfully() {
//        // Arrange
//        Player player = new Player(new PlayerId(TestIds.PLAYER_ID), "Noah");
//        playerJpaRepository.save(toPlayerJpa(player));
//        Game game = new Game(new GameId(TestIds.GAME_ID), TestIds.GAME_NAME);
//
//        // Act
//        gamePurchasedUseCase.handleGamePurchased(new PlayerId(TestIds.PLAYER_ID), game);
//
//        // Assert
//        GameOwnedJpaEntity gameOwnedJpaEntity = gameOwnedJpaRepository.findByGameId(TestIds.GAME_ID);
//        Assertions.assertNotNull(gameOwnedJpaEntity);
//
//        // Cleanup
//        playerJpaRepository.deleteAll();
//        gameOwnedJpaRepository.deleteAll();
//    }
//
//    @Test
//    void shouldFailToGamePurchased() {
//        // Arrange
//        Game game = new Game(new GameId(TestIds.GAME_ID), TestIds.GAME_NAME);
//
//        // Act & Assert
//        Assertions.assertThrows(Exception.class, () ->
//                gamePurchasedUseCase.handleGamePurchased(new PlayerId(TestIds.PLAYER_ID), game));
//
//        // Cleanup
//        playerJpaRepository.deleteAll();
//        gameOwnedJpaRepository.deleteAll();
//    }
//
//    private PlayerJpaEntity toPlayerJpa(Player player) {
//        return new PlayerJpaEntity(
//                player.getPlayerId().id(),
//                player.getName()
//        );
//    }
//}
//
