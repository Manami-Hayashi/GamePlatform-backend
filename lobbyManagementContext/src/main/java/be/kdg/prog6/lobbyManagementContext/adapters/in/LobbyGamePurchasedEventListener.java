package be.kdg.prog6.lobbyManagementContext.adapters.in;

import be.kdg.prog6.common.events.GamePurchasedEvent;
import be.kdg.prog6.lobbyManagementContext.domain.Game;
import be.kdg.prog6.lobbyManagementContext.domain.GameId;
import be.kdg.prog6.lobbyManagementContext.domain.PlayerId;
import be.kdg.prog6.lobbyManagementContext.ports.in.PurchaseLobbyGameUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class LobbyGamePurchasedEventListener {
    private final PurchaseLobbyGameUseCase purchaseLobbyGameUseCase;
    private final Logger logger = LoggerFactory.getLogger(LobbyGamePurchasedEventListener.class);


    public LobbyGamePurchasedEventListener(PurchaseLobbyGameUseCase purchaseLobbyGameUseCase) {
        this.purchaseLobbyGameUseCase = purchaseLobbyGameUseCase;
    }

    @RabbitListener(queues = "#{gamePurchasedQueue2.name}")
    public void handleGamePurchasedEvent(GamePurchasedEvent event) {
        logger.info("Handling game purchased event in the lobby mng for player: {} game: {}", event.getPlayerId(), event.getGameId());
        Game game = new Game(new GameId(event.getGameId()), event.getGameName());
        purchaseLobbyGameUseCase.purchaseLobbyGame(new PlayerId(event.getPlayerId()), game);
    }
}
