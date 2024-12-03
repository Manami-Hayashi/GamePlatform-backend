package be.kdg.prog6.PlayerManagementContext.port.out;

import be.kdg.prog6.PlayerManagementContext.domain.PlayerId;
import be.kdg.prog6.PlayerManagementContext.domain.Profile;


public interface ProfileLoadedPort {

    Profile loadProfileById(PlayerId playerId);
}
