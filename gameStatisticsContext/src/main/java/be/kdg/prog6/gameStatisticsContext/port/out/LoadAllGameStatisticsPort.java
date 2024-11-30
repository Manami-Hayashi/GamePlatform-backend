package be.kdg.prog6.gameStatisticsContext.port.out;

import be.kdg.prog6.gameStatisticsContext.domain.GameStatistics;

import java.util.List;

@FunctionalInterface
public interface LoadAllGameStatisticsPort {
    List<GameStatistics> loadAllGameStatistics();
}
