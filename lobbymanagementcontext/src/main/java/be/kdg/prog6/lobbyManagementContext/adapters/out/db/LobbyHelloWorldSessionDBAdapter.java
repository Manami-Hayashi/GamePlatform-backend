package be.kdg.prog6.lobbyManagementContext.adapters.out.db;

import be.kdg.prog6.lobbyManagementContext.domain.GameSession;
import be.kdg.prog6.lobbyManagementContext.domain.PlayerId;
import be.kdg.prog6.lobbyManagementContext.ports.out.SaveHelloWorldGameSession;
import be.kdg.prog6.lobbyManagementContext.ports.out.UpdateHelloWorldGameSessionPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


@Component
public class LobbyHelloWorldSessionDBAdapter implements SaveHelloWorldGameSession, UpdateHelloWorldGameSessionPort {
    private static final Logger logger = LoggerFactory.getLogger(LobbyHelloWorldSessionDBAdapter.class);
    private final LobbyGameSessionJpaRepository gameSessionJpaRepository;
    private final LobbyPlayerJpaRepository playerJpaRepository;
    private final RestTemplate restTemplate;

    public LobbyHelloWorldSessionDBAdapter(LobbyGameSessionJpaRepository gameSessionJpaRepository, LobbyPlayerJpaRepository playerJpaRepository, RestTemplate restTemplate) {
        this.gameSessionJpaRepository = gameSessionJpaRepository;
        this.playerJpaRepository = playerJpaRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public void saveGameSession(GameSession gameSession) {
        LobbyGameSessionJpaEntity entity = toJpaEntity(gameSession);
        logger.info("Saving Hello World game session with ID: {}", entity.getSessionId());
        logger.info("Game ID: {}", entity.getGameId());
        logger.info("Player IDs in the lobby: {}", entity.getPlayerIds());
        gameSessionJpaRepository.save(entity);
    }

    @Override
    public void updateHWGameSession(GameSession gameSession) {
        // Save the game session to the database
        saveGameSession(gameSession);

        // Retrieve player names from the database
        String player1Name = playerJpaRepository.findById(gameSession.getPlayerIds().get(0).id())
                .map(LobbyPlayerJpaEntity::getName)
                .orElse("Unknown");
        String player2Name = playerJpaRepository.findById(gameSession.getPlayerIds().get(1).id())
                .map(LobbyPlayerJpaEntity::getName)
                .orElse("Unknown");

        logger.info("Fetching Player 1: {}", gameSession.getPlayerIds().get(0).id());
        logger.info("Fetching Player 2: {}", gameSession.getPlayerIds().get(1).id());

        // Send request to the game application (Hello World game) to start the game
//        String url = "http://localhost:8082/api/game/create-session"; // Update with actual URL
        String url = "https://hello-world-container.nicehill-98b0fd60.westeurope.azurecontainerapps.io/api/game/create-session";
        Map<String, Object> request = new HashMap<>();
        request.put("sessionId", gameSession.getSessionId());
        request.put("player1Id", gameSession.getPlayerIds().get(0).id());
        request.put("player1Name", player1Name);
        request.put("player2Id", gameSession.getPlayerIds().get(1).id());
        request.put("player2Name", player2Name);

        logger.info("REST API Request Payload: {}", request);

        restTemplate.postForObject(url, request, Void.class);
    }

    private LobbyGameSessionJpaEntity toJpaEntity(GameSession gameSession) {
        LobbyGameSessionJpaEntity entity = new LobbyGameSessionJpaEntity();
        entity.setSessionId(gameSession.getSessionId());
        entity.setGameId(gameSession.getGameId().id());
        entity.setPlayerIds(gameSession.getPlayerIds().stream().map(PlayerId::id).toList());
        entity.setStartTime(gameSession.getStartTime());
        entity.setEndTime(gameSession.getEndTime());
        entity.setActive(gameSession.isActive());
        entity.setWinner(gameSession.getWinner() != null ? gameSession.getWinner().id() : null);
        return entity;
    }
}
