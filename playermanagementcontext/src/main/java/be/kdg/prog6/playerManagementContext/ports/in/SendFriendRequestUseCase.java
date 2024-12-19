package be.kdg.prog6.playerManagementContext.ports.in;

import be.kdg.prog6.playerManagementContext.domain.PlayerId;

public interface SendFriendRequestUseCase {
    void sendFriendRequest(PlayerId playerId, PlayerId friendId);
}
