package be.kdg.prog6.gameStatisticsContext.port.out;

import be.kdg.prog6.gameStatisticsContext.domain.MatchSession;

import java.util.Optional;
import java.util.UUID;

@FunctionalInterface
public interface LoadMatchSessionPort {
    Optional<MatchSession> loadMatchSessionById(UUID sessionId);

}
