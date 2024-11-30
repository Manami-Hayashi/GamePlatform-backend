package be.kdg.prog6.lobbyManagementContext.ports.out;

import be.kdg.prog6.lobbyManagementContext.domain.Player;

@FunctionalInterface
public interface LobbyPlayerCreatedPort {
    void createPlayer(Player player);
}
