package be.kdg.prog6.common.events;

import java.io.Serializable;
import java.util.UUID;

public class GameAddedEvent implements Serializable {
    private UUID gameId;
    private String gameName;
    private String description;

    public GameAddedEvent(UUID gameId, String gameName, String description) {
        this.gameId = gameId;
        this.gameName = gameName;
        this.description = description;
    }

    public UUID getGameId() {
        return gameId;
    }

    public String getGameName() {
        return gameName;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "GameAddedEvent{" +
                "gameId=" + gameId +
                ", gameName='" + gameName + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}