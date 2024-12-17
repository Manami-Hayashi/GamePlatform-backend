package be.kdg.prog6.playerManagementContext.ports.out;

import be.kdg.prog6.playerManagementContext.domain.Player;

import java.util.List;

@FunctionalInterface
public interface LoadPlayersPort {
    List<Player> loadPlayers();
}
