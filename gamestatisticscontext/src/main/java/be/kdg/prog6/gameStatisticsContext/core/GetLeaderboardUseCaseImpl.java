package be.kdg.prog6.gameStatisticsContext.core;

import be.kdg.prog6.gameStatisticsContext.domain.GameStatistics;
import be.kdg.prog6.gameStatisticsContext.domain.Player;
import be.kdg.prog6.gameStatisticsContext.port.in.GetLeaderboardCommand;
import be.kdg.prog6.gameStatisticsContext.port.in.GetLeaderboardUseCase;
import be.kdg.prog6.gameStatisticsContext.port.out.LoadGameStatisticsPort;
import be.kdg.prog6.gameStatisticsContext.port.out.LoadPlayersPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class GetLeaderboardUseCaseImpl implements GetLeaderboardUseCase {
    private final static Logger LOGGER = LoggerFactory.getLogger(GetLeaderboardUseCaseImpl.class);
    private final LoadGameStatisticsPort loadGameStatisticsPort;
    private final LoadPlayersPort loadPlayersPort;

    public GetLeaderboardUseCaseImpl(LoadGameStatisticsPort loadGameStatisticsPort, LoadPlayersPort loadPlayersPort) {
        this.loadGameStatisticsPort = loadGameStatisticsPort;
        this.loadPlayersPort = loadPlayersPort;
    }

    @Override
    public List<GetLeaderboardCommand> getLeaderboard(UUID gameId) {
        List<Player> players = loadPlayersPort.loadPlayers();
        List<GameStatistics> gameStatistics = new ArrayList<>();
        for (Player player : players) {
            gameStatistics.add(loadGameStatisticsPort.loadGameStatisticsByPlayerIdAndGameId(player.getId().id(), gameId).orElse(null));
        }
        List<GetLeaderboardCommand> leaderboard = new ArrayList<>();
        for (GameStatistics gameStatistic : gameStatistics) {
            leaderboard.add(new GetLeaderboardCommand(gameStatistic.getPlayerId().id(), gameStatistic.getWins(), gameStatistic.getTotalGamesPlayed()));
        }
        return leaderboard;
    }
}
