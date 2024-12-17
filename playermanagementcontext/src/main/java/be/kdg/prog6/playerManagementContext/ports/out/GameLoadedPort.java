package be.kdg.prog6.playerManagementContext.ports.out;

import be.kdg.prog6.playerManagementContext.domain.Game;
import be.kdg.prog6.playerManagementContext.domain.PlayerId;

import java.util.List;

public interface GameLoadedPort {
    List<Game> loadGames(PlayerId playerId);
}
