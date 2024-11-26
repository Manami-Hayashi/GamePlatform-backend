package be.kdg.prog6.gameManagementContext.ports.out;

import be.kdg.prog6.gameManagementContext.domain.Game;
import java.util.List;

@FunctionalInterface
public interface LoadAllGamesPort {
    List<Game> loadAllGames();
}