package be.kdg.prog6.playerManagementContext.ports.in;

import be.kdg.prog6.playerManagementContext.domain.Game;
import be.kdg.prog6.playerManagementContext.domain.PlayerId;

public interface GamePurchasedUseCase {
    void handleGamePurchased(PlayerId playerId, Game game);
}