package be.kdg.prog6.PlayerManagementContext.port.out;

import be.kdg.prog6.PlayerManagementContext.domain.Profile;

public interface ProfileCreatedPort {
    void profileCreated(Profile profile );
}
