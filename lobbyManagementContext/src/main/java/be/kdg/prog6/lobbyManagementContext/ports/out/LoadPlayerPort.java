package be.kdg.prog6.lobbyManagementContext.ports.out;

import be.kdg.prog6.lobbyManagementContext.domain.Player;
import java.util.UUID;

@FunctionalInterface
public interface LoadPlayerPort {
    Player loadPlayer(UUID playerId);
}