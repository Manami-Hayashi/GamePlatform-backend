package be.kdg.prog6.PlayerManagementContext.adapter.in;

import be.kdg.prog6.PlayerManagementContext.domain.Game;
import be.kdg.prog6.PlayerManagementContext.domain.GameId;
import be.kdg.prog6.PlayerManagementContext.domain.PlayerId;
import be.kdg.prog6.PlayerManagementContext.port.in.GamePurchasedUseCase;
import be.kdg.prog6.common.events.GamePurchasedEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class GamePurchasedEventListener {
    private final GamePurchasedUseCase gamePurchasedUseCase;
    private final Logger logger = LoggerFactory.getLogger(GamePurchasedEventListener.class);

    public GamePurchasedEventListener(GamePurchasedUseCase gamePurchasedUseCase) {
        this.gamePurchasedUseCase = gamePurchasedUseCase;
    }
    @RabbitListener(queues = "#{gamePurchasedQueue.name}")
    public void handleGamePurchasedEvent(GamePurchasedEvent event) {
        logger.info("Handling game purchased event in the player mng for player: {} game: {}", event.getPlayerId(), event.getGameId());
        Game game = new Game(new GameId(event.getGameId()), event.getGameName(), false);
        gamePurchasedUseCase.handleGamePurchased(new PlayerId(event.getPlayerId().getId()), game);
    }
}