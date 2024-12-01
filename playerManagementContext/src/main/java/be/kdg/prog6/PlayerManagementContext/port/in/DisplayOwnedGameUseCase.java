package be.kdg.prog6.PlayerManagementContext.port.in;

import be.kdg.prog6.PlayerManagementContext.domain.Game;
import be.kdg.prog6.PlayerManagementContext.domain.PlayerId;

import java.util.List;

public interface DisplayOwnedGameUseCase {
    List<Game> displayOwnedGames(PlayerId playerId);
}
