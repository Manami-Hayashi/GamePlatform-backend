package be.kdg.prog6.PlayerManagementContext.port.in;

import be.kdg.prog6.PlayerManagementContext.domain.PlayerId;

public interface SendFriendRequestUseCase {
    void sendFriendRequest(PlayerId playerId, PlayerId friendId);
}
