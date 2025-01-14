package be.kdg.prog6.lobbyManagementContext.domain;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Lobby {

    private final UUID lobbyId;
    private final List<PlayerId> playerIds;
    private final List<PlayerId> readyPlayers;
    private GameId gameId;
    private static final int MAX_PLAYERS = 2;

    public Lobby(UUID lobbyId, List<PlayerId> playerIds, List<PlayerId> readyPlayers, GameId gameId) {
        this.lobbyId = lobbyId;
        this.playerIds = new ArrayList<>(playerIds);
        this.readyPlayers = new ArrayList<>(readyPlayers);
        this.gameId = gameId;
    }

    public Lobby(UUID lobbyId, List<PlayerId> playerIds, GameId gameId) {
        this(lobbyId, playerIds, new ArrayList<>(), gameId);
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
        playerIds.remove(playerId);
    }

    public void readyUp(PlayerId playerId) {
        if (!playerIds.contains(playerId)) {
            throw new IllegalStateException("Player is not in the lobby.");
        }
        if (!readyPlayers.contains(playerId)) {
            readyPlayers.add(playerId);
        }
    }

    public boolean allPlayersReady() {
        return readyPlayers.size() == playerIds.size();
    }

    public boolean isPlayerReady(PlayerId playerId) {
        return readyPlayers.contains(playerId);
    }

    public UUID getLobbyId() {
        return lobbyId;
    }

    public List<PlayerId> getPlayerIds() {
        return List.copyOf(playerIds);
    }

    public GameId getGameId() {
        return gameId;
    }

    public void setGameId(GameId gameId) {
        this.gameId = gameId;
    }

    public Instant getCreationTime() {
        return Instant.now();
    }
}