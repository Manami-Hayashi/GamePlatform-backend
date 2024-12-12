// storeContext/src/main/java/be/kdg/prog6/storeContext/adapters/out/GamePurchasedEventPublisher.java
package be.kdg.prog6.storeContext.adapters.out;

import be.kdg.prog6.common.domain.PlayerId;
import be.kdg.prog6.common.events.GamePurchasedEvent;
import be.kdg.prog6.storeContext.port.out.UpdatePurchasePort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class GamePurchasedEventPublisher implements UpdatePurchasePort {
    private final RabbitTemplate rabbitTemplate;
    private static final String EXCHANGE_NAME = "game.purchased.exchange";
    private static final String ROUTING_KEY = "game.purchased";
    private static final Logger LOGGER = LoggerFactory.getLogger(GamePurchasedEventPublisher.class);

    public GamePurchasedEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publish(UUID customerId, UUID gameId, String gameName) {
        LOGGER.info("Publishing GamePurchasedEvent to RabbitMQ");

        // Generate or retrieve playerId
        PlayerId playerId = new PlayerId(customerId);

        GamePurchasedEvent event = new GamePurchasedEvent(playerId, gameId, gameName);
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, event);
    }
}