package be.kdg.prog6.lobbyManagementContext.core;

import be.kdg.prog6.common.exceptions.GameSessionNotReadyException;
import be.kdg.prog6.lobbyManagementContext.domain.GameSession;
import be.kdg.prog6.lobbyManagementContext.domain.Lobby;
import be.kdg.prog6.lobbyManagementContext.domain.PlayerId;
import be.kdg.prog6.lobbyManagementContext.ports.in.StartGameUseCase;
import be.kdg.prog6.lobbyManagementContext.ports.out.LoadLobbyByPlayerIdPort;
import be.kdg.prog6.lobbyManagementContext.ports.out.SaveGameSessionPort;
import be.kdg.prog6.lobbyManagementContext.ports.out.SaveLobbyPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class StartGameUseCaseImpl implements StartGameUseCase {

    private final LoadLobbyByPlayerIdPort loadLobbyByPlayerIdPort;
    private final SaveLobbyPort saveLobbyPort;
    private final SaveGameSessionPort saveGameSessionPort;
    private final RestTemplate restTemplate;

    public StartGameUseCaseImpl(LoadLobbyByPlayerIdPort loadLobbyByPlayerIdPort, SaveLobbyPort saveLobbyPort, SaveGameSessionPort saveGameSessionPort, RestTemplate restTemplate) {
        this.loadLobbyByPlayerIdPort = loadLobbyByPlayerIdPort;
        this.saveLobbyPort = saveLobbyPort;
        this.saveGameSessionPort = saveGameSessionPort;
        this.restTemplate = restTemplate;
    }

    @Override
    @Transactional
    public GameSession readyUp(PlayerId playerId) {
        Lobby lobby = loadLobbyByPlayerIdPort.loadLobbyByPlayerId(playerId.id());

        if (!lobby.isFull()) {
            throw new GameSessionNotReadyException("Lobby is not full.");
        }

        lobby.readyUp(playerId);
        saveLobbyPort.saveLobby(lobby);

        if (lobby.allPlayersReady()) {
            GameSession gameSession = new GameSession(UUID.randomUUID(), lobby.getGameId(), lobby.getPlayerIds());
            gameSession.startSession();
            saveGameSessionPort.saveGameSession(gameSession);

            // Send request to another service on port 8081
            String url = "http://localhost:8081/connect";
            Map<String, Object> request = new HashMap<>();
            request.put("sessionId", gameSession.getSessionId());
            request.put("gameId", gameSession.getGameId().id());
            request.put("playerIds", gameSession.getPlayerIds().stream().map(PlayerId::id).toList());

            restTemplate.postForObject(url, request, Void.class);

            return gameSession;
        }

        throw new GameSessionNotReadyException("Not all players are ready.");
    }
} //make a service yes yes, inside the adpater out like u did before, updategamesesion which will do both the to outgame and to the frontend