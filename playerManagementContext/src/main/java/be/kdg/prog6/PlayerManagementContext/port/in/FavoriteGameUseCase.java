package be.kdg.prog6.PlayerManagementContext.port.in;

import be.kdg.prog6.PlayerManagementContext.domain.GameId;
import be.kdg.prog6.PlayerManagementContext.domain.PlayerId;

public interface FavoriteGameUseCase {
    void toggleFavoriteGame(PlayerId playerId, GameId gameId);
}
