package be.kdg.prog6.PlayerManagementContext.port.out;

import be.kdg.prog6.PlayerManagementContext.domain.Player;

import java.util.List;

@FunctionalInterface
public interface LoadPlayersPort {
    List<Player> loadPlayers();
}
