package be.kdg.prog6.lobbyManagementContext.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Lobby {
    private final UUID lobbyId;
    private final List<PlayerId> playerIds;
    private static final int MAX_PLAYERS = 2;

    // Constructor for creating a new Lobby with a specific lobbyId
    public Lobby(UUID lobbyId, List<PlayerId> playerIds) {
        this.lobbyId = lobbyId;
        this.playerIds = new ArrayList<>(playerIds);  // Creating a new list to prevent mutation
    }

    public Lobby(UUID lobbyId) {
        this(lobbyId, new ArrayList<>());
    }

    public Lobby() {
        this(UUID.randomUUID(), new ArrayList<>());
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
}
