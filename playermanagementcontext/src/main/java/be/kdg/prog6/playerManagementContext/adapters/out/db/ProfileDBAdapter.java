package be.kdg.prog6.playerManagementContext.adapters.out.db;

import be.kdg.prog6.playerManagementContext.domain.PlayerId;
import be.kdg.prog6.playerManagementContext.domain.Profile;
import be.kdg.prog6.playerManagementContext.ports.out.ProfileCreatedPort;
import be.kdg.prog6.playerManagementContext.ports.out.ProfileLoadedPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ProfileDBAdapter implements ProfileCreatedPort, ProfileLoadedPort {

    private final ProfileJpaRepository profileJpaRepository;
    private static final Logger logger = LoggerFactory.getLogger(ProfileDBAdapter.class);

    public ProfileDBAdapter(ProfileJpaRepository profileJpaRepository) {
        this.profileJpaRepository = profileJpaRepository;
    }

    @Override
    public void profileCreated(Profile profile) {
        ProfileJpaEntity profileJpaEntity = profileJpaRepository.findById(profile.getPlayerId().id())
                .orElse(new ProfileJpaEntity()); // Create a new profile entity if it doesn't exist

        profileJpaEntity.setPlayerId(profile.getPlayerId().id());
        profileJpaEntity.setBio(profile.getBio());
        profileJpaEntity.setAvatar(profile.getAvatar());
        profileJpaEntity.setGender(profile.getGender());
        profileJpaEntity.setLocation(profile.getLocation());
        profileJpaEntity.setBirthDate(profile.getBirthDate());

        logger.info("Profile created: {}", profile);
        profileJpaRepository.save(profileJpaEntity);
    }

    @Override
    public Profile loadProfileById(PlayerId playerId) {
        logger.info("getting profile for player with id: {}", playerId);
        Optional<ProfileJpaEntity> profileJpaEntity = profileJpaRepository.findById(playerId.id());
        if (profileJpaEntity.isEmpty()) {
            logger.error("Profile not found for player with id: {}", playerId);
            return null;
        }
        return toDomain(profileJpaEntity.get());
    }

    private Profile toDomain(ProfileJpaEntity jpaEntity) {
        return new Profile(
                new PlayerId(jpaEntity.getPlayerId()),
                jpaEntity.getBio(),
                jpaEntity.getAvatar(),
                jpaEntity.getGender(),
                jpaEntity.getLocation(),
                jpaEntity.getBirthDate()
        );
    }
}
