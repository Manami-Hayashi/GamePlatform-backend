package be.kdg.prog6.gameStatisticsContext.port.in;

public record GetLeaderboardCommand(String gameName, String playerName, int wins, int totalGamesPlayed) {
}
