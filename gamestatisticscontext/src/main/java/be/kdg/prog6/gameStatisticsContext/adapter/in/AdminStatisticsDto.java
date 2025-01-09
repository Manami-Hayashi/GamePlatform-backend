package be.kdg.prog6.gameStatisticsContext.adapter.in;

import java.util.UUID;

public record AdminStatisticsDto(
        UUID gameId,
        int totalScore,
        int totalGamesPlayed,
        double totalTimePlayed,
        int highestScore,
        int movesMade,
        double averageGameDuration,
        double averageScore,
        double averageMovesMade,
        double averageAge,
        String bestPlayer
        ) {
}
