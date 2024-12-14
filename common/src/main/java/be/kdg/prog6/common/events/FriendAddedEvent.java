package be.kdg.prog6.common.events;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class FriendAddedEvent implements Serializable {
    private UUID playerId;
    private UUID friendId;

    public FriendAddedEvent(UUID playerId, UUID friendId) {
        this.playerId = playerId;
        this.friendId = friendId;
    }

    public FriendAddedEvent() {
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public UUID getFriendId() {
        return friendId;
    }

    @Override
    public String toString() {
        return "FriendAddedEvent{" +
                "playerId=" + playerId +
                ", friendId=" + friendId +
                '}';
    }
}