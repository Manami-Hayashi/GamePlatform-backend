package be.kdg.prog6.gameStatisticsContext.domain;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

public class MatchHistory {
    private final UUID id;
    private final GameId gameId;
    private final List<Player> players;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean isActive;
    private Player winner;
    private int score;
    private int movesMade;

    public MatchHistory(final UUID id, final GameId gameId, final List<Player> players, LocalDateTime startTime, LocalDateTime endTime, boolean isActive, Player winner) {
        this.id = id;
        this.gameId = gameId;
        this.players = players;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isActive = isActive;
        this.winner = winner;
    }

    public MatchHistory(final UUID id, final GameId gameId, final List<Player> players, LocalDateTime startTime, LocalDateTime endTime, boolean isActive, Player winner, int score, int movesMade) {
        this.id = id;
        this.gameId = gameId;
        this.players = players;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isActive = isActive;
        this.winner = winner;
        this.score = score;
        this.movesMade = movesMade;
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

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Player getWinner() {
        return winner;
    }

    public void setWinner(Player winner) {
        this.winner = winner;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getMovesMade() {
        return movesMade;
    }

    public void setMovesMade(int movesMade) {
        this.movesMade = movesMade;
    }

    public int getDuration() {
        return (int) (ZonedDateTime.of(endTime, ZoneOffset.UTC).toEpochSecond() -
                ZonedDateTime.of(startTime, ZoneOffset.UTC).toEpochSecond());
    }
}
