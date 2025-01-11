package be.kdg.prog6.gameStatisticsContext.adapter.in;

import java.util.UUID;

public record LeaderboardDto(UUID playerId, int wins, int totalGamesPlayed) {
}
