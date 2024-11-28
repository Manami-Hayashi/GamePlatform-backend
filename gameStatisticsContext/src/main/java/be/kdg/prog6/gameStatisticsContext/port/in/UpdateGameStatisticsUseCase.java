package be.kdg.prog6.gameStatisticsContext.port.in;

import be.kdg.prog6.gameStatisticsContext.adapter.in.MatchSessionDto;

@FunctionalInterface
public interface UpdateGameStatisticsUseCase {
    void updateGameStatistics(MatchSessionDto matchSessionDto);
}
