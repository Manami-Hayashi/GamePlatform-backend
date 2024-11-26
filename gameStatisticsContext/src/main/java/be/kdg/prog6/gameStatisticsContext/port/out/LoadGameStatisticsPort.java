package be.kdg.prog6.gameStatisticsContext.port.out;

import be.kdg.prog6.gameStatisticsContext.domain.Player;

import java.util.Optional;
import java.util.UUID;

@FunctionalInterface
public interface LoadGameStatisticsPort {
    Optional<Player> loadGameStatisticsByPlayerId(UUID playerId);
}
