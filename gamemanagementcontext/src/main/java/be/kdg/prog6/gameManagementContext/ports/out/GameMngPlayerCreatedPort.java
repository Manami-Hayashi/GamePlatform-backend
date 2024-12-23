package be.kdg.prog6.gameManagementContext.ports.out;

import be.kdg.prog6.gameManagementContext.domain.Player;

@FunctionalInterface
public interface GameMngPlayerCreatedPort {
    void createPlayer(Player player);

}
