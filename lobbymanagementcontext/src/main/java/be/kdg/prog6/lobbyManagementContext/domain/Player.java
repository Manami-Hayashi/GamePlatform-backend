package be.kdg.prog6.lobbyManagementContext.domain;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Player {
    private final PlayerId playerId;
    private final String name;
    private Instant lastActive;
    private UUID lobbyId;
    private GameId gameId;
    private boolean ready;
    private List<PlayerId> friends = new ArrayList<>();

    public Player(PlayerId playerId, String name, UUID lobbyId) {
        this.playerId = playerId;
        this.name = name;
        this.lobbyId = lobbyId;
        this.lastActive = Instant.now();
        this.ready = false;
    }

    public Player(PlayerId playerId, String name, Instant lastActive, UUID lobbyId) {
        this.playerId = playerId;
        this.name = name;
        this.lastActive = lastActive;
        this.lobbyId = lobbyId;
        this.ready = false;
    }

    public Player(PlayerId playerId, String name, Instant lastActive, UUID lobbyId, GameId gameId) {
        this.playerId = playerId;
        this.name = name;
        this.lastActive = lastActive;
        this.lobbyId = lobbyId;
        this.gameId = gameId;
        this.ready = false;
    }

    public Player(PlayerId playerId, String name) {
        this.playerId = playerId;
        this.name = name;
        this.lastActive = Instant.now();
        this.ready = false;
    }



    public Player(PlayerId playerId, String name, Instant lastActive, UUID lobbyId, GameId gameId, boolean ready) {
        this.playerId = playerId;
        this.name = name;
        this.lastActive = lastActive;
        this.lobbyId = lobbyId;
        this.gameId = gameId;
        this.ready = ready;
    }

    public Player(PlayerId playerId, String name, Instant lastActive, UUID lobbyId, GameId gameId, boolean ready, List<PlayerId> friends) {
        this.playerId = playerId;
        this.name = name;
        this.lastActive = lastActive;
        this.lobbyId = lobbyId;
        this.gameId = gameId;
        this.ready = ready;
        this.friends = friends;
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
        return Instant.now().minusSeconds(1800).isBefore(lastActive); // 30 minutes timeout
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

    public List<PlayerId> getFriends() {
        return friends;
    }

    public void setFriends(List<PlayerId> friends) {
        this.friends = friends;
    }

    public void addFriend(PlayerId friendId) {
        friends.add(friendId);
    }
}
