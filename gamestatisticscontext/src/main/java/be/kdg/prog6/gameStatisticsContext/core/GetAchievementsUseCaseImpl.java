package be.kdg.prog6.gameStatisticsContext.core;

import be.kdg.prog6.gameStatisticsContext.domain.Achievement;
import be.kdg.prog6.gameStatisticsContext.port.in.GetAchievementsUseCase;
import be.kdg.prog6.gameStatisticsContext.port.out.LoadAchievementsPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAchievementsUseCaseImpl implements GetAchievementsUseCase {
    private final LoadAchievementsPort loadAchievementsPort;

    public GetAchievementsUseCaseImpl(LoadAchievementsPort loadAchievementsPort) {
        this.loadAchievementsPort = loadAchievementsPort;
    }

    @Override
    public List<Achievement> getAchievements() {
        List<Achievement> achievements = loadAchievementsPort.loadAchievements();
        if (achievements.isEmpty()) {
            throw new RuntimeException("No achievements found");
        }
        return achievements;
    }
}
