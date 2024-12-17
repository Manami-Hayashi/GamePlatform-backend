package be.kdg.prog6.gameStatisticsContext.port.out;

import be.kdg.prog6.gameStatisticsContext.domain.MatchSession;

@FunctionalInterface
public interface CreateMatchSessionPort {
    void createMatchSession(MatchSession matchSession);
}
