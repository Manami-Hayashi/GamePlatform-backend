package be.kdg.prog6.storeContext.adapters.out;

import be.kdg.prog6.common.events.GamePurchasedEvent;
import org.slf4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class GamePurchasedEventPublisher {
    private final RabbitTemplate rabbitTemplate;
    private static final String EXCHANGE_NAME = "game.purchased.exchange";
    private static final String ROUTING_KEY = "game.purchased";
    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(GamePurchasedEventPublisher.class);

    public GamePurchasedEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    public void publish(GamePurchasedEvent event) {
        LOGGER.info("Publishing GamePurchasedEvent to RabbitMQ");
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, event);
    }
}