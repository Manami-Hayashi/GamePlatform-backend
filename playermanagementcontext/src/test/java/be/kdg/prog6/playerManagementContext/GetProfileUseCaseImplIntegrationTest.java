package be.kdg.prog6.playerManagementContext;

import be.kdg.prog6.playerManagementContext.adapters.out.db.PlayerJpaEntity;
import be.kdg.prog6.playerManagementContext.adapters.out.db.PlayerJpaRepository;
import be.kdg.prog6.playerManagementContext.adapters.out.db.ProfileJpaEntity;
import be.kdg.prog6.playerManagementContext.adapters.out.db.ProfileJpaRepository;
import be.kdg.prog6.playerManagementContext.domain.Gender;
import be.kdg.prog6.playerManagementContext.domain.Player;
import be.kdg.prog6.playerManagementContext.domain.PlayerId;
import be.kdg.prog6.playerManagementContext.domain.Profile;
import be.kdg.prog6.playerManagementContext.ports.in.GetProfileUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class GetProfileUseCaseImplIntegrationTest extends AbstractDatabaseTest {

    @Autowired
    private GetProfileUseCase getProfileUseCase;

    @MockBean
    private RabbitTemplate rabbitTemplate;

    @MockBean
    private AmqpAdmin amqpAdmin;

    @Autowired
    private PlayerJpaRepository playerJpaRepository;

    @Autowired
    private ProfileJpaRepository profileJpaRepository;

    @Test
    void shouldGetProfileSuccessfully() {
        // Arrange
        Player player = new Player(new PlayerId(TestIds.PLAYER_ID), "Noah");
        playerJpaRepository.save(toPlayerJpa(player));
        Profile profile = new Profile(player.getPlayerId(), "Noah", "avatar", Gender.MALE, "location", null);
        profileJpaRepository.save(toProfileJpa(profile));

        // Act & Assert
        Assertions.assertNotNull(getProfileUseCase.getProfile(player.getPlayerId()));

        // Cleanup
        playerJpaRepository.deleteAll();
        profileJpaRepository.deleteAll();
    }

    @Test
    void shouldFailToGetProfile() {
        // Act & Assert
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                getProfileUseCase.getProfile(new PlayerId(TestIds.PLAYER_ID)));
    }

    private PlayerJpaEntity toPlayerJpa(Player player) {
        return new PlayerJpaEntity(
                player.getPlayerId().id(),
                player.getName()
        );
    }

    private ProfileJpaEntity toProfileJpa(Profile profile) {
        return new ProfileJpaEntity(
                profile.getPlayerId().id(),
                profile.getBio(),
                profile.getAvatar(),
                profile.getGender(),
                profile.getLocation(),
                profile.getBirthDate()
        );
    }
}

