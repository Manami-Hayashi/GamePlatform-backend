package be.kdg.prog6.playerManagementContext.ports.out;

import be.kdg.prog6.playerManagementContext.domain.Player;

public interface UpdatePlayerPort {
    void updatePlayer(Player player);
}
