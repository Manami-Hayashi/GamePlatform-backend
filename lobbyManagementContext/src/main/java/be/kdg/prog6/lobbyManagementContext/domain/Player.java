package be.kdg.prog6.lobbyManagementContext.domain;

import java.time.Instant;
import java.util.UUID;

public class Player {
    private final PlayerId playerId;
    private final String name;
    private Instant lastActive;
    private UUID lobbyId;
    private GameId gameId;

    public Player(PlayerId playerId, String name, UUID lobbyId) {
        this.playerId = playerId;
        this.name = name;
        this.lobbyId = lobbyId;
        this.lastActive = Instant.now();
    }

    public Player(PlayerId playerId, String name, Instant lastActive, UUID lobbyId) {
        this.playerId = playerId;
        this.name = name;
        this.lastActive = lastActive;
        this.lobbyId = lobbyId;
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

    public Instant getLastActive() {
        return lastActive;
    }

    public UUID getLobbyId() {
        return lobbyId;
    }

    public void setLobbyId(UUID lobbyId) {
        this.lobbyId = lobbyId;
    }

    public GameId getGameId() {
        return gameId;
    }

    public void setGameId(GameId gameId) {
        this.gameId = gameId;
    }
}
