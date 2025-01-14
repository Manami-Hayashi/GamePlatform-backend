package be.kdg.prog6.gameStatisticsContext.port.out;

import be.kdg.prog6.gameStatisticsContext.domain.GameStatistics;

@FunctionalInterface
public interface CreateGameStatisticsPort {
    void createGameStatistics(GameStatistics gameStatistics);
}
