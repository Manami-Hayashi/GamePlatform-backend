package be.kdg.prog6.PlayerManagementContext.port.in;

import be.kdg.prog6.PlayerManagementContext.domain.PlayerId;
import be.kdg.prog6.PlayerManagementContext.domain.Profile;

public interface GetProfileUseCase {
    Profile getProfile(PlayerId playerId);
}
