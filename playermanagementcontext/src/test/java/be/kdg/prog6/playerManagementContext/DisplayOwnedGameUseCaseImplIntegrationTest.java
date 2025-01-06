//package be.kdg.prog6.playerManagementContext;
//import be.kdg.prog6.playerManagementContext.AbstractDatabaseTest;
//import be.kdg.prog6.playerManagementContext.TestIds;
//import be.kdg.prog6.playerManagementContext.adapters.out.db.GameOwnedJpaEntity;
//import be.kdg.prog6.playerManagementContext.adapters.out.db.PlayerJpaEntity;
//import be.kdg.prog6.playerManagementContext.adapters.out.db.PlayerJpaRepository;
//import be.kdg.prog6.playerManagementContext.domain.Game;
//import be.kdg.prog6.playerManagementContext.domain.GameId;
//import be.kdg.prog6.playerManagementContext.domain.Player;
//import be.kdg.prog6.playerManagementContext.domain.PlayerId;
//import be.kdg.prog6.playerManagementContext.ports.in.DisplayOwnedGameUseCase;
//import be.kdg.prog6.playerManagementContext.adapters.out.db.GameOwnedJpaRepository;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.amqp.core.AmqpAdmin;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.ActiveProfiles;
//import java.util.List;
//@SpringBootTest
//@ActiveProfiles("test")
//class DisplayOwnedGameUseCaseImplIntegrationTest extends AbstractDatabaseTest {
//    @Autowired
//    private DisplayOwnedGameUseCase displayOwnedGameUseCase;
//    @Autowired
//    private GameOwnedJpaRepository gameOwnedJpaRepository;
//    @MockBean
//    private RabbitTemplate rabbitTemplate;
//    @MockBean
//    private AmqpAdmin amqpAdmin;
//    @Autowired
//    private PlayerJpaRepository playerJpaRepository;
//    @Test
//    void shouldDisplayOwnedGamesSuccessfully() {
//        // Arrange
//        Player player = new Player(new PlayerId(TestIds.PLAYER_ID), "Noah");
//        Game game = new Game(new GameId(TestIds.GAME_ID), TestIds.GAME_NAME);
//        gameOwnedJpaRepository.save(toGameJpa(game));
//        player.setGamesOwned(List.of(game));
//        playerJpaRepository.save(toPlayerJpa(player));
//        // Act
//        List<Game> games = displayOwnedGameUseCase.displayOwnedGames(new PlayerId(TestIds.PLAYER_ID));
//        // Assert
//        Assertions.assertNotNull(games);
//        // Cleanup
//        gameOwnedJpaRepository.deleteAll();
//        playerJpaRepository.deleteAll();
//    }
//    @Test
//    void shouldFailToDisplayOwnedGames() {
//        // Arrange
//        Player player = new Player(new PlayerId(TestIds.PLAYER_ID), "Noah");
//        Game game = new Game(new GameId(TestIds.GAME_ID), TestIds.GAME_NAME);
//        gameOwnedJpaRepository.save(toGameJpa(game));
//        playerJpaRepository.save(toPlayerJpa(player));
//        // Act
//        List<Game> games = displayOwnedGameUseCase.displayOwnedGames(new PlayerId(TestIds.PLAYER_ID));
//
//        // Assert
//        Assertions.assertEquals(0, games.size());
//
//        // Cleanup
//        gameOwnedJpaRepository.deleteAll();
//        playerJpaRepository.deleteAll();
//    }
//
//    private GameOwnedJpaEntity toGameJpa(Game game) {
//        return new GameOwnedJpaEntity(
//                game.getGameId().id(),
//                game.getGameName(),
//                game.isFavorite()
//        );
//    }
//
//    private PlayerJpaEntity toPlayerJpa(Player player) {
//        return new PlayerJpaEntity(
//                player.getPlayerId().id(),
//                player.getName()
//        );
//    }
//}
