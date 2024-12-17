package be.kdg.prog6.lobbyManagementContext.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class GameSession {
    private final UUID sessionId;
    private final GameId gameId;
    private final List<PlayerId> playerIds;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean isActive;
    private PlayerId winner;

    public GameSession(UUID sessionId, GameId gameId, List<PlayerId> playerIds) {
        this.sessionId = sessionId;
        this.gameId = gameId;
        this.playerIds = playerIds;
        this.startTime = null;
        this.endTime = null;
        this.isActive = false;
        this.winner = null;
    }

    public void startSession() {
        this.startTime = LocalDateTime.now();
        this.isActive = true;
    }

    public void endSession(PlayerId winner) {
        this.endTime = LocalDateTime.now();
        this.isActive = false;
        this.winner = winner;
    }

    public boolean isActive() {
        return isActive;
    }

    public PlayerId getWinner() {
        return winner;
    }

    public List<PlayerId> getPlayerIds() {
        return playerIds;
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