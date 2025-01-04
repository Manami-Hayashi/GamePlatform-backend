package be.kdg.prog6.lobbyManagementContext.adapters.out.db;

import be.kdg.prog6.lobbyManagementContext.domain.GameSession;
import be.kdg.prog6.lobbyManagementContext.domain.PlayerId;
import be.kdg.prog6.lobbyManagementContext.ports.out.SaveGameSessionPort;
import be.kdg.prog6.lobbyManagementContext.ports.out.UpdateGameSessionPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Component
public class LobbyGameSessionDBAdapter implements SaveGameSessionPort, UpdateGameSessionPort {
    private static final Logger logger = LoggerFactory.getLogger(LobbyGameSessionDBAdapter.class);
    private final LobbyGameSessionJpaRepository lobbyGameSessionJpaRepository;
    private final LobbyPlayerJpaRepository lobbyPlayerJpaRepository;
    private final RestTemplate restTemplate;

    public LobbyGameSessionDBAdapter(LobbyGameSessionJpaRepository lobbyGameSessionJpaRepository, LobbyPlayerJpaRepository lobbyPlayerJpaRepository, RestTemplate restTemplate) {
        this.lobbyGameSessionJpaRepository = lobbyGameSessionJpaRepository;
        this.lobbyPlayerJpaRepository = lobbyPlayerJpaRepository;
        this.restTemplate = restTemplate;
    }

    @Override
    public void saveGameSession(GameSession gameSession) {
        LobbyGameSessionJpaEntity entity = toJpaEntity(gameSession);
        logger.info("Saving game session with ID: {}", entity.getSessionId());
        lobbyGameSessionJpaRepository.save(entity);
    }

    @Override
    public void updateGameSession(GameSession gameSession) {
        // Save the game session to the database
        saveGameSession(gameSession);

        // Retrieve player names from the database
        String player1Name = lobbyPlayerJpaRepository.findById(gameSession.getPlayerIds().get(0).id())
                .map(LobbyPlayerJpaEntity::getName)
                .orElse("Unknown");
        String player2Name = lobbyPlayerJpaRepository.findById(gameSession.getPlayerIds().get(1).id())
                .map(LobbyPlayerJpaEntity::getName)
                .orElse("Unknown");

        // Send request to another service on port 8081
        String url = "http://localhost:8081/api/checkers/connect";
        Map<String, Object> request = new HashMap<>();
        request.put("sessionId", gameSession.getSessionId());
        request.put("player1Id", gameSession.getPlayerIds().get(0).id());
        request.put("player1Name", player1Name);
        request.put("player2Id", gameSession.getPlayerIds().get(1).id());
        request.put("player2Name", player2Name);

        restTemplate.postForObject(url, request, String.class);
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
