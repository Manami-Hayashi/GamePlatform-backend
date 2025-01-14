package be.kdg.prog6.gameStatisticsContext.port.out;

import be.kdg.prog6.gameStatisticsContext.domain.GameStatistics;
import be.kdg.prog6.gameStatisticsContext.domain.MatchSession;

import java.util.List;
import java.util.UUID;

@FunctionalInterface
public interface LoadDataByGameIdPort {
    List<MatchSession> loadDataByGameId(UUID gameId);
}
