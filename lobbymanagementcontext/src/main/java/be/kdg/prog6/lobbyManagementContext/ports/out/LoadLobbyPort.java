package be.kdg.prog6.lobbyManagementContext.ports.out;

import be.kdg.prog6.lobbyManagementContext.domain.Lobby;
import java.util.UUID;

@FunctionalInterface
public interface LoadLobbyPort {
    Lobby loadLobby(UUID lobbyId);
}