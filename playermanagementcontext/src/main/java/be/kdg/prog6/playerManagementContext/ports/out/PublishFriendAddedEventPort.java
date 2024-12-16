package be.kdg.prog6.playerManagementContext.ports.out;

import java.util.UUID;

public interface PublishFriendAddedEventPort {
    void publishFriendAddedEvent(UUID playerId, UUID friendId);
}
