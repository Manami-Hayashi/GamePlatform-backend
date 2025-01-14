package be.kdg.prog6.gameStatisticsContext.adapter.in;

public record LeaderboardDto(String gameName, String playerName, int wins, int totalGamesPlayed) {
}
