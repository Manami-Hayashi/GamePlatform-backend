package be.kdg.prog6.gameStatisticsContext.adapter.out;

import be.kdg.prog6.gameStatisticsContext.domain.*;
import be.kdg.prog6.gameStatisticsContext.port.out.LoadGameStatisticsPort;
import be.kdg.prog6.gameStatisticsContext.port.out.UpdateGameStatisticsPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class GameStatisticsDbAdapter implements LoadGameStatisticsPort, UpdateGameStatisticsPort {
    private final GameStatisticsRepository gameStatisticsRepository;

    public GameStatisticsDbAdapter(GameStatisticsRepository gameStatisticsRepository) {
        this.gameStatisticsRepository = gameStatisticsRepository;
    }

    @Override
    public Optional<GameStatistics> loadGameStatisticsByPlayerIdAndGameId(UUID playerId, UUID gameId) {
        return gameStatisticsRepository.findByPlayerIdAndGameId(playerId, gameId).map(this::toGameStatistics);
    }

    @Override
    public void updateGameStatistics(GameStatistics gameStatistics) {
        GameStatisticsJpaEntity gameStatisticsJpaEntity = gameStatisticsRepository.findByPlayerIdAndGameId(gameStatistics.getPlayerId().id(), gameStatistics.getGameId().id())
                        .orElseThrow(() -> new IllegalArgumentException("Game statistics not found"));
        gameStatisticsRepository.save(gameStatisticsJpaEntity);
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

    private Player toPlayer(StatsPlayerJpaEntity playerEntity) {
        List<GameStatistics> gameStatistics = playerEntity.getGameStatistics()
                .stream()
                .map(this::toGameStatistics)
                .toList();

        return new Player(
                new PlayerId(playerEntity.getId()),
                playerEntity.getName(),
                playerEntity.getAge(),
                Gender.valueOf(playerEntity.getGender()),
                playerEntity.getLocation());
    }
}
