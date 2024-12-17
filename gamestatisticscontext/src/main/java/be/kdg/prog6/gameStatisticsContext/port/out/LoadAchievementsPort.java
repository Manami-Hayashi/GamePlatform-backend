package be.kdg.prog6.gameStatisticsContext.port.out;

import be.kdg.prog6.gameStatisticsContext.domain.Achievement;

import java.util.List;

@FunctionalInterface
public interface LoadAchievementsPort {
    List<Achievement> loadAchievements();
}
