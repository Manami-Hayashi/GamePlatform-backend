package be.kdg.lobbyManagementContext.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class GameSession {
    private final UUID sessionId;
    private final GameId gameId;
    private final List<Player> players;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean isActive;
    private Player winner;

    public GameSession(UUID sessionId, GameId gameId, List<Player> players, LocalDateTime startTime, LocalDateTime endTime, boolean isActive, Player winner) {
        this.sessionId = sessionId;
        this.gameId = gameId;
        this.players = players;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isActive = isActive;
        this.winner = winner;
    }

    public void startSession() {
        this.startTime = LocalDateTime.now();
        this.isActive = true;
    }

    public void endSession(Player winner) {
        this.endTime = LocalDateTime.now();
        this.isActive = false;
        this.winner = winner;
    }

    public boolean isActive() {
        return isActive;
    }

    public Player getWinner() {
        return winner;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public UUID getSessionId() {
        return sessionId;
    }

    public GameId getGameId() {
        return gameId;
    }



}
