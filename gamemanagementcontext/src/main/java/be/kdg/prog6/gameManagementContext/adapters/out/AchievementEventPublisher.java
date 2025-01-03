package be.kdg.prog6.gameManagementContext.adapters.out;

import be.kdg.prog6.common.events.AchievementAddedEvent;
import be.kdg.prog6.gameManagementContext.ports.in.AddAchievementCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class AchievementEventPublisher {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameCreatedEventPublisher.class);
    private final RabbitTemplate rabbitTemplate;
    private static final String EXCHANGE_NAME = "achievement.added.exchange";
    private static final String ROUTING_KEY = "achievement.added";
    public AchievementEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void addAchievement(AddAchievementCommand command) {
        LOGGER.info("Publishing GameAddedEvent to RabbitMQ");
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, new AchievementAddedEvent(
                command.playerId(),
                command.gameId(),
                command.name(),
                command.description(),
                command.totalScore(),
                command.totalGamesPlayed(),
                command.wins(),
                command.totalTimePlayed()
        ));
    }
}
