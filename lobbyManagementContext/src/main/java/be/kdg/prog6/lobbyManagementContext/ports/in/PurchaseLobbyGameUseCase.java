package be.kdg.prog6.lobbyManagementContext.ports.in;

import be.kdg.prog6.lobbyManagementContext.domain.Game;
import be.kdg.prog6.lobbyManagementContext.domain.PlayerId;

@FunctionalInterface
public interface PurchaseLobbyGameUseCase {
    public void purchaseLobbyGame(PlayerId playerId, Game game);
}
