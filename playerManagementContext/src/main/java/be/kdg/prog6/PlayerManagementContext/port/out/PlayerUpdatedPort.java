package be.kdg.prog6.PlayerManagementContext.port.out;

import be.kdg.prog6.PlayerManagementContext.domain.Game;
import be.kdg.prog6.PlayerManagementContext.domain.Player;
import be.kdg.prog6.PlayerManagementContext.domain.PlayerId;

public interface PlayerUpdatedPort {
    void updatePlayer(Player player);
}
