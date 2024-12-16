package be.kdg.prog6.gameStatisticsContext.adapter.out;

import be.kdg.prog6.gameStatisticsContext.domain.GameStatistics;
import be.kdg.prog6.gameStatisticsContext.port.out.PredictionModelPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class PredictionAdapter implements PredictionModelPort {
    private static final Logger logger = LoggerFactory.getLogger(PredictionAdapter.class);

    private static final String FAST_API_URL = "http://localhost:8000/predict/";
    private static final String KEYCLOAK_URL = "http://localhost:8181/realms/GamePlatform/protocol/openid-connect/token";
    private static final String CLIENT_ID = "backend";
    private static final String CLIENT_SECRET = "dAq5kVoa3li7gcvxz5xQQ0qVNF6qpOuf";

    private String cachedToken = null;
    private long tokenExpiryTime = 0;

    @Override
    public double fetchWinProbability(GameStatistics gameStatistics, String authorizationHeader) {
        if (gameStatistics.getPlayerId() == null || gameStatistics.getGameId() == null) {
            throw new IllegalArgumentException("Player ID and Game ID must not be null.");
        }

        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> body = new HashMap<>();
        body.put("player_id", gameStatistics.getPlayerId().toString());
        body.put("game_id", gameStatistics.getGameId().toString());

        logger.info("Request body: " + body);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", authorizationHeader);  // Use the provided token

        logger.info("authorizationHeader: " + authorizationHeader);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<PredictionResponse> response = restTemplate.exchange(
                    FAST_API_URL, HttpMethod.POST, request, PredictionResponse.class);

            logger.info("FastAPI prediction response: " + response.getBody());

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response.getBody().getWinProbability();
            } else {
                throw new RuntimeException("Failed to fetch prediction: " + response.getStatusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error during FastAPI prediction call: " + e.getMessage(), e);
        }
    }

    private String getKeycloakToken() {
        if (cachedToken != null && System.currentTimeMillis() < tokenExpiryTime) {
            return cachedToken;
        }

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("grant_type", "client_credentials");
        requestBody.add("client_id", CLIENT_ID);
        requestBody.add("client_secret", CLIENT_SECRET);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(requestBody, headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(KEYCLOAK_URL, HttpMethod.POST, request, Map.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                Map<String, Object> responseBody = response.getBody();
                cachedToken = (String) responseBody.get("access_token");
                int expiresIn = (int) responseBody.get("expires_in");
                tokenExpiryTime = System.currentTimeMillis() + (expiresIn * 1000L);
                return cachedToken;
            } else {
                throw new RuntimeException("Failed to get Keycloak token: " + response.getStatusCode());
            }
        } catch (Exception e) {
            throw new RuntimeException("Error during Keycloak token request: " + e.getMessage(), e);
        }
    }

    private static class PredictionResponse {
        private UUID playerId;
        private UUID gameId;
        private Double winProbability;

        public double getWinProbability() {
            return winProbability != null ? winProbability : 0.0;
        }

        public void setWinProbability(Double winProbability) {
            this.winProbability = winProbability;
        }
    }
}
