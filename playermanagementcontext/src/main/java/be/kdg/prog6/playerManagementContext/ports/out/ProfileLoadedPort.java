package be.kdg.prog6.playerManagementContext.ports.out;

import be.kdg.prog6.playerManagementContext.domain.PlayerId;
import be.kdg.prog6.playerManagementContext.domain.Profile;


public interface ProfileLoadedPort {

    Profile loadProfileById(PlayerId playerId);
}
