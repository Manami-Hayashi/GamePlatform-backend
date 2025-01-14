package be.kdg.prog6.gameStatisticsContext.domain;

public class Achievement {
    private final PlayerId playerId;
    private final GameId gameId;
    private final String name;
    private final String description;
    private boolean isLocked;
    private int totalScore;
    private int totalGamesPlayed;
    private int wins;
    private double totalTimePlayed;

    public Achievement(PlayerId playerId, GameId gameId, String name, String description) {
        this.playerId = playerId;
        this.gameId = gameId;
        this.name = name;
        this.description = description;
        this.isLocked = true;
    }

    public Achievement(PlayerId playerId, GameId gameId, String name, String description, boolean isLocked) {
        this.playerId = playerId;
        this.gameId = gameId;
        this.name = name;
        this.description = description;
        this.isLocked = isLocked;
    }

    public Achievement(PlayerId playerId, GameId gameId, String name, String description, boolean isLocked, int totalScore, int totalGamesPlayed, int wins, double totalTimePlayed) {
        this.playerId = playerId;
        this.gameId = gameId;
        this.name = name;
        this.description = description;
        this.isLocked = isLocked;
        this.totalScore = totalScore;
        this.totalGamesPlayed = totalGamesPlayed;
        this.wins = wins;
        this.totalTimePlayed = totalTimePlayed;
    }

    public Achievement(PlayerId playerId, GameId gameId, String name, String description, int totalScore, int totalGamesPlayed, int wins, double totalTimePlayed) {
        this.playerId = playerId;
        this.gameId = gameId;
        this.name = name;
        this.description = description;
        this.isLocked = true;
        this.totalScore = totalScore;
        this.totalGamesPlayed = totalGamesPlayed;
        this.wins = wins;
        this.totalTimePlayed = totalTimePlayed;
    }

    public PlayerId getPlayerId() {
        return playerId;
    }

    public GameId getGameId() {
        return gameId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public int getTotalGamesPlayed() {
        return totalGamesPlayed;
    }

    public int getWins() {
        return wins;
    }

    public double getTotalTimePlayed() {
        return totalTimePlayed;
    }

    public void unlock() {
        this.isLocked = false;
    }
}
