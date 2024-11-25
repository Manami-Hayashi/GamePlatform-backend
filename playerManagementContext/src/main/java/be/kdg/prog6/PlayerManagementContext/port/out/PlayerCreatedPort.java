package be.kdg.prog6.PlayerManagementContext.port.out;

import be.kdg.prog6.PlayerManagementContext.domain.Player;

@FunctionalInterface
public interface PlayerCreatedPort {
    void createPlayer(Player player);

}