// common/src/main/java/be/kdg/prog6/common/events/GameSessionCreatedEvent.java
package be.kdg.prog6.common.events;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.UUID;

public class GameSessionCreatedEvent {
    private UUID sessionId;
    private UUID gameId;
    private LocalDateTime startTime;

    @JsonProperty("active")
    private boolean isActive;

    private UUID playerId1;
    private UUID playerId2;

    public GameSessionCreatedEvent(UUID sessionId, UUID gameId, LocalDateTime startTime, boolean isActive, UUID playerId1, UUID playerId2) {
        this.sessionId = sessionId;
        this.gameId = gameId;
        this.startTime = startTime;
        this.isActive = isActive;
        this.playerId1 = playerId1;
        this.playerId2 = playerId2;
    }

    public GameSessionCreatedEvent() {
    }

    public UUID getSessionId() {
        return sessionId;
    }

    public UUID getGameId() {
        return gameId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public boolean isActive() {
        return isActive;
    }

    public UUID getPlayerId1() {
        return playerId1;
    }

    public UUID getPlayerId2() {
        return playerId2;
    }
}