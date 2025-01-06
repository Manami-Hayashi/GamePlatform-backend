//package be.kdg.prog6.playerManagementContext;
//
//import be.kdg.prog6.playerManagementContext.adapters.out.db.PlayerJpaEntity;
//import be.kdg.prog6.playerManagementContext.adapters.out.db.PlayerJpaRepository;
//import be.kdg.prog6.playerManagementContext.domain.Player;
//import be.kdg.prog6.playerManagementContext.domain.PlayerId;
//import be.kdg.prog6.playerManagementContext.ports.in.GetPlayersUseCase;
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
//class GetPlayersUseCaseImplIntegrationTest extends AbstractDatabaseTest {
//
//    @Autowired
//    private GetPlayersUseCase GetPlayersUseCase;
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
//    @Test
//    void shouldGetPlayersSuccessfully() {
//        // Arrange
//        Player player = new Player(new PlayerId(TestIds.PLAYER_ID), "Noah");
//        playerJpaRepository.save(toPlayerJpa(player));
//
//        // Act
//        List<Player> players = GetPlayersUseCase.getPlayers();
//
//        // Assert
//        Assertions.assertNotNull(players);
//
//        // Cleanup
//        playerJpaRepository.deleteAll();
//    }
//
//    @Test
//    void shouldFailToGetPlayers() {
//        // Arrange
//
//        // Act
//        List<Player> players = GetPlayersUseCase.getPlayers();
//
//        // Assert
//        Assertions.assertNotNull(players);
//
//        // Cleanup
//        playerJpaRepository.deleteAll();
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
