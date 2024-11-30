package be.kdg.prog6.gameStatisticsContext.port.in;

import be.kdg.prog6.gameStatisticsContext.domain.MatchSession;
import be.kdg.prog6.gameStatisticsContext.domain.PlayerId;

import java.util.List;
import java.util.UUID;

@FunctionalInterface
public interface GetMatchHistoryUseCase {
    List<MatchSession> getMatchHistory(UUID playerId);
}
