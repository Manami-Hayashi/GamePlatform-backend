package be.kdg.prog6.playerManagementContext.ports.out;

import be.kdg.prog6.playerManagementContext.domain.Player;

@FunctionalInterface
public interface CreatePlayerPort {
    void createPlayer(Player player);

}
