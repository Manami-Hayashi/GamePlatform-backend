package be.kdg.prog6.PlayerManagementContext.port.out;

import be.kdg.prog6.PlayerManagementContext.domain.Player;

import java.util.UUID;

@FunctionalInterface
public interface LoadPlayerPort {
    Player loadPlayer(UUID id);
}
