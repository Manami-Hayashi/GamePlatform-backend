package be.kdg.prog6.lobbyManagementContext.adapters.out;

import be.kdg.prog6.common.events.GameSessionCreatedEvent;
import be.kdg.prog6.lobbyManagementContext.domain.GameSession;
import be.kdg.prog6.lobbyManagementContext.ports.out.UpdateGameSessionPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class SessionStartedEventPublisher implements UpdateGameSessionPort {
    private final RabbitTemplate rabbitTemplate;
    private static final String EXCHANGE_NAME = "session.started.exchange";
    private static final String ROUTING_KEY = "game.session.created";
    private static final Logger LOGGER = LoggerFactory.getLogger(SessionStartedEventPublisher.class);

    public SessionStartedEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void updateGameSession(GameSession gameSession) {
        LOGGER.info("Publishing GameSessionCreatedEvent to RabbitMQ");
        GameSessionCreatedEvent event = new GameSessionCreatedEvent(
                gameSession.getSessionId(),
                gameSession.getGameId().id(),
                gameSession.getStartTime(),
                gameSession.isActive(),
                gameSession.getPlayerIds().get(0).id(),
                gameSession.getPlayerIds().get(1).id()
        );
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, ROUTING_KEY, event);
    }
}

