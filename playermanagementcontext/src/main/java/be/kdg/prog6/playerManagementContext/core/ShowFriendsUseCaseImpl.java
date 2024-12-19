package be.kdg.prog6.playerManagementContext.core;

import be.kdg.prog6.playerManagementContext.domain.Friend;
import be.kdg.prog6.playerManagementContext.domain.Player;
import be.kdg.prog6.playerManagementContext.domain.PlayerId;
import be.kdg.prog6.playerManagementContext.ports.in.ShowFriendsUseCase;
import be.kdg.prog6.playerManagementContext.ports.out.LoadPlayerPort;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowFriendsUseCaseImpl implements ShowFriendsUseCase {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShowFriendsUseCaseImpl.class);
    private final LoadPlayerPort loadPlayerPort;

    public ShowFriendsUseCaseImpl(LoadPlayerPort loadPlayerPort) {
        this.loadPlayerPort = loadPlayerPort;
    }

    @Transactional
    @Override
    public List<Friend> getFriends(PlayerId playerId) {
        LOGGER.info("Retrieving friends for player with ID {}", playerId);
        Player player = loadPlayerPort.loadPlayer(playerId.id());
        List<Friend> playerFriends = player.getFriends();
        LOGGER.info("Retrieved {} friends for player with ID {}", playerFriends.size(), playerId);
        return playerFriends;
    }
}
