package be.kdg.prog6.lobbyManagementContext.adapters.in;

import be.kdg.prog6.common.events.FriendAddedEvent;
import be.kdg.prog6.lobbyManagementContext.ports.in.GetFriendsUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class LobbyFriendAddedEventListener {
    private static final Logger LOGGER = LoggerFactory.getLogger(LobbyFriendAddedEventListener.class);
    private final GetFriendsUseCase getFriendsUseCase;

    public LobbyFriendAddedEventListener(GetFriendsUseCase getFriendsUseCase) {
        this.getFriendsUseCase = getFriendsUseCase;
    }

    @RabbitListener(queues = "#{friendAddedQueue.name}")
    public void handleFriendAddedEvent(FriendAddedEvent event) {
        LOGGER.info("Handling FriendAddedEvent for player: {}", event.getPlayerId());
        // Use getFriendsUseCase to get friends
        var friends = getFriendsUseCase.getFriends();
        LOGGER.info("Retrieved friends: {}", friends);
        // Handle the event, e.g., update the lobby or notify other services
    }
}