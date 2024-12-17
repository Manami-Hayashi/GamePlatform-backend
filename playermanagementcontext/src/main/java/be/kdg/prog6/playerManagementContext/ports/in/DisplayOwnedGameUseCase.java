package be.kdg.prog6.playerManagementContext.ports.in;

import be.kdg.prog6.playerManagementContext.domain.Game;
import be.kdg.prog6.playerManagementContext.domain.PlayerId;

import java.util.List;

public interface DisplayOwnedGameUseCase {
    List<Game> displayOwnedGames(PlayerId playerId);
}
