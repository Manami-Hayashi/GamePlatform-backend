package be.kdg.prog6.gameStatisticsContext.core;

import be.kdg.prog6.gameStatisticsContext.domain.Achievement;
import be.kdg.prog6.gameStatisticsContext.port.in.AddAchievementUseCase;
import be.kdg.prog6.gameStatisticsContext.port.out.CreateAchievementPort;
import org.springframework.stereotype.Service;

@Service
public class AddAchievementUseCaseImpl implements AddAchievementUseCase {
    private final CreateAchievementPort createAchievementPort;

    public AddAchievementUseCaseImpl(CreateAchievementPort createAchievementPort) {
        this.createAchievementPort = createAchievementPort;
    }

    @Override
    public void addAchievement(Achievement achievement) {
        createAchievementPort.createAchievement(achievement);
    }
}
