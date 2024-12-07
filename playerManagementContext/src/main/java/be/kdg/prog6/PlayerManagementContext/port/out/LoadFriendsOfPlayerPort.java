package be.kdg.prog6.PlayerManagementContext.port.out;

import be.kdg.prog6.PlayerManagementContext.domain.Friend;

import java.util.List;
import java.util.UUID;

@FunctionalInterface
public interface LoadFriendsOfPlayerPort {
    List<Friend> loadFriendsOfPlayer(UUID playerId);
}
