// Player.java
package be.kdg.prog6.lobbyManagementContext.domain;

import java.time.Instant;
import java.util.UUID;

public class Player {
    private final PlayerId playerId;
    private final String name;
    private Instant lastActive;
    private UUID lobbyId;
    private GameId gameId;
    private boolean ready; // Add the ready field

    public Player(PlayerId playerId, String name, UUID lobbyId) {
        this.playerId = playerId;
        this.name = name;
        this.lobbyId = lobbyId;
        this.lastActive = Instant.now();
        this.ready = false; // Initialize ready to false
    }

    public Player(PlayerId playerId, String name, Instant lastActive, UUID lobbyId) {
        this.playerId = playerId;
        this.name = name;
        this.lastActive = lastActive;
        this.lobbyId = lobbyId;
        this.ready = false; // Initialize ready to false
    }

    public Player(PlayerId playerId, String name, Instant lastActive, UUID lobbyId, GameId gameId) {
        this.playerId = playerId;
        this.name = name;
        this.lastActive = lastActive;
        this.lobbyId = lobbyId;
        this.gameId = gameId;
        this.ready = false; // Initialize ready to false
    }

    public Player(PlayerId playerId, String name) {
        this.playerId = playerId;
        this.name = name;
        this.lastActive = Instant.now();
        this.ready = false; // Initialize ready to false
    }



    public Player(PlayerId playerId, String name, Instant lastActive, UUID lobbyId, GameId gameId, boolean ready) {
        this.playerId = playerId;
        this.name = name;
        this.lastActive = lastActive;
        this.lobbyId = lobbyId;
        this.gameId = gameId;
        this.ready = ready;
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

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }
}