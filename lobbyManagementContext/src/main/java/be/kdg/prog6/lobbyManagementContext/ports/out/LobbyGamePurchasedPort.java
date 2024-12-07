package be.kdg.prog6.lobbyManagementContext.ports.out;

import be.kdg.prog6.lobbyManagementContext.domain.Game;
import be.kdg.prog6.lobbyManagementContext.domain.PlayerId;

public interface LobbyGamePurchasedPort {
    void purchaseLobbyGame(PlayerId playerId, Game game);
}
