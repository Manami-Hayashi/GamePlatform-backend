package be.kdg.prog6.gameStatisticsContext.adapter.in;

import be.kdg.prog6.gameStatisticsContext.domain.Achievement;
import be.kdg.prog6.gameStatisticsContext.port.in.GetAchievementsUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class AchievementController {
    private final GetAchievementsUseCase getAchievementsUseCase;

    public AchievementController(GetAchievementsUseCase getAchievementsUseCase) {
        this.getAchievementsUseCase = getAchievementsUseCase;
    }

    @GetMapping("/achievements")
    public List<AchievementDto> getAchievements() {
        List<Achievement> achievements = getAchievementsUseCase.getAchievements();
        List<AchievementDto> achievementDtos = new ArrayList<>();
        for (Achievement achievement : achievements) {
            achievementDtos.add(new AchievementDto(achievement.getAchievementId(), achievement.getPlayerId().id(), achievement.getName(), achievement.getDescription(), achievement.isLocked()));
        }
        return achievementDtos;
    }
}
