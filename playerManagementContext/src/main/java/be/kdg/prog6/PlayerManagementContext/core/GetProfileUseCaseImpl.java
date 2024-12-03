package be.kdg.prog6.PlayerManagementContext.core;

import be.kdg.prog6.PlayerManagementContext.domain.PlayerId;
import be.kdg.prog6.PlayerManagementContext.domain.Profile;
import be.kdg.prog6.PlayerManagementContext.port.in.GetProfileUseCase;
import be.kdg.prog6.PlayerManagementContext.port.out.ProfileLoadedPort;
import org.springframework.stereotype.Service;

@Service
public class GetProfileUseCaseImpl implements GetProfileUseCase {
    private final ProfileLoadedPort profileLoadedPort;

    public GetProfileUseCaseImpl(ProfileLoadedPort profileLoadedPort) {
        this.profileLoadedPort = profileLoadedPort;
    }

    @Override
    public Profile getProfile(PlayerId playerId) {
        return profileLoadedPort.loadProfileById(playerId);
    }
}
