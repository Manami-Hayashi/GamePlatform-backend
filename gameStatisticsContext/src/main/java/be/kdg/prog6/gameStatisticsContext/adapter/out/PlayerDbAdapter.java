package be.kdg.prog6.gameStatisticsContext.adapter.out;

import be.kdg.prog6.gameStatisticsContext.domain.*;
import be.kdg.prog6.gameStatisticsContext.port.out.LoadPlayerPort;
import be.kdg.prog6.gameStatisticsContext.port.out.LoadPlayersPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class PlayerDbAdapter implements LoadPlayerPort, LoadPlayersPort {
    private final PlayerRepository playerRepository;

    public PlayerDbAdapter(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Optional<Player> loadPlayerById(UUID playerId) {
        return playerRepository.findById(playerId)
                .map(this::toPlayer);
    }

    @Override
    public List<Player> loadPlayers() {
        return playerRepository.findAll()
                .stream()
                .map(this::toPlayer)
                .collect(Collectors.toList());
    }

    private Player toPlayer(StatsPlayerJpaEntity playerEntity) {
        List<GameStatistics> gameStatistics = playerEntity.getGameStatistics()
                .stream()
                .map(this::toGameStatistics)
                .collect(Collectors.toList());

        return new Player(
                new PlayerId(playerEntity.getId()),
                playerEntity.getName(),
                playerEntity.getAge(),
                Gender.valueOf(playerEntity.getGender()),
                playerEntity.getLocation(),
                gameStatistics
        );
    }

    private GameStatistics toGameStatistics(GameStatisticsJpaEntity gameStatsEntity) {
        return new GameStatistics(
                new PlayerId(gameStatsEntity.getPlayerId()),
                new GameId(gameStatsEntity.getGameId()),
                gameStatsEntity.getTotalScore(),
                gameStatsEntity.getTotalGamesPlayed(),
                gameStatsEntity.getWins(),
                gameStatsEntity.getLosses(),
                gameStatsEntity.getDraws(),
                gameStatsEntity.getWinLossRatio(),
                gameStatsEntity.getTotalTimePlayed(),
                gameStatsEntity.getHighestScore(),
                gameStatsEntity.getMovesMade(),
                gameStatsEntity.getAverageGameDuration()
        );
    }
}
