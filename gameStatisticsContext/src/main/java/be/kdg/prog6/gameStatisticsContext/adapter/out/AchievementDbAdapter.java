package be.kdg.prog6.gameStatisticsContext.adapter.out;

import be.kdg.prog6.gameStatisticsContext.domain.Achievement;
import be.kdg.prog6.gameStatisticsContext.domain.PlayerId;
import be.kdg.prog6.gameStatisticsContext.port.out.LoadAchievementsPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AchievementDbAdapter implements LoadAchievementsPort {
    private final AchievementRepository achievementRepository;

    public AchievementDbAdapter(AchievementRepository achievementRepository) {
        this.achievementRepository = achievementRepository;
    }

    @Override
    public List<Achievement> loadAchievements() {
        return achievementRepository.findAll().stream().map(this::toAchievement).collect(Collectors.toList());
    }

    private Achievement toAchievement(AchievementJpaEntity achievementJpaEntity) {
        return new Achievement(achievementJpaEntity.getId(), new PlayerId(achievementJpaEntity.getPlayerId()), achievementJpaEntity.getName(), achievementJpaEntity.getDescription(), achievementJpaEntity.isLocked());
    }
}
