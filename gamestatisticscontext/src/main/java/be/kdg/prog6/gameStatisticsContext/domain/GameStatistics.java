package be.kdg.prog6.gameStatisticsContext.domain;

import java.util.List;

public class GameStatistics {
    private final GameId gameId;
    private final PlayerId playerId;
    private int totalScore;
    private int totalGamesPlayed;
    private int wins;
    private int losses;
    private int draws;
    private double winLossRatio;
    private double totalTimePlayed;
    private int highestScore;
    private int movesMade;
    private double averageGameDuration;
    private List<MatchSession> matchesPlayed;

    public GameStatistics(PlayerId playerId, GameId gameId, int totalScore, int totalGamesPlayed, int wins, int losses, int draws, double winLossRatio, double totalTimePlayed, int highestScore, int movesMade, double averageGameDuration) {
        this.playerId = playerId;
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

    public PlayerId getPlayerId() {
        return playerId;
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

    public double getTotalTimePlayed() {
        return totalTimePlayed;
    }

    public void setTotalTimePlayed(double totalTimePlayed) {
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

    public double getAverageGameDuration() {
        return averageGameDuration;
    }

    public void setAverageGameDuration(double averageGameDuration) {
        this.averageGameDuration = averageGameDuration;
    }

    public List<MatchSession> getMatchesPlayed() {
        return matchesPlayed;
    }

    public void setMatchesPlayed(List<MatchSession> matchesPlayed) {
        this.matchesPlayed = matchesPlayed;
    }

    public void addMatch(MatchSession matchSession) {
        matchesPlayed.add(matchSession);
    }
}
