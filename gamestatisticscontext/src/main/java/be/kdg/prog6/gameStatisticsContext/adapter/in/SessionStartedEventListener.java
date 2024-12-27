package be.kdg.prog6.gameStatisticsContext.adapter.in;

import be.kdg.prog6.common.events.GameSessionCreatedEvent;
import be.kdg.prog6.gameStatisticsContext.port.in.AddPartialSessionCommand;
import be.kdg.prog6.gameStatisticsContext.port.in.AddPartialSessionUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class SessionStartedEventListener {
    private static final Logger logger = LoggerFactory.getLogger(SessionStartedEventListener.class);
    private final AddPartialSessionUseCase addPartialSessionUseCase;

    public SessionStartedEventListener(AddPartialSessionUseCase addPartialSessionUseCase) {
        this.addPartialSessionUseCase = addPartialSessionUseCase;
    }

    @RabbitListener(queues = "#{sessionStartedQueue.name}")
    public void handleGameSessionCreatedEvent(GameSessionCreatedEvent event) {
        logger.info("Received GameSessionCreatedEvent for session ID: {}", event.getSessionId());
        AddPartialSessionCommand command = new AddPartialSessionCommand(
                event.getSessionId(),
                event.getGameId(),
                event.getStartTime(),
                event.isActive(),
                event.getPlayerId1(),
                event.getPlayerId2()
        );
        addPartialSessionUseCase.addPartialSession(command);
    }
}
