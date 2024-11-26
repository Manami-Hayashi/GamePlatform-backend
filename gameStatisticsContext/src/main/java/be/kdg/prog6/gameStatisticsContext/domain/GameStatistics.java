package be.kdg.prog6.gameStatisticsContext.domain;

import java.util.List;

public class GameStatistics {
    private final GameId gameId;
    private int totalScore;
    private int totalGamesPlayed;
    private int wins;
    private int losses;
    private int draws;
    private double winLossRatio;
    private int totalTimePlayed;
    private int highestScore;
    private int movesMade;
    private int averageGameDuration;
    private List<MatchHistory> matchesPlayed;

    public GameStatistics(final GameId gameId, int totalScore, List<MatchHistory> matchesPlayed) {
        this.gameId = gameId;
        this.totalScore = totalScore;
        this.matchesPlayed = matchesPlayed;
    }

    public GameStatistics(GameId gameId, int totalScore, int totalGamesPlayed, int wins, int losses, int draws, double winLossRatio, int totalTimePlayed, int highestScore, int movesMade, int averageGameDuration) {
        this.gameId = gameId;
        this.totalScore = totalScore;
        this.totalGamesPlayed = totalGamesPlayed;
        this.wins = wins;
        this.losses = losses;
        this.draws = draws;
        this.winLossRatio = winLossRatio;
        this.totalTimePlayed = totalTimePlayed;
        this.highestScore = highestScore;
        this.movesMade = movesMade;
        this.averageGameDuration = averageGameDuration;
    }

    public GameId getGameId() {
        return gameId;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public int getTotalGamesPlayed() {
        return totalGamesPlayed;
    }

    public void setTotalGamesPlayed(int totalGamesPlayed) {
        this.totalGamesPlayed = totalGamesPlayed;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }

    public int getDraws() {
        return draws;
    }

    public void setDraws(int draws) {
        this.draws = draws;
    }

    public double getWinLossRatio() {
        return winLossRatio;
    }

    public void setWinLossRatio(double winLossRatio) {
        this.winLossRatio = winLossRatio;
    }

    public int getTotalTimePlayed() {
        return totalTimePlayed;
    }

    public void setTotalTimePlayed(int totalTimePlayed) {
        this.totalTimePlayed = totalTimePlayed;
    }

    public int getHighestScore() {
        return highestScore;
    }

    public void setHighestScore(int highestScore) {
        this.highestScore = highestScore;
    }

    public int getMovesMade() {
        return movesMade;
    }

    public void setMovesMade(int movesMade) {
        this.movesMade = movesMade;
    }

    public int getAverageGameDuration() {
        return averageGameDuration;
    }

    public void setAverageGameDuration(int averageGameDuration) {
        this.averageGameDuration = averageGameDuration;
    }

    public List<MatchHistory> getMatchesPlayed() {
        return matchesPlayed;
    }

    public void setMatchesPlayed(List<MatchHistory> matchesPlayed) {
        this.matchesPlayed = matchesPlayed;
    }
}
