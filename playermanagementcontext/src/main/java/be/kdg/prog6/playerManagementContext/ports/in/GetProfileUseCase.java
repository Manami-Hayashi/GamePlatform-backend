package be.kdg.prog6.playerManagementContext.ports.in;

import be.kdg.prog6.playerManagementContext.domain.PlayerId;
import be.kdg.prog6.playerManagementContext.domain.Profile;

public interface GetProfileUseCase {
    Profile getProfile(PlayerId playerId);
}
