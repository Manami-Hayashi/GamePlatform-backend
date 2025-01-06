package be.kdg.prog6.gameStatisticsContext.adapter.in;

import java.util.UUID;

public record AchievementDto(UUID playerId, UUID gameId, String name, String description, Boolean isLocked, int totalScore, int totalGamesPlayed, int wins, double totalTimePlayed) {
    public AchievementDto {
        if (playerId == null) {
            throw new IllegalArgumentException("PlayerId must not be null");
        }
        if (gameId == null) {
            throw new IllegalArgumentException("GameId must not be null");
        }
        if (name == null) {
            throw new IllegalArgumentException("Name must not be null");
        }
        if (description == null) {
            throw new IllegalArgumentException("Description must not be null");
        }
        if (isLocked == null) {
            isLocked = true;
        }
        if (totalScore < 0) {
            throw new IllegalArgumentException("TotalScore must not be negative");
        }
        if (totalGamesPlayed < 0) {
            throw new IllegalArgumentException("TotalGamesPlayed must not be negative");
        }
        if (wins < 0) {
            throw new IllegalArgumentException("Wins must not be negative");
        }
        if (totalTimePlayed < 0) {
            throw new IllegalArgumentException("TotalTimePlayed must not be negative");
        }
    }
}
