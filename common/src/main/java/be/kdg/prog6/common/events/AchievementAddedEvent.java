package be.kdg.prog6.common.events;

import java.io.Serializable;
import java.util.UUID;

public class AchievementAddedEvent implements Serializable {
    private UUID playerId;
    private UUID gameId;
    private String name;
    private String description;
    private int totalScore;
    private int totalGamesPlayed;
    private int wins;
    private double totalTimePlayed;

    public AchievementAddedEvent() {
    }

    public AchievementAddedEvent(UUID playerId, UUID gameId, String name, String description, int totalScore, int totalGamesPlayed, int wins, double totalTimePlayed) {
        this.playerId = playerId;
        this.gameId = gameId;
        this.name = name;
        this.description = description;
        this.totalScore = totalScore;
        this.totalGamesPlayed = totalGamesPlayed;
        this.wins = wins;
        this.totalTimePlayed = totalTimePlayed;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public UUID getGameId() {
        return gameId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
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
}
