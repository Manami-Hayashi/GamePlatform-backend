package be.kdg.prog6.PlayerManagementContext.port.in;

import be.kdg.prog6.PlayerManagementContext.domain.PlayerId;

public interface AcceptFriendRequestUseCase {
    void acceptFriendRequest(PlayerId playerId, PlayerId friendId);
}
