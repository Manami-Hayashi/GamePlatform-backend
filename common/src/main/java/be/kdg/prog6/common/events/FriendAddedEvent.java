package be.kdg.prog6.common.events;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class FriendAddedEvent implements Serializable {
    private UUID playerId;

    public FriendAddedEvent(UUID playerId) {
        this.playerId = playerId;
    }
    
    public FriendAddedEvent() {
    }
    
    public UUID getPlayerId() {
        return playerId;
    }
    
    @Override
    public String toString() {
        return "FriendAddedEvent{" +
                "playerId=" + playerId +
                '}';
    }
}