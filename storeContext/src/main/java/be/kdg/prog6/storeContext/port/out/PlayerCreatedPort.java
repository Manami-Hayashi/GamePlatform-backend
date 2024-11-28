package be.kdg.prog6.storeContext.port.out;

import be.kdg.prog6.storeContext.domain.Player;

@FunctionalInterface
public interface PlayerCreatedPort {
    void createPlayer(Player player);
}
