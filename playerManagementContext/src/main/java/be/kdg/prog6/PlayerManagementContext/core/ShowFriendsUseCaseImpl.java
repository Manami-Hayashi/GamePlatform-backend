package be.kdg.prog6.PlayerManagementContext.core;

import be.kdg.prog6.PlayerManagementContext.domain.Friend;
import be.kdg.prog6.PlayerManagementContext.domain.PlayerId;
import be.kdg.prog6.PlayerManagementContext.port.in.ShowFriendsUseCase;
import be.kdg.prog6.PlayerManagementContext.port.out.LoadFriendsPort;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowFriendsUseCaseImpl implements ShowFriendsUseCase {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShowFriendsUseCaseImpl.class);
    private final LoadFriendsPort loadFriendsPort;

    public ShowFriendsUseCaseImpl(LoadFriendsPort loadFriendsPort) {
        this.loadFriendsPort = loadFriendsPort;
    }

    @Transactional
    @Override
    public List<Friend> getFriends(PlayerId playerId) {
        LOGGER.info("Retrieving friends for player with ID {}", playerId);
        List<Friend> friends = loadFriendsPort.loadFriends();
        List<Friend> playerFriends = friends.stream()
                .filter(friend -> friend.getPlayer().getPlayerId().equals(playerId))
                .toList();
        LOGGER.info("Retrieved {} friends for player with ID {}", playerFriends.size(), playerId);
        return playerFriends;
    }
}
