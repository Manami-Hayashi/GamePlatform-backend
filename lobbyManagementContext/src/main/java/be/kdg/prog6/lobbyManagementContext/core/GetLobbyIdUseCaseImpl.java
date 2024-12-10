package be.kdg.prog6.lobbyManagementContext.core;

import be.kdg.prog6.lobbyManagementContext.domain.Lobby;
import be.kdg.prog6.lobbyManagementContext.ports.in.GetLobbyIdUseCase;
import be.kdg.prog6.lobbyManagementContext.ports.out.LoadLatestLobbyPort;
import be.kdg.prog6.lobbyManagementContext.ports.out.LoadLobbyByPlayerIdPort;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetLobbyIdUseCaseImpl implements GetLobbyIdUseCase {
    private final LoadLatestLobbyPort loadLatestLobbyPort;
    private final LoadLobbyByPlayerIdPort loadLobbyByPlayerIdPort;
    private final Logger logger = LoggerFactory.getLogger(GetLobbyIdUseCaseImpl.class);

    public GetLobbyIdUseCaseImpl(LoadLatestLobbyPort loadLatestLobbyPort, LoadLobbyByPlayerIdPort loadLobbyByPlayerIdPort) {
        this.loadLobbyByPlayerIdPort = loadLobbyByPlayerIdPort;
        this.loadLatestLobbyPort = loadLatestLobbyPort;
    }

    @Override
    @Transactional
    public UUID getLobbyId(UUID playerId) {
        logger.info("Getting the latest lobby for player id {}", playerId);
        Lobby lobby = loadLobbyByPlayerIdPort.loadLobbyByPlayerId(playerId);
        if (lobby == null) {
            logger.info("No lobby found for player id {}, getting the latest lobby", playerId);
            lobby = loadLatestLobbyPort.loadLatestLobby();
        }
        logger.info("Lobby id {}", lobby != null ? lobby.getLobbyId() : "not found");
        return (lobby != null) ? lobby.getLobbyId() : null;
    }
}