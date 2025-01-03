package be.kdg.prog6.common.events;

import java.io.Serializable;
import java.util.UUID;

public class AchievementAddedEvent implements Serializable {
    private UUID playerId;
    private UUID gameId;
    private String name;
    private String description;

    public AchievementAddedEvent() {
    }

    public AchievementAddedEvent(UUID playerId, UUID gameId, String name, String description) {
        this.playerId = playerId;
        this.gameId = gameId;
        this.name = name;
        this.description = description;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public UUID getGameId() {
        return gameId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "AchievementAddedEvent{" +
                "playerId=" + playerId +
                ", gameId=" + gameId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
