package be.kdg.prog6.gameStatisticsContext.domain;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

public class MatchSession {
    private final UUID id;
    private final GameId gameId;
    private final List<GameStatistics> gameStatistics;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean isActive;
    private Winner winner;
    private int scoreP1;
    private int scoreP2;
    private int movesMadeP1;
    private int movesMadeP2;

    public MatchSession(final UUID id, final GameId gameId, final List<GameStatistics> gameStatistics, LocalDateTime startTime, LocalDateTime endTime, boolean isActive, Winner winner) {
        this.id = id;
        this.gameId = gameId;
        this.gameStatistics = gameStatistics;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isActive = isActive;
        this.winner = winner;
    }

    public MatchSession(final UUID id, final GameId gameId, final List<GameStatistics> gameStatistics, LocalDateTime startTime, LocalDateTime endTime, boolean isActive, Winner winner, int scoreP1, int scoreP2, int movesMadeP1, int movesMadeP2) {
        this.id = id;
        this.gameId = gameId;
        this.gameStatistics = gameStatistics;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isActive = isActive;
        this.winner = winner;
        this.scoreP1 = scoreP1;
        this.scoreP2 = scoreP2;
        this.movesMadeP1 = movesMadeP1;
        this.movesMadeP2 = movesMadeP2;
    }

    public UUID getId() {
        return id;
    }

    public GameId getGameId() {
        return gameId;
    }

    public List<GameStatistics> getGameStatistics() {
        return gameStatistics;
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

    public Winner getWinner() {
        return winner;
    }

    public void setWinner(Winner winner) {
        this.winner = winner;
    }

    public int getScoreP1() {
        return scoreP1;
    }

    public void setScoreP1(int scoreP1) {
        this.scoreP1 = scoreP1;
    }

    public int getScoreP2() {
        return scoreP2;
    }

    public void setScoreP2(int scoreP2) {
        this.scoreP2 = scoreP2;
    }

    public int getMovesMadeP1() {
        return movesMadeP1;
    }

    public void setMovesMadeP1(int movesMadeP1) {
        this.movesMadeP1 = movesMadeP1;
    }

    public int getMovesMadeP2() {
        return movesMadeP2;
    }

    public void setMovesMadeP2(int movesMadeP2) {
        this.movesMadeP2 = movesMadeP2;
    }

    public int getDuration() {
        return (int) (ZonedDateTime.of(endTime, ZoneOffset.UTC).toEpochSecond() -
                ZonedDateTime.of(startTime, ZoneOffset.UTC).toEpochSecond());
    }
}
