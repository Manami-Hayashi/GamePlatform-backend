package be.kdg.prog6.gameStatisticsContext.adapter.in;

import java.util.UUID;

public record GameStatisticsDto(UUID gameId, int totalScore, int matchesPlayed) {
}
