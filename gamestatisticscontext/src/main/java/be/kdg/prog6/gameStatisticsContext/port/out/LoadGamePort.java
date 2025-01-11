package be.kdg.prog6.gameStatisticsContext.port.out;

import be.kdg.prog6.gameStatisticsContext.domain.Game;

import java.util.Optional;
import java.util.UUID;

@FunctionalInterface
public interface LoadGamePort {
    Optional<Game> loadGameById(UUID gameId);
}
