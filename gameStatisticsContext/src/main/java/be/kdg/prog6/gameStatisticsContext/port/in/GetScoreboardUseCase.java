package be.kdg.prog6.gameStatisticsContext.port.in;

import be.kdg.prog6.gameStatisticsContext.domain.GameStatistics;
import be.kdg.prog6.gameStatisticsContext.domain.PlayerId;

import java.util.List;

@FunctionalInterface
public interface GetScoreboardUseCase {
    List<GameStatistics> getMatchHistory(PlayerId playerId);
}
