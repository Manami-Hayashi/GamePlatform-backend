package be.kdg.prog6.lobbyManagementContext.ports.out;

import be.kdg.prog6.lobbyManagementContext.domain.Game;

public interface LoadLobbyGameByNamePort {
    Game loadLobbyGameByName(String gameName);
}