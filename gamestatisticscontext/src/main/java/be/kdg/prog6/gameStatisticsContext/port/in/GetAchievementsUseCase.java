package be.kdg.prog6.gameStatisticsContext.port.in;

import be.kdg.prog6.gameStatisticsContext.domain.Achievement;

import java.util.List;
import java.util.UUID;

@FunctionalInterface
public interface GetAchievementsUseCase {
    List<Achievement> getAchievements(UUID playerId, UUID gameId);
}
