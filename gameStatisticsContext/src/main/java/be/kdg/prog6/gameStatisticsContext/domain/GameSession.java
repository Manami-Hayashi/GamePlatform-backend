package be.kdg.prog6.gameStatisticsContext.domain;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class GameSession {
    private final UUID id;
    private final GameId gameId;
    private final List<Player> players;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean isActive;
    private Player winner;

    public GameSession(final UUID id, final GameId gameId, final List<Player> players, LocalDateTime startTime, LocalDateTime endTime, boolean isActive, Player winner) {
        this.id = id;
        this.gameId = gameId;
        this.players = players;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isActive = isActive;
        this.winner = winner;
    }

    public UUID getId() {
        return id;
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

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }
}
