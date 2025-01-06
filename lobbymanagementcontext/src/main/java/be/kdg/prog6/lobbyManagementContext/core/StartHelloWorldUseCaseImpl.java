package be.kdg.prog6.lobbyManagementContext.core;

import be.kdg.prog6.common.exceptions.GameSessionNotReadyException;
import be.kdg.prog6.lobbyManagementContext.domain.GameSession;
import be.kdg.prog6.lobbyManagementContext.domain.Lobby;
import be.kdg.prog6.lobbyManagementContext.ports.in.ReadyUpResponse;
import be.kdg.prog6.lobbyManagementContext.ports.in.StartHelloWorldUseCase;
import be.kdg.prog6.lobbyManagementContext.ports.out.CheckAllPlayersReadyPort;
import be.kdg.prog6.lobbyManagementContext.ports.out.LoadLobbyPort;
import be.kdg.prog6.lobbyManagementContext.ports.out.UpdateGameSessionPort;
import be.kdg.prog6.lobbyManagementContext.ports.out.UpdateHelloWorldGameSessionPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class StartHelloWorldUseCaseImpl implements StartHelloWorldUseCase {
    private static final Logger logger = LoggerFactory.getLogger(StartHelloWorldUseCaseImpl.class);

    private final LoadLobbyPort loadLobbyPort;
    private final List<UpdateHelloWorldGameSessionPort> updateGameSessionPorts;
    private final CheckAllPlayersReadyPort checkAllPlayersReadyPort;

    public StartHelloWorldUseCaseImpl(LoadLobbyPort loadLobbyPort, List<UpdateHelloWorldGameSessionPort> updateGameSessionPorts, CheckAllPlayersReadyPort checkAllPlayersReadyPort) {
        this.loadLobbyPort = loadLobbyPort;
        this.updateGameSessionPorts = updateGameSessionPorts;
        this.checkAllPlayersReadyPort = checkAllPlayersReadyPort;
    }

    @Override
    @Transactional
    public ReadyUpResponse readyUp(UUID lobbyId) {
        Lobby lobby = loadLobbyPort.loadLobby(lobbyId);

        if (lobby == null) {
            logger.error("Lobby not found for lobby ID: {}", lobbyId);
            throw new GameSessionNotReadyException("Lobby not found.");
        }

        if (!lobby.isFull()) {
            logger.error("Lobby is not full for lobby ID: {}", lobbyId);
            throw new GameSessionNotReadyException("Lobby is not full.");
        }

        if (checkAllPlayersReadyPort.areAllPlayersReady(lobbyId)) {
            GameSession gameSession = new GameSession(UUID.randomUUID(), lobby.getGameId(), lobby.getPlayerIds());
            gameSession.startSession();
            for (UpdateHelloWorldGameSessionPort port : updateGameSessionPorts) {
                port.updateHWGameSession(gameSession);
            }            logger.info("Game session started for lobby {}", lobby.getLobbyId());

            return new ReadyUpResponse(true, gameSession);
        }

        logger.info("Not all players are ready in lobby {}", lobby.getLobbyId());
        return new ReadyUpResponse(false, null);
    }
}
