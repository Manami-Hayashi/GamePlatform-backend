//package be.kdg.prog6.playerManagementContext;
//
//import be.kdg.prog6.playerManagementContext.adapters.out.db.PlayerJpaEntity;
//import be.kdg.prog6.playerManagementContext.adapters.out.db.PlayerJpaRepository;
//import be.kdg.prog6.playerManagementContext.domain.Friend;
//import be.kdg.prog6.playerManagementContext.domain.Player;
//import be.kdg.prog6.playerManagementContext.domain.PlayerId;
//import be.kdg.prog6.playerManagementContext.ports.in.ShowFriendsUseCase;
//import com.github.dockerjava.api.exception.NotFoundException;
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
//import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//@SpringBootTest
//@ActiveProfiles("test")
//class ShowFriendsUseCaseImplIntegrationTest extends AbstractDatabaseTest {
//
//    @Autowired
//    private ShowFriendsUseCase showFriendsUseCase;
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
//    void shouldShowFriendsSuccessfully() {
//        // Arrange
//        Player player = new Player(new PlayerId(TestIds.PLAYER_ID), "Noah");
//        playerJpaRepository.save(toPlayerJpa(player));
//
//        // Act
//        List<Friend> friends = showFriendsUseCase.getFriends(new PlayerId(TestIds.PLAYER_ID));
//
//        // Assert
//        Assertions.assertNotNull(friends);
//
//        // Cleanup
//        playerJpaRepository.deleteAll();
//    }
//
//    @Test
//    void shouldFailToShowFriends() {
//        // Act & Assert
//        assertThrows(Exception.class, () -> showFriendsUseCase.getFriends(new PlayerId(TestIds.PLAYER_ID)));
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
