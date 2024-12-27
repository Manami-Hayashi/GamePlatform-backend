package be.kdg.prog6.gameStatisticsContext.port.in;

import be.kdg.prog6.gameStatisticsContext.domain.GameId;

import java.util.List;

public interface GetGamesUseCase {
    List<GameId> getGames();
}
