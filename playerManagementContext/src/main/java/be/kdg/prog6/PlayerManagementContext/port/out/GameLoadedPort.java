package be.kdg.prog6.PlayerManagementContext.port.out;

import be.kdg.prog6.PlayerManagementContext.domain.Game;
import be.kdg.prog6.PlayerManagementContext.domain.PlayerId;

import java.util.List;

public interface GameLoadedPort {
    List<Game> loadGames(PlayerId playerId);
}
