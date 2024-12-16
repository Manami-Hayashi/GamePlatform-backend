package be.kdg.prog6.gameStatisticsContext.core;

import be.kdg.prog6.gameStatisticsContext.domain.GameStatistics;
import be.kdg.prog6.gameStatisticsContext.port.in.GetPredictionForAdminUseCase;
import be.kdg.prog6.gameStatisticsContext.port.out.LoadGameStatisticsPort;
import be.kdg.prog6.gameStatisticsContext.port.out.PredictionModelPort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetPredictionForAdminUseCaseImpl implements GetPredictionForAdminUseCase {

    private final PredictionModelPort predictionModelPort;
    private final LoadGameStatisticsPort loadGameStatisticsPort;

    public GetPredictionForAdminUseCaseImpl(PredictionModelPort predictionModelPort, LoadGameStatisticsPort loadGameStatisticsPort) {
        this.predictionModelPort = predictionModelPort;
        this.loadGameStatisticsPort = loadGameStatisticsPort;
    }

    @Override
    public double getWinProbability(UUID playerId, UUID gameId, String authorizationHeader) {
        // Step 1: Fetch game statistics
        GameStatistics gameStatistics = loadGameStatisticsPort
                .loadGameStatisticsByPlayerIdAndGameId(playerId, gameId)
                .orElseThrow(() -> new IllegalArgumentException("Game statistics not found for Player ID: " + playerId + " and Game ID: " + gameId));

        // Step 2: Delegate prediction to the Python model
        return predictionModelPort.fetchWinProbability(gameStatistics, authorizationHeader);

    }

    @Override
    public GameStatistics getGameStatistics(UUID playerId, UUID gameId) {
        GameStatistics gameStatistics = loadGameStatisticsPort
                .loadGameStatisticsByPlayerIdAndGameId(playerId, gameId)
                .orElseThrow(() -> new IllegalArgumentException("Game statistics not found for Player ID: " + playerId + " and Game ID: " + gameId));

        return gameStatistics;
    }
}
