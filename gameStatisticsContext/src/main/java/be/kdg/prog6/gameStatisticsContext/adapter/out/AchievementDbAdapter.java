package be.kdg.prog6.gameStatisticsContext.adapter.out;

import be.kdg.prog6.gameStatisticsContext.domain.Achievement;
import be.kdg.prog6.gameStatisticsContext.domain.GameId;
import be.kdg.prog6.gameStatisticsContext.domain.PlayerId;
import be.kdg.prog6.gameStatisticsContext.port.out.LoadAchievementsPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AchievementDbAdapter implements LoadAchievementsPort {
    private final AchievementRepository achievementRepo;

    public AchievementDbAdapter(AchievementRepository achievementRepo) {
        this.achievementRepo = achievementRepo;
    }

    @Override
    public List<Achievement> loadAchievements() {
        return achievementRepo.findAll().stream().map(this::toAchievement).collect(Collectors.toList());
    }

    private Achievement toAchievement(AchievementJpaEntity achievementJpaEntity) {
        return new Achievement(achievementJpaEntity.getId(), new PlayerId(achievementJpaEntity.getPlayerId()), new GameId(achievementJpaEntity.getGameId()), achievementJpaEntity.getName(), achievementJpaEntity.getDescription(), achievementJpaEntity.isLocked());
    }
}
