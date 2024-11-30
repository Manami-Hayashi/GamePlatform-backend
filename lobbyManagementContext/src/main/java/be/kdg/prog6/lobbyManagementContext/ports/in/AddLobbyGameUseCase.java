package be.kdg.prog6.lobbyManagementContext.ports.in;

import be.kdg.prog6.lobbyManagementContext.domain.Game;

public interface AddLobbyGameUseCase {
    void addLobbyGame(Game game);
}
