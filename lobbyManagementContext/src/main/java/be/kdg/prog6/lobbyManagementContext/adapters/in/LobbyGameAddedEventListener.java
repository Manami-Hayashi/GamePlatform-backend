package be.kdg.prog6.lobbyManagementContext.adapters.in;

import be.kdg.prog6.common.events.GameAddedEvent;
import be.kdg.prog6.lobbyManagementContext.domain.Game;
import be.kdg.prog6.lobbyManagementContext.domain.GameId;
import be.kdg.prog6.lobbyManagementContext.ports.in.AddLobbyGameUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class LobbyGameAddedEventListener {
    private final AddLobbyGameUseCase addLobbyGameUseCase;
    private final Logger logger = LoggerFactory.getLogger(LobbyGameAddedEventListener.class);

    public LobbyGameAddedEventListener(AddLobbyGameUseCase addLobbyGameUseCase) {
        this.addLobbyGameUseCase = addLobbyGameUseCase;
    }

    @RabbitListener(queues = "#{gameAddedQueue2.name}")
    public void handleGameAddedEvent(GameAddedEvent event) {
        logger.info("Handling game added event in the lobby");

        GameId gameId = new GameId(event.getGameId());
        logger.info("GameId: {}", gameId.id());
        Game game = new Game(gameId, event.getGameName());
        logger.info("GameName: {}", game.getGameName());

        MatchPlayersRequest matchPlayersRequest = new MatchPlayersRequest();
        matchPlayersRequest.setGameId(event.getGameId());


        addLobbyGameUseCase.addLobbyGame(game);
    }

}
