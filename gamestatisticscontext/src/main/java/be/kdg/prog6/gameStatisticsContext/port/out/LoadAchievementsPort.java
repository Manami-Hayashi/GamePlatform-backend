package be.kdg.prog6.gameStatisticsContext.port.out;

import be.kdg.prog6.gameStatisticsContext.domain.Achievement;

import java.util.List;
import java.util.UUID;

@FunctionalInterface
public interface LoadAchievementsPort {
    List<Achievement> loadAchievementsByPlayerIdAndGameId(UUID playerId, UUID gameId);
}
