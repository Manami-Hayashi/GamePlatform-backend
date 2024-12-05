package be.kdg.prog6.PlayerManagementContext.port.out;

import be.kdg.prog6.PlayerManagementContext.domain.Friend;
import be.kdg.prog6.PlayerManagementContext.domain.Player;

@FunctionalInterface
public interface CreateFriendPort {
    void createFriend(Friend friend, Player player);

}
