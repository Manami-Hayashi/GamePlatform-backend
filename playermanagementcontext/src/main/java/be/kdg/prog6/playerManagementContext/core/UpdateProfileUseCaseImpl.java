package be.kdg.prog6.playerManagementContext.core;

import be.kdg.prog6.playerManagementContext.domain.PlayerId;
import be.kdg.prog6.playerManagementContext.domain.Profile;
import be.kdg.prog6.playerManagementContext.ports.in.UpdateProfileCommand;
import be.kdg.prog6.playerManagementContext.ports.in.UpdateProfileUseCase;
import be.kdg.prog6.playerManagementContext.ports.out.ProfileCreatedPort;
import be.kdg.prog6.playerManagementContext.ports.out.ProfileLoadedPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UpdateProfileUseCaseImpl implements UpdateProfileUseCase {

    private final Logger logger = LoggerFactory.getLogger(UpdateProfileUseCaseImpl.class);
    private final ProfileLoadedPort profileLoadedPort;
    private final ProfileCreatedPort profileCreatedPort;

    public UpdateProfileUseCaseImpl(ProfileLoadedPort profileLoadedPort, ProfileCreatedPort profileCreatedPort) {
        this.profileLoadedPort = profileLoadedPort;
        this.profileCreatedPort = profileCreatedPort;
    }

    @Override
    public void updateProfile(UpdateProfileCommand command) {
        PlayerId playerId = command.playerId();
        logger.info("Updating profile for player with id: {}", playerId);
        Profile profile = profileLoadedPort.loadProfileById(playerId);
        if(profile == null) {
            logger.info("Createing new profile for player with id: {}", playerId);
            Profile newProfile = new Profile(playerId, command.bio(), command.avatar(), command.location(), command.birthDate());
            profileCreatedPort.profileCreated(newProfile);
        }else {
            profile.updateProfile(command.bio(), command.avatar(), command.location(), command.birthDate());
            profileCreatedPort.profileCreated(profile);
        }
    }
}
