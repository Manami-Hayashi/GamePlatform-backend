package be.kdg.prog6.gameStatisticsContext.port.in;

import be.kdg.prog6.gameStatisticsContext.domain.GameStatistics;

import java.util.List;

@FunctionalInterface
public interface GetGameStatisticsUseCase {
    List<GameStatistics> getGameStatistics();
}
