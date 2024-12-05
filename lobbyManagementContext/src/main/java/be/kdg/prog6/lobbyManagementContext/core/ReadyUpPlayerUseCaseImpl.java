package be.kdg.prog6.lobbyManagementContext.core;

import be.kdg.prog6.lobbyManagementContext.domain.Lobby;
import be.kdg.prog6.lobbyManagementContext.domain.Player;
import be.kdg.prog6.lobbyManagementContext.domain.PlayerId;
import be.kdg.prog6.lobbyManagementContext.ports.in.ReadyUpPlayerUseCase;
import be.kdg.prog6.lobbyManagementContext.ports.out.LoadLobbyByPlayerIdPort;
import be.kdg.prog6.lobbyManagementContext.ports.out.LoadPlayerPort;
import be.kdg.prog6.lobbyManagementContext.ports.out.SaveLobbyPort;
import be.kdg.prog6.lobbyManagementContext.ports.out.UpdatePlayerPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReadyUpPlayerUseCaseImpl implements ReadyUpPlayerUseCase {
    private static final Logger logger = LoggerFactory.getLogger(ReadyUpPlayerUseCaseImpl.class);

    private final LoadLobbyByPlayerIdPort loadLobbyByPlayerIdPort;
    private final SaveLobbyPort saveLobbyPort;
    private final LoadPlayerPort loadPlayerPort;
    private final UpdatePlayerPort updatePlayerPort;

    public ReadyUpPlayerUseCaseImpl(LoadLobbyByPlayerIdPort loadLobbyByPlayerIdPort, SaveLobbyPort saveLobbyPort, LoadPlayerPort loadPlayerPort, UpdatePlayerPort updatePlayerPort) {
        this.loadLobbyByPlayerIdPort = loadLobbyByPlayerIdPort;
        this.saveLobbyPort = saveLobbyPort;
        this.loadPlayerPort = loadPlayerPort;
        this.updatePlayerPort = updatePlayerPort;
    }

    @Override
    @Transactional
    public void readyUp(PlayerId playerId) {
        Lobby lobby = loadLobbyByPlayerIdPort.loadLobbyByPlayerId(playerId.id());
        logger.info("Player {} is readying up", playerId.id());
        lobby.readyUp(playerId);
        saveLobbyPort.saveLobby(lobby);

        // Load the player and update the ready status
        Player player = loadPlayerPort.loadPlayer(playerId.id());
        if (player != null) {
            player.setReady(true);
            updatePlayerPort.updatePlayer(player);
            logger.info("Player {} is ready", playerId.id());
        } else {
            logger.error("Player not found for ID: {}", playerId.id());
        }
    }
}