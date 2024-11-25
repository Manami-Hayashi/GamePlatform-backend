package be.kdg.prog6.gameStatisticsContext.domain;

import java.util.List;

public class GameStatistics {
    private GameId gameId;
    private int totalScore;
    private List<GameSession> matchesPlayed;

    public GameStatistics(GameId gameId, int totalScore, List<GameSession> matchesPlayed) {
        this.gameId = gameId;
        this.totalScore = totalScore;
        this.matchesPlayed = matchesPlayed;
    }

    public GameId getGameId() {
        return gameId;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public List<GameSession> getMatchesPlayed() {
        return matchesPlayed;
    }
}
