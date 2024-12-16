package be.kdg.prog6.gameStatisticsContext.adapter.in;

import java.util.UUID;

public record GameStatisticsDto(
        UUID gameId,
        int totalScore,
        int totalGamesPlayed,
        int wins,
        int losses,
        int draws,
        double winLossRatio,
        double totalTimePlayed,
        int highestScore,
        int movesMade,
        double averageGameDuration
) {
    public GameStatisticsDto {
        if (gameId == null) {
            throw new IllegalArgumentException("GameId must not be null");
        }
        if (totalScore < 0) {
            throw new IllegalArgumentException("TotalScore must be positive");
        }
        if (totalGamesPlayed < 0) {
            throw new IllegalArgumentException("TotalGamesPlayed must be positive");
        }
        if (wins < 0) {
            throw new IllegalArgumentException("Wins must be positive");
        }
        if (losses < 0) {
            throw new IllegalArgumentException("Losses must be positive");
        }
        if (draws < 0) {
            throw new IllegalArgumentException("Draws must be positive");
        }
        if (winLossRatio < 0) {
            throw new IllegalArgumentException("WinLossRatio must be positive");
        }
        if (totalTimePlayed < 0) {
            throw new IllegalArgumentException("TotalTimePlayed must be positive");
        }
        if (highestScore < 0) {
            throw new IllegalArgumentException("HighestScore must be positive");
        }
        if (movesMade < 0) {
            throw new IllegalArgumentException("MovesMade must be positive");
        }
        if (averageGameDuration < 0) {
            throw new IllegalArgumentException("AverageGameDuration must be positive");
        }
    }
}
