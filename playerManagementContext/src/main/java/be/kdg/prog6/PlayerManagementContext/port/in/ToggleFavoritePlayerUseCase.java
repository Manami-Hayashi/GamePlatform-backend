package be.kdg.prog6.PlayerManagementContext.port.in;

import be.kdg.prog6.PlayerManagementContext.domain.PlayerId;

public interface ToggleFavoritePlayerUseCase {
    void toggleFavoritePlayer(PlayerId playerId, PlayerId friendId);
}
