package be.kdg.prog6.playerManagementContext.ports.in;

import be.kdg.prog6.playerManagementContext.domain.GameId;
import be.kdg.prog6.playerManagementContext.domain.PlayerId;

public interface FavoriteGameUseCase {
    void toggleFavoriteGame(PlayerId playerId, GameId gameId);
}
