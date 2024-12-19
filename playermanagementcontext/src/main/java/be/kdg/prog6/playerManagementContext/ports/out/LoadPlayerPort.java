package be.kdg.prog6.playerManagementContext.ports.out;

import be.kdg.prog6.playerManagementContext.domain.Player;

import java.util.UUID;

@FunctionalInterface
public interface LoadPlayerPort {
    Player loadPlayer(UUID id);
}
