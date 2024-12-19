package be.kdg.prog6.gameStatisticsContext.port.out;

import be.kdg.prog6.gameStatisticsContext.domain.GameId;

import java.util.List;

public interface LoadGamesPort {
    List<GameId> loadGames();
}
