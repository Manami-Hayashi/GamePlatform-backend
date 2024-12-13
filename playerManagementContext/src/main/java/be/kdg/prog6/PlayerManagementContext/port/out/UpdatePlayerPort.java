package be.kdg.prog6.PlayerManagementContext.port.out;

import be.kdg.prog6.PlayerManagementContext.domain.Player;

public interface UpdatePlayerPort {
    void updatePlayer(Player player);
}
