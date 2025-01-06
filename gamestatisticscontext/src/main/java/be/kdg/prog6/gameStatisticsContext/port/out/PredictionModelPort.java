package be.kdg.prog6.gameStatisticsContext.port.out;

import be.kdg.prog6.gameStatisticsContext.domain.GameStatistics;

public interface PredictionModelPort {
    double fetchWinProbability(GameStatistics gameStatistics, String authorizationHeader);
}
