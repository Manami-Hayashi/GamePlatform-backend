//package be.kdg.prog6.playerManagementContext;
//
//import be.kdg.prog6.playerManagementContext.adapters.out.db.GameOwnedJpaEntity;
//import be.kdg.prog6.playerManagementContext.adapters.out.db.PlayerJpaEntity;
//import be.kdg.prog6.playerManagementContext.adapters.out.db.PlayerJpaRepository;
//import be.kdg.prog6.playerManagementContext.domain.Game;
//import be.kdg.prog6.playerManagementContext.domain.GameId;
//import be.kdg.prog6.playerManagementContext.domain.Player;
//import be.kdg.prog6.playerManagementContext.domain.PlayerId;
//import be.kdg.prog6.playerManagementContext.ports.in.FavoriteGameUseCase;
//import be.kdg.prog6.playerManagementContext.adapters.out.db.GameOwnedJpaRepository;
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
//import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//@SpringBootTest
//@ActiveProfiles("test")
//class FavoriteGameUseCaseImplIntegrationTest extends AbstractDatabaseTest {
//
//    @Autowired
//    private FavoriteGameUseCase favoriteGameUseCase;
//
//    @Autowired
//    private GameOwnedJpaRepository gameOwnedJpaRepository;
//
//    @MockBean
//    private RabbitTemplate rabbitTemplate;
//
//    @MockBean
//    private AmqpAdmin amqpAdmin;
//    @Autowired
//    private PlayerJpaRepository playerJpaRepository;
//
//    @Test
//    void shouldToggleFavoriteGameSuccessfully() {
//        // Arrange
//        Player player = new Player(new PlayerId(TestIds.PLAYER_ID), "Noah");
//        Game game = new Game(new GameId(TestIds.GAME_ID), TestIds.GAME_NAME);
//        gameOwnedJpaRepository.save(toGameJpa(game));
//        player.setGamesOwned(List.of(game));
//        playerJpaRepository.save(toPlayerJpa(player));
//
//        // Ensure the game is loaded correctly
//        gameOwnedJpaRepository.save(new GameOwnedJpaEntity(game.getGameId().id(), game.getGameName(), game.isFavorite(), toPlayerJpa(player)));
//
//        // Act & Assert
//        assertDoesNotThrow(() -> favoriteGameUseCase.toggleFavoriteGame(new PlayerId(TestIds.PLAYER_ID), new GameId(TestIds.GAME_ID)), "Expected no exception to be thrown for toggling a favorite game owned by the player");
//
//        // Cleanup
//        gameOwnedJpaRepository.deleteAll();
//        playerJpaRepository.deleteAll();
//    }
//
//    @Test
//    void shouldFailToToggleFavoriteGameNotOwned() {
//        // Act & Assert
//        assertThrows(RuntimeException.class, () -> favoriteGameUseCase.toggleFavoriteGame(new PlayerId(TestIds.PLAYER_ID), new GameId(TestIds.GAME_ID)), "Expected an IllegalArgumentException to be thrown for toggling a game not owned by the player");
//
//        // Cleanup
//        gameOwnedJpaRepository.deleteAll();
//        playerJpaRepository.deleteAll();
//    }
//
//    private PlayerJpaEntity toPlayerJpa(Player player) {
//        return new PlayerJpaEntity(
//                player.getPlayerId().id(),
//                player.getName(),
//                player.getGamesOwned().stream().map(this::toGameJpa).toList(
//                ));
//    }
//
//    private GameOwnedJpaEntity toGameJpa(Game game) {
//        return new GameOwnedJpaEntity(
//                game.getGameId().id(),
//                game.getGameName(),
//                game.isFavorite()
//        );
//    }
//}
