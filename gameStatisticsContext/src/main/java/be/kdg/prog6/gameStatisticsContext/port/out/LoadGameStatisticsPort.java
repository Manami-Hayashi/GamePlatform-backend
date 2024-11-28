package be.kdg.prog6.gameStatisticsContext.port.out;

import be.kdg.prog6.gameStatisticsContext.domain.GameStatistics;

import java.util.Optional;
import java.util.UUID;

@FunctionalInterface
public interface LoadGameStatisticsPort {
    Optional<GameStatistics> loadGameStatisticsByPlayerIdAndGameId(UUID playerId, UUID gameId);
}
