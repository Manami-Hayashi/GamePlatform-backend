package be.kdg.prog6.lobbyManagementContext.core;

import be.kdg.prog6.lobbyManagementContext.domain.Lobby;
import be.kdg.prog6.lobbyManagementContext.ports.in.CheckReadyStatusUseCase;
import be.kdg.prog6.lobbyManagementContext.ports.out.LoadLobbyByPlayerIdPort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CheckReadyStatusUseCaseImpl implements CheckReadyStatusUseCase {

    private final LoadLobbyByPlayerIdPort loadLobbyByPlayerIdPort;

    public CheckReadyStatusUseCaseImpl(LoadLobbyByPlayerIdPort loadLobbyByPlayerIdPort) {
        this.loadLobbyByPlayerIdPort = loadLobbyByPlayerIdPort;
    }

    @Override
    public boolean checkReadyStatus(UUID playerId) {
        Lobby lobby = loadLobbyByPlayerIdPort.loadLobbyByPlayerId(playerId);
        return lobby != null && lobby.allPlayersReady();
    }
}