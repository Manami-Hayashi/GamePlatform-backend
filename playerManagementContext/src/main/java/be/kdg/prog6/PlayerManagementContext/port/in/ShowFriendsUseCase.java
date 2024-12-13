package be.kdg.prog6.PlayerManagementContext.port.in;

import be.kdg.prog6.PlayerManagementContext.domain.Friend;
import be.kdg.prog6.PlayerManagementContext.domain.PlayerId;

import java.util.List;

public interface ShowFriendsUseCase {
    List<Friend> getFriends(PlayerId playerId);
}
