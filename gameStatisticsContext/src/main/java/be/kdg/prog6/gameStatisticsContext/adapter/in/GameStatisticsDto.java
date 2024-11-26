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
        long totalTimePlayed,
        int highestScore,
        int movesMade,
        double averageGameDuration
) {
}
