package be.kdg.playerManagementContext.adapters.in.messaging;

import be.kdg.prog6.common.events.GameAddedEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class PlayerGameAddedListener {

    @RabbitListener(queues = "#{gameAddedQueue.name}")
    public void handleGameAddedEvent(GameAddedEvent event) {
        System.out.println("Received GameAddedEvent in playerManagementContext: " + event);
        // Add your logic to handle the event here
    }
}