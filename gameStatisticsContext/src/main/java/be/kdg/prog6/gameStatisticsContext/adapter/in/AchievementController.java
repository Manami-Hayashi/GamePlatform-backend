package be.kdg.prog6.gameStatisticsContext.adapter.in;

import be.kdg.prog6.gameStatisticsContext.domain.Achievement;
import be.kdg.prog6.gameStatisticsContext.port.in.GetAchievementsUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AchievementController {

    /*
    private final GetAchievementsUseCase getAchievementsUseCase;

    public AchievementController(GetAchievementsUseCase getAchievementsUseCase) {
        this.getAchievementsUseCase = getAchievementsUseCase;
    }

    @GetMapping("/api/achievements")
    public List<Achievement> getAchievements() {
        return getAchievementsUseCase.getAchievements();
    }

     */
}
