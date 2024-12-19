package be.kdg.prog6.playerManagementContext.ports.in;

import be.kdg.prog6.playerManagementContext.domain.Friend;
import be.kdg.prog6.playerManagementContext.domain.PlayerId;

import java.util.List;

public interface ShowFriendsUseCase {
    List<Friend> getFriends(PlayerId playerId);
}
