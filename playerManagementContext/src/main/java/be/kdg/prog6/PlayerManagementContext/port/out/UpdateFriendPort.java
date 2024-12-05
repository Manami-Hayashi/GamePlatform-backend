package be.kdg.prog6.PlayerManagementContext.port.out;

import be.kdg.prog6.PlayerManagementContext.domain.Friend;

import java.util.UUID;

@FunctionalInterface
public interface UpdateFriendPort {
    void updateFriend(Friend friend, UUID playerId);

}
