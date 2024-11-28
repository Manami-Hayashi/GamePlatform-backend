package be.kdg.prog6.storeContext.adapters.in;

import be.kdg.prog6.common.events.GameAddedEvent;
import be.kdg.prog6.storeContext.domain.GameId;
import be.kdg.prog6.storeContext.domain.StoreGame;
import be.kdg.prog6.storeContext.port.in.AddStoreGameUseCase;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Collections;

@Component
public class StoreGameAddedListener {
    private final AddStoreGameUseCase addStoreGameUseCase;

    public StoreGameAddedListener(AddStoreGameUseCase addStoreGameUseCase) {
        this.addStoreGameUseCase = addStoreGameUseCase;
    }

    @RabbitListener(queues = "#{gameAddedQueue1.name}")
    public void handleGameAddedEvent(GameAddedEvent event) {
        System.out.println("Received GameAddedEvent in storeContext: " + event);
        StoreGame storeGame = new StoreGame(
                new GameId(event.getGameId()),
                event.getGameName(),
                event.getPrice(),
                event.getDescription(),
                Collections.emptyList()
        );
        addStoreGameUseCase.addStoreGame(storeGame);
    }
}