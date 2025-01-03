package be.kdg.prog6.gameStatisticsContext.adapter.in;

import be.kdg.prog6.gameStatisticsContext.domain.Achievement;
import be.kdg.prog6.gameStatisticsContext.domain.GameId;
import be.kdg.prog6.gameStatisticsContext.domain.PlayerId;
import be.kdg.prog6.gameStatisticsContext.port.in.AddAchievementUseCase;
import be.kdg.prog6.gameStatisticsContext.port.in.GetAchievementsUseCase;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class AchievementController {
    private final GetAchievementsUseCase getAchievementsUseCase;
    private final AddAchievementUseCase addAchievementUseCase;

    public AchievementController(GetAchievementsUseCase getAchievementsUseCase, AddAchievementUseCase addAchievementUseCase) {
        this.getAchievementsUseCase = getAchievementsUseCase;
        this.addAchievementUseCase = addAchievementUseCase;
    }

    @GetMapping("/achievements/player/{playerId}")
    public List<AchievementDto> getAchievements(@PathVariable UUID playerId) {
        List<Achievement> achievements = getAchievementsUseCase.getAchievements(playerId);
        List<AchievementDto> achievementDtos = new ArrayList<>();
        for (Achievement achievement : achievements) {
            achievementDtos.add(new AchievementDto(achievement.getPlayerId().id(), achievement.getGameId().id(), achievement.getName(), achievement.getDescription(), achievement.isLocked()));
        }
        return achievementDtos;
    }

    @PostMapping("/achievement")
    public void addAchievement(@RequestBody AchievementDto achievementDto) {
        Achievement achievement = new Achievement(new PlayerId(achievementDto.playerId()), new GameId(achievementDto.gameId()), achievementDto.name(), achievementDto.description());
        addAchievementUseCase.addAchievement(achievement);
    }
}
