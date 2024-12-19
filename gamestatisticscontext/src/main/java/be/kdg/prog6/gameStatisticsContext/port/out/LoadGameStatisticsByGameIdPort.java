package be.kdg.prog6.gameStatisticsContext.port.out;

import be.kdg.prog6.gameStatisticsContext.domain.GameStatistics;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@FunctionalInterface
public interface LoadGameStatisticsByGameIdPort {
    List<GameStatistics> loadGameStatisticsByGameId(UUID gameId);
}
