package be.kdg.prog6.gameStatisticsContext.core;

import be.kdg.prog6.gameStatisticsContext.adapter.in.AdminStatisticsDto;
import be.kdg.prog6.gameStatisticsContext.domain.GameId;
import be.kdg.prog6.gameStatisticsContext.domain.GameStatistics;
import be.kdg.prog6.gameStatisticsContext.domain.Player;
import be.kdg.prog6.gameStatisticsContext.port.in.GetAdminStatisticsUseCase;
import be.kdg.prog6.gameStatisticsContext.port.out.LoadAllGameStatisticsPort;
import be.kdg.prog6.gameStatisticsContext.port.out.LoadGameStatisticsByGameIdPort;
import be.kdg.prog6.gameStatisticsContext.port.out.LoadPlayersPort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class GetAdminStatisticsUseCaseImpl implements GetAdminStatisticsUseCase {
    private final LoadPlayersPort loadPlayersPort;
    private final LoadGameStatisticsByGameIdPort loadGameStatisticsByGameIdPort;
    private final LoadAllGameStatisticsPort loadAllGameStatisticsPort;

    public GetAdminStatisticsUseCaseImpl(final LoadPlayersPort loadPlayersPort, LoadGameStatisticsByGameIdPort loadGameStatisticsByGameIdPort, LoadAllGameStatisticsPort loadAllGameStatisticsPort) {
        this.loadPlayersPort = loadPlayersPort;
        this.loadGameStatisticsByGameIdPort = loadGameStatisticsByGameIdPort;
        this.loadAllGameStatisticsPort = loadAllGameStatisticsPort;
    }

    @Override
    public List<AdminStatisticsDto> getAdminStatistics() {
        List<GameStatistics> allGameStatistics = loadAllGameStatisticsPort.loadAllGameStatistics();
        Set<GameId> gameIds = allGameStatistics.stream()
                .map(GameStatistics::getGameId)
                .collect(Collectors.toSet());
        List<AdminStatisticsDto> dtos = new ArrayList<>();
        for (GameId gameId : gameIds) {
            List<GameStatistics> gameStatistics = loadGameStatisticsByGameIdPort.loadGameStatisticsByGameId(gameId.id());
            int totalScore = gameStatistics.stream().mapToInt(GameStatistics::getTotalScore).sum();
            int totalGamesPlayed = gameStatistics.size();
            long totalTimePlayed = gameStatistics.stream().mapToLong(GameStatistics::getTotalTimePlayed).sum();
            int highestScore = gameStatistics.stream().mapToInt(GameStatistics::getHighestScore).max().orElse(0);
            int movesMade = gameStatistics.stream().mapToInt(GameStatistics::getMovesMade).sum();
            double averageGameDuration = gameStatistics.stream().mapToDouble(GameStatistics::getAverageGameDuration).average().orElse(0);
            double averageScore = gameStatistics.stream().mapToDouble(GameStatistics::getTotalScore).average().orElse(0);
            double averageMovesMade = gameStatistics.stream().mapToDouble(GameStatistics::getMovesMade).average().orElse(0);
            List<Player> players = loadPlayersPort.loadPlayers().stream().filter(player -> player.getGameStatistics().stream().anyMatch(gameStat -> gameStat.getGameId().equals(gameId))).toList();
            double averageAge = players.stream().mapToInt(Player::getAge).average().orElse(0);
            String mostCommonLocation = players.stream().collect(Collectors.groupingBy(Player::getLocation, Collectors.counting())).entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse("");
            String bestPlayer = players.stream().max(Player::compareTo).map(Player::getName).orElse("");
            AdminStatisticsDto newDto = new AdminStatisticsDto(gameId.id(), totalScore, totalGamesPlayed, totalTimePlayed, highestScore, movesMade, averageGameDuration, averageScore, averageMovesMade, averageAge, mostCommonLocation, bestPlayer);
            dtos.add(newDto);
        }
        return dtos;
    }
}
