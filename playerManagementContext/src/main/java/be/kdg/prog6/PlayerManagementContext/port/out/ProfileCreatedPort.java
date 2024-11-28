package be.kdg.prog6.PlayerManagementContext.port.out;

import be.kdg.prog6.PlayerManagementContext.domain.Player;
import be.kdg.prog6.PlayerManagementContext.domain.Profile;

import java.util.UUID;

public interface ProfileCreatedPort {
    void profileCreated(Profile profile );
}
