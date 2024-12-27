package be.kdg.prog6.gameStatisticsContext.port.in;

import be.kdg.prog6.gameStatisticsContext.domain.Achievement;

@FunctionalInterface
public interface AddAchievementUseCase {
    void addAchievement(Achievement achievement);
}
