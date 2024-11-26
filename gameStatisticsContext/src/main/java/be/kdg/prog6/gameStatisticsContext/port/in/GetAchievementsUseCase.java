package be.kdg.prog6.gameStatisticsContext.port.in;

import be.kdg.prog6.gameStatisticsContext.domain.Achievement;

import java.util.List;

@FunctionalInterface
public interface GetAchievementsUseCase {
    List<Achievement> getAchievements();
}
