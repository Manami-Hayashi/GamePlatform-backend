package be.kdg.prog6.gameStatisticsContext.port.out;

import be.kdg.prog6.gameStatisticsContext.domain.Achievement;

@FunctionalInterface
public interface UpdateAchievementPort {
    void updateAchievement(Achievement achievement);
}
