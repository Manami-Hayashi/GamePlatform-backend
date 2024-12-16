package be.kdg.prog6.lobbyManagementContext.ports.out;

import be.kdg.prog6.lobbyManagementContext.domain.Lobby;

@FunctionalInterface
public interface SaveLobbyPort {
    void saveLobby(Lobby lobby);
}