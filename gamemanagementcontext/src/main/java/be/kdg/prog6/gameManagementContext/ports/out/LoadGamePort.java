package be.kdg.prog6.gameManagementContext.ports.out;

import be.kdg.prog6.gameManagementContext.domain.Game;

@FunctionalInterface
public interface LoadGamePort {
    Game loadGame(String gameName);
}
