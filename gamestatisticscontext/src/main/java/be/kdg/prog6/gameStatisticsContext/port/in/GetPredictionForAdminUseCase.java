package be.kdg.prog6.gameStatisticsContext.port.in;

import be.kdg.prog6.gameStatisticsContext.domain.GameStatistics;

import java.util.UUID;

public interface GetPredictionForAdminUseCase {

    double getWinProbability(UUID playerId, UUID gameId, String authorizationHeader);  // New method

    GameStatistics getGameStatistics(UUID playerId, UUID gameId);  // New method
}
