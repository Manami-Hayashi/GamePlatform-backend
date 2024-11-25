package be.kdg.prog6.gameStatisticsContext.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class GameSession {
    private UUID sessionId;
    private GameId gameId;
    private List<Player> players;
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

    public UUID getSessionId() {
        return sessionId;
    }

    public GameId getGameId() {
        return gameId;
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

    public boolean isActive() {
        return isActive;
    }

    public Player getWinner() {
        return winner;
    }
}
