package be.kdg.prog6.playerManagementContext.adapters.out;

import be.kdg.prog6.playerManagementContext.ports.out.PublishFriendAddedEventPort;
import be.kdg.prog6.common.events.FriendAddedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class FriendAddedEventPublisher implements PublishFriendAddedEventPort {
    private final RabbitTemplate rabbitTemplate;
    private static final String EXCHANGE_NAME = "friend.added.exchange";
    private static final String ROUTING_KEY = "friend.added";
    private static final Logger LOGGER = LoggerFactory.getLogger(FriendAddedEventPublisher.class);

    public FriendAddedEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publishFriendAddedEvent(UUID playerId, UUID friendId) {
        LOGGER.info("Publishing FriendAddedEvent to RabbitMQ");

        // Publish event
        FriendAddedEvent event = new FriendAddedEvent(playerId, friendId);
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, event);
    }
}
