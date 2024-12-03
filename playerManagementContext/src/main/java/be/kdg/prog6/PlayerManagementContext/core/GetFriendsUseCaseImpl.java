package be.kdg.prog6.PlayerManagementContext.core;

import be.kdg.prog6.PlayerManagementContext.domain.Friend;
import be.kdg.prog6.PlayerManagementContext.domain.PlayerId;
import be.kdg.prog6.PlayerManagementContext.port.in.GetFriendsUseCase;
import be.kdg.prog6.PlayerManagementContext.port.out.LoadFriendsPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetFriendsUseCaseImpl implements GetFriendsUseCase {
    private static final Logger LOGGER = LoggerFactory.getLogger(GetFriendsUseCaseImpl.class);
    private final LoadFriendsPort loadFriendsPort;

    public GetFriendsUseCaseImpl(LoadFriendsPort loadFriendsPort) {
        this.loadFriendsPort = loadFriendsPort;
    }

    @Override
    public List<Friend> getFriends(PlayerId playerId) {
        LOGGER.info("Retrieving friends for player with ID {}", playerId);
        return loadFriendsPort.loadFriends(playerId.id());
    }
}
