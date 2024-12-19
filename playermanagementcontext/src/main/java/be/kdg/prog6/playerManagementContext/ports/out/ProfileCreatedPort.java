package be.kdg.prog6.playerManagementContext.ports.out;

import be.kdg.prog6.playerManagementContext.domain.Profile;

public interface ProfileCreatedPort {
    void profileCreated(Profile profile );
}
