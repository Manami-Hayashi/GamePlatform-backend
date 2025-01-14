package be.kdg.prog6.gameStatisticsContext.adapter.out;

import be.kdg.prog6.gameStatisticsContext.domain.GameStatistics;
import be.kdg.prog6.gameStatisticsContext.port.out.PredictionModelPort;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class PredictionAdapter implements PredictionModelPort {
    private static final Logger logger = LoggerFactory.getLogger(PredictionAdapter.class);

//    private static final String FAST_API_URL = "http://localhost:8000/predict/";
    private static final String FAST_API_URL = "https://winprobpredictionfastapiapp.azurewebsites.net/predict/";

    @Override
    public double fetchWinProbability(GameStatistics gameStatistics, String authorizationHeader) {
        if (gameStatistics.getPlayerId() == null || gameStatistics.getGameId() == null) {
            throw new IllegalArgumentException("Player ID and Game ID must not be null.");
        }

        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> body = new HashMap<>();
        body.put("total_score", gameStatistics.getTotalScore());
        body.put("total_games_played", gameStatistics.getTotalGamesPlayed());
        body.put("wins", gameStatistics.getWins());
        body.put("losses", gameStatistics.getLosses());
        body.put("draws", gameStatistics.getDraws());
        body.put("win_loss_ratio", gameStatistics.getWinLossRatio());
        body.put("total_time_played", gameStatistics.getTotalTimePlayed());
        body.put("highest_score", gameStatistics.getHighestScore());
        body.put("moves_made", gameStatistics.getMovesMade());
        body.put("average_game_duration", gameStatistics.getAverageGameDuration());

        logger.info("Sending data to Python for prediction: {}", body);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", authorizationHeader);  // Use the provided token

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<PredictionResponse> response = restTemplate.exchange(
                    FAST_API_URL, HttpMethod.POST, request, PredictionResponse.class);

            PredictionResponse predictionResponse = response.getBody();
            logger.info("Raw response from Python: {}", response.getBody());

            if (predictionResponse == null || predictionResponse.getWinProbability() == null) {
                throw new RuntimeException("Python service returned null winProbability");
            }
            logger.info("Win probability from Python: {}", predictionResponse.getWinProbability());

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                logger.info("Raw response from Python: {}", response.getBody());
                double winProbability = response.getBody().getWinProbability();
                // Log the win probability

                logger.info("Win probability for Player ID {} in Game ID {}: {}",
                        gameStatistics.getPlayerId(), gameStatistics.getGameId(), winProbability);

                return response.getBody().getWinProbability();
            } else {
                throw new RuntimeException("Failed to fetch prediction: " + response.getStatusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error during FastAPI prediction call: " + e.getMessage(), e);
        }
    }


    private static class PredictionResponse {
        @JsonProperty("win_probability")
        private Double winProbability;

        public Double getWinProbability() {
            return winProbability;
        }

        public void setWinProbability(Double winProbability) {
            this.winProbability = winProbability;
        }
    }
}
