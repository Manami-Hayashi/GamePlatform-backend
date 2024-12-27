package be.kdg.prog6.gameManagementContext.ports.out;

import be.kdg.prog6.gameManagementContext.domain.Player;

import java.util.Optional;
import java.util.UUID;

@FunctionalInterface
public interface LoadPlayerPort {
    Optional<Player> loadPlayerById(UUID playerId);
}
