package be.kdg.prog6.gameStatisticsContext.adapter.in;

import be.kdg.prog6.common.events.AchievementAddedEvent;
import be.kdg.prog6.gameStatisticsContext.domain.Achievement;
import be.kdg.prog6.gameStatisticsContext.domain.GameId;
import be.kdg.prog6.gameStatisticsContext.domain.PlayerId;
import be.kdg.prog6.gameStatisticsContext.port.in.AddAchievementUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class AchievementEventListener {
    private static final Logger logger = LoggerFactory.getLogger(AchievementEventListener.class);
    private final AddAchievementUseCase addAchievementUseCase;

    public AchievementEventListener(AddAchievementUseCase addAchievementUseCase) {
        this.addAchievementUseCase = addAchievementUseCase;
    }

    @RabbitListener(queues = "add.achievement.queue")
    public void addAchievementEvent(AchievementAddedEvent event) {
        logger.info("Received achievement added event: {}", event);
        Achievement achievement = new Achievement(new PlayerId(event.getPlayerId()), new GameId(event.getGameId()), event.getName(), event.getDescription());
        addAchievementUseCase.addAchievement(achievement);
    }
}
