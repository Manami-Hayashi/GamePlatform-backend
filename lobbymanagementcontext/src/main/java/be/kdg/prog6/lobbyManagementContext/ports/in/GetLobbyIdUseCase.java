package be.kdg.prog6.lobbyManagementContext.ports.in;

import java.util.UUID;

public interface GetLobbyIdUseCase {
    UUID getLobbyId(UUID playerId);
}