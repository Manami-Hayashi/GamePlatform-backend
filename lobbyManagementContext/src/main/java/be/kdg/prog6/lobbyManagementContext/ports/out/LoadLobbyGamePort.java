package be.kdg.prog6.lobbyManagementContext.ports.out;

import be.kdg.prog6.lobbyManagementContext.domain.Game;
import java.util.UUID;

@FunctionalInterface
public interface LoadLobbyGamePort {
   Game loadLobbyGame(UUID gameId);
}
