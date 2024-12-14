package be.kdg.prog6.PlayerManagementContext.port.out;

import java.util.UUID;

public interface PublishFriendAddedEventPort {
    void publishFriendAddedEvent(UUID playerId, UUID friendId);
}
