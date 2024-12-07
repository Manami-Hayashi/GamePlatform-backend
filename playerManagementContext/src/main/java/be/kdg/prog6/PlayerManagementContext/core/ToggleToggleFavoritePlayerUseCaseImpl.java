package be.kdg.prog6.PlayerManagementContext.core;

import be.kdg.prog6.PlayerManagementContext.domain.*;
import be.kdg.prog6.PlayerManagementContext.port.in.ToggleFavoritePlayerUseCase;
import be.kdg.prog6.PlayerManagementContext.port.out.LoadFriendsOfPlayerPort;
import be.kdg.prog6.PlayerManagementContext.port.out.PlayerLoadedPort;
import be.kdg.prog6.PlayerManagementContext.port.out.UpdatePlayerPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToggleToggleFavoritePlayerUseCaseImpl implements ToggleFavoritePlayerUseCase {
    private final Logger logger = LoggerFactory.getLogger(ToggleToggleFavoritePlayerUseCaseImpl.class);
    private final PlayerLoadedPort loadPlayerPort;
    private final LoadFriendsOfPlayerPort loadFriendsOfPlayerPort;
    private final UpdatePlayerPort updatePlayerPort;

    public ToggleToggleFavoritePlayerUseCaseImpl(PlayerLoadedPort loadPlayerPort, LoadFriendsOfPlayerPort loadFriendsOfPlayerPort, UpdatePlayerPort updatePlayerPort) {
        this.loadPlayerPort = loadPlayerPort;
        this.loadFriendsOfPlayerPort = loadFriendsOfPlayerPort;
        this.updatePlayerPort = updatePlayerPort;
    }

    @Override
    public void toggleFavoritePlayer(PlayerId playerId, PlayerId friendId) {
        logger.info("Toggling favorite game for player with ID {}", playerId.id());
        Player player = loadPlayerPort.loadPlayer(playerId.id());

        List<Friend> friends = loadFriendsOfPlayerPort.loadFriendsOfPlayer(playerId.id());
        Friend friendToToggle = friends.stream().filter(friend -> friend.getFriendId().equals(friendId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Player with ID " + friendId + " is not a friend of player."));

        logger.info("friends: {}", friends.size());

        // Step 3: Delegate the toggling logic to the Player entity
        player.toggleFavoriteFriend(friendToToggle);

        updatePlayerPort.updatePlayer(player);

    }

}
