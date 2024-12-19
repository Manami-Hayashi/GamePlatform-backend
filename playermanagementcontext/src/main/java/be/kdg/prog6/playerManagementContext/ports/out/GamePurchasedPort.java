package be.kdg.prog6.playerManagementContext.ports.out;

import be.kdg.prog6.playerManagementContext.domain.Game;
import be.kdg.prog6.playerManagementContext.domain.PlayerId;

public interface GamePurchasedPort {
    void gamePurchased(PlayerId playerId, Game game);
}
