package be.kdg.prog6.gameManagementContext.adapters.out.external;

import be.kdg.prog6.common.exceptions.ExternalApiException;
import be.kdg.prog6.gameManagementContext.domain.Game;
import be.kdg.prog6.gameManagementContext.domain.GameId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
public class ExternalGameService {

    private final RestTemplate restTemplate;
    private final String externalApiUrl;

    public ExternalGameService(RestTemplate restTemplate, @Value("${external.api.url}") String externalApiUrl) {
        this.restTemplate = restTemplate;
        this.externalApiUrl = externalApiUrl;
    }

    public Game fetchGame(Long gameId) {
        try {
            // Fetch the game details from the external application
            Game externalGame = restTemplate.getForObject(externalApiUrl + "/api/checkers/" + gameId, Game.class);

            if (externalGame == null) {
                throw new ExternalApiException("External API returned null for game ID: " + gameId, null);
            }

            // Derive UUID from the external Long ID
            UUID derivedGameId = deriveUUIDFromLong(gameId);

            // Create a new Game object using the derived UUID
            return new Game(new GameId(derivedGameId), externalGame.getGameName(), externalGame.getDescription());
        } catch (Exception e) {
            throw new ExternalApiException("Failed to fetch game from external API with ID: " + gameId, e);
        }
    }


    private UUID deriveUUIDFromLong(Long externalId) {
        // Use the external ID in the least significant bits, and a static value in the most significant bits
        return new UUID(0L, externalId);
    }
}
