package be.kdg.prog6.PlayerManagementContext.port.in;

import be.kdg.prog6.PlayerManagementContext.domain.PlayerId;

public interface AddFriendUseCase {
    void addFriend(PlayerId playerId, PlayerId friendId);
}
