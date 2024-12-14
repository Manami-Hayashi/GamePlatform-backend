// LobbyFriendAddedEventListener.java
package be.kdg.prog6.lobbyManagementContext.adapters.in;

import be.kdg.prog6.common.events.FriendAddedEvent;
import be.kdg.prog6.lobbyManagementContext.ports.in.AddFriendUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class LobbyFriendAddedEventListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(LobbyFriendAddedEventListener.class);
    private final AddFriendUseCase addFriendUseCase;

    public LobbyFriendAddedEventListener(AddFriendUseCase addFriendUseCase) {
        this.addFriendUseCase = addFriendUseCase;
    }

    @RabbitListener(queues = "#{friendAddedQueue.name}")
    public void handleFriendAddedEvent(FriendAddedEvent event) {
        LOGGER.info("Handling FriendAddedEvent for player: {}", event.getPlayerId());
        addFriendUseCase.addFriend(event.getPlayerId(), event.getFriendId());
        LOGGER.info("Added friend with ID {} to player with ID {}", event.getFriendId(), event.getPlayerId());
    }
}