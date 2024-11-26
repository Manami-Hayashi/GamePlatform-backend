package be.kdg.prog6.lobbyManagementContext.domain;

import java.time.Instant;

public class Player {
    private final PlayerId playerId;
    private final String name;
    private Instant lastActive;

    public Player(PlayerId playerId, String name) {
        this.playerId = playerId;
        this.name = name;
        this.lastActive = Instant.now();
    }

    public PlayerId getPlayerId() {
        return playerId;
    }

    public String getName() {
        return name;
    }

    public void updateActivity() {
        this.lastActive = Instant.now();
    }

    public boolean isOnline() {
        return Instant.now().minusSeconds(300).isBefore(lastActive); // 5 minutes timeout
    }
}