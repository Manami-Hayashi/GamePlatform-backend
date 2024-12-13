package be.kdg.prog6.PlayerManagementContext.port.in;

import be.kdg.prog6.PlayerManagementContext.domain.Game;
import be.kdg.prog6.PlayerManagementContext.domain.PlayerId;

public interface GamePurchasedUseCase {
    void handleGamePurchased(PlayerId playerId, Game game);
}