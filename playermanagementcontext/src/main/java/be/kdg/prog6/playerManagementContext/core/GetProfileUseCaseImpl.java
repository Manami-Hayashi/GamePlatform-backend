package be.kdg.prog6.playerManagementContext.core;

import be.kdg.prog6.playerManagementContext.domain.PlayerId;
import be.kdg.prog6.playerManagementContext.domain.Profile;
import be.kdg.prog6.playerManagementContext.ports.in.GetProfileUseCase;
import be.kdg.prog6.playerManagementContext.ports.out.ProfileLoadedPort;
import org.springframework.stereotype.Service;

@Service
public class GetProfileUseCaseImpl implements GetProfileUseCase {
    private final ProfileLoadedPort profileLoadedPort;

    public GetProfileUseCaseImpl(ProfileLoadedPort profileLoadedPort) {
        this.profileLoadedPort = profileLoadedPort;
    }

    @Override
    public Profile getProfile(PlayerId playerId) {
        Profile profile = profileLoadedPort.loadProfileById(playerId);
        if (profile == null) {
            throw new IllegalArgumentException("Profile not found for player with id: " + playerId);
        }
        return profile;
    }
}
