package be.kdg.prog6.gameManagementContext.adapters.out;

import be.kdg.prog6.common.events.GameAddedEvent;
import be.kdg.prog6.gameManagementContext.domain.Game;
import be.kdg.prog6.gameManagementContext.ports.out.UpdateGamePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class GameCreatedEventPublisher implements UpdateGamePort {
    private final RabbitTemplate rabbitTemplate;
    private static final String EXCHANGE_NAME = "game.added.exchange";
    private static final Logger LOGGER = LoggerFactory.getLogger(GameCreatedEventPublisher.class);

    public GameCreatedEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void updateGame(Game game) {
        final String routingKey = "game." + game.getGameId().id() + ".added";
        LOGGER.info("Publishing GameAddedEvent to RabbitMQ: {}", routingKey);
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, routingKey, new GameAddedEvent(
                game.getGameId().id(),
                game.getGameName(),
                game.getDescription()
        ));
    }
}