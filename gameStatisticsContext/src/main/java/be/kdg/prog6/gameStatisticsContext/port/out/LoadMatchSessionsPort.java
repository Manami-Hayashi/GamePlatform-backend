package be.kdg.prog6.gameStatisticsContext.port.out;

import be.kdg.prog6.gameStatisticsContext.domain.GameStatistics;
import be.kdg.prog6.gameStatisticsContext.domain.MatchSession;

import java.util.List;

@FunctionalInterface
public interface LoadMatchSessionsPort {
    List<MatchSession> loadMatchSessionsByGameStatistics(GameStatistics gameStatistics);
}
