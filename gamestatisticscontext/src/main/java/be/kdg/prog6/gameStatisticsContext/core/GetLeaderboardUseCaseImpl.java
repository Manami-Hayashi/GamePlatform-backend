package be.kdg.prog6.gameStatisticsContext.core;

import be.kdg.prog6.gameStatisticsContext.domain.GameId;
import be.kdg.prog6.gameStatisticsContext.domain.GameStatistics;
import be.kdg.prog6.gameStatisticsContext.domain.Player;
import be.kdg.prog6.gameStatisticsContext.port.in.GetLeaderboardCommand;
import be.kdg.prog6.gameStatisticsContext.port.in.GetLeaderboardUseCase;
import be.kdg.prog6.gameStatisticsContext.port.out.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GetLeaderboardUseCaseImpl implements GetLeaderboardUseCase {
    private final static Logger LOGGER = LoggerFactory.getLogger(GetLeaderboardUseCaseImpl.class);
    private final LoadGamesPort loadGamesPort;
    private final LoadGameStatisticsPort loadGameStatisticsPort;
    private final LoadPlayersPort loadPlayersPort;
    private final LoadPlayerPort loadPlayerPort;
    private final LoadGamePort loadGamePort;

    public GetLeaderboardUseCaseImpl(LoadGamesPort loadGamesPort, LoadGameStatisticsPort loadGameStatisticsPort, LoadPlayersPort loadPlayersPort, LoadPlayerPort loadPlayerPort, LoadGamePort loadGamePort) {
        this.loadGamesPort = loadGamesPort;
        this.loadGameStatisticsPort = loadGameStatisticsPort;
        this.loadPlayersPort = loadPlayersPort;
        this.loadPlayerPort = loadPlayerPort;
        this.loadGamePort = loadGamePort;
    }

    @Override
    public List<GetLeaderboardCommand> getLeaderboard() {
        List<GameId> games = loadGamesPort.loadGames();
        LOGGER.info("Loaded games: {}", games);
        List<Player> players = loadPlayersPort.loadPlayers();
        LOGGER.info("Loaded players: {}", players);
        List<GetLeaderboardCommand> leaderboards = new ArrayList<>();
        for (GameId gameId : games) {
            for (Player player : players) {
                Optional<GameStatistics> optionalGameStats = loadGameStatisticsPort.loadGameStatisticsByPlayerIdAndGameId(player.getId().id(), gameId.id());
                LOGGER.info("Loaded game statistics: {}", optionalGameStats);
                if (optionalGameStats.isPresent()) {
                    GameStatistics gameStats = optionalGameStats.get();
                    String gameName = loadGamePort.loadGameById(gameId.id()).get().getName();
                    String playerName = loadPlayerPort.loadPlayerById(gameStats.getPlayerId().id()).get().getName();
                    leaderboards.add(new GetLeaderboardCommand(gameName, playerName, gameStats.getWins(), gameStats.getTotalGamesPlayed()));
                }
            }
        }
        return leaderboards;
    }
}
