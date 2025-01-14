package be.kdg.prog6.gameManagementContext.core;

import be.kdg.prog6.gameManagementContext.ports.in.RecommendGamesCommand;
import be.kdg.prog6.gameManagementContext.ports.in.RecommendGamesUseCase;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class RecommendGamesUseCaseImpl implements RecommendGamesUseCase {
    private final RestTemplate restTemplate;

    @Autowired
    public RecommendGamesUseCaseImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    @Transactional
    public List<String> recommendGames(RecommendGamesCommand command) {
        //String url = "https://localhost:5000/recommend";
        String url = "https://recommendation-flask-app.azurewebsites.net/recommend";
        Map<String, Object> request = Map.of(
                "game_names", command.gameNames(),
                "num_recommendations", 2
        );

        // Set the Content-Type header to application/json
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(request, headers);

        // Send the POST request and handle the response
        ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.POST, entity, Map.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            Map<String, Object> responseBody = response.getBody();
            if (responseBody != null && responseBody.containsKey("recommended_games")) {
                return (List<String>) responseBody.get("recommended_games");
            }
        }
        throw new RuntimeException("Failed to fetch game recommendations");
    }
}
