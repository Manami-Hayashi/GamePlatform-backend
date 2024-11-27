package be.kdg.prog6.gameStatisticsContext.port.in;

import be.kdg.prog6.gameStatisticsContext.domain.MatchSession;

@FunctionalInterface
public interface UpdateGameStatisticsUseCase {
    void updateGameStatistics(MatchSession matchSession);
}
