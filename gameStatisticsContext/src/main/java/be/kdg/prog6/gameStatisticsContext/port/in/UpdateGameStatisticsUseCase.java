package be.kdg.prog6.gameStatisticsContext.port.in;

import be.kdg.prog6.gameStatisticsContext.adapter.in.UpdateGameStatisticsDto;

@FunctionalInterface
public interface UpdateGameStatisticsUseCase {
    void updateGameStatistics(UpdateGameStatisticsDto updateGameStatisticsDto);
}
