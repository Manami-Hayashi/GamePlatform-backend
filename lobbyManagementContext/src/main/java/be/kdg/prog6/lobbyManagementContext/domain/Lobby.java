package be.kdg.prog6.lobbyManagementContext.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Lobby {

    private final UUID lobbyId;
    private final List<PlayerId> playerIds;
    private final GameId gameId;
    private static final int MAX_PLAYERS = 2;

    public Lobby(UUID lobbyId, List<PlayerId> playerIds, GameId gameId) {
        this.lobbyId = lobbyId;
        this.playerIds = new ArrayList<>(playerIds);
        this.gameId = gameId;
    }

    public Lobby(UUID lobbyId, GameId gameId) {
        this(lobbyId, new ArrayList<>(), gameId);
    }

    public Lobby(GameId gameId) {
        this(UUID.randomUUID(), new ArrayList<>(), gameId);
    }



    public boolean isFull() {
        return playerIds.size() >= MAX_PLAYERS;
    }

    public void addPlayer(PlayerId playerId) {
        if (isFull()) {
            throw new IllegalStateException("Lobby is full.");
        }
        if (playerIds.contains(playerId)) {
            throw new IllegalStateException("Player is already in the lobby.");
        }
        playerIds.add(playerId);
    }

    public void removePlayer(PlayerId playerId) {
        if (!playerIds.contains(playerId)) {
            throw new IllegalStateException("Player is not in the lobby.");
        }
        playerIds.remove(playerId);  // Remove the player from the list
    }

    public void inviteFriend(PlayerId playerId, PlayerId friendId) {
        if (!playerIds.contains(playerId)) {
            throw new IllegalStateException("Player must be in the lobby to invite a friend.");
        }
        addPlayer(friendId); // Reuses addPlayer for logic
    }

    public void matchWithRandomPlayer(List<PlayerId> availablePlayers) {
        for (PlayerId playerId : availablePlayers) {
            if (!playerIds.contains(playerId) && !isFull()) {
                addPlayer(playerId);
                return;
            }
        }
        throw new IllegalStateException("No suitable players available to match.");
    }

    public boolean containsPlayer(PlayerId playerId) {
        return playerIds.contains(playerId);
    }

    public UUID getLobbyId() {
        return lobbyId;
    }

    public List<PlayerId> getPlayerIds() {
        return List.copyOf(playerIds); // Immutable copy
    }

    public GameId getGameId() {
        return gameId;
    }
}