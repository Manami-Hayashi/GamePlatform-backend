package be.kdg.prog6.playerManagementContext.ports.in;

import be.kdg.prog6.playerManagementContext.domain.PlayerId;

public interface AcceptFriendRequestUseCase {
    void acceptFriendRequest(PlayerId playerId, PlayerId friendId);
}
