package be.kdg.prog6.playerManagementContext.ports.in;

import java.util.UUID;

public record AddAchievementCommand(
        UUID playerId,
        UUID gameId,
        String name,
        String description,
        int totalScore,
        int totalGamesPlayed,
        int wins,
        double totalTimePlayed
) {
}
