package be.kdg.storeContext.adapter.in;

import be.kdg.prog6.common.events.GameAddedEvent;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class StoreGameAddedListener {

    @RabbitListener(queues = "#{gameAddedQueue.name}")
    public void handleGameAddedEvent(GameAddedEvent event) {
        System.out.println("Received GameAddedEvent in storeContext: " + event);
        // Add your logic to handle the event here
    }
}