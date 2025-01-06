package be.kdg.prog6.gameStatisticsContext.adapter.out;

import be.kdg.prog6.gameStatisticsContext.domain.*;
import be.kdg.prog6.gameStatisticsContext.port.out.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class GameStatisticsDbAdapter implements LoadGamesPort, LoadGameStatisticsPort, LoadGameStatisticsByGameIdPort, LoadAllGameStatisticsPort, UpdateGameStatisticsPort, CreateGameStatisticsPort {
    private static final Logger log = LoggerFactory.getLogger(GameStatisticsDbAdapter.class);
    private final GameStatisticsRepository gameStatisticsRepo;
    private final GameRepository gameRepository;

    public GameStatisticsDbAdapter(GameStatisticsRepository gameStatisticsRepo, GameRepository gameRepository) {
        this.gameStatisticsRepo = gameStatisticsRepo;
        this.gameRepository = gameRepository;
    }

    @Override
    public Optional<GameStatistics> loadGameStatisticsByPlayerIdAndGameId(UUID playerId, UUID gameId) {
        return gameStatisticsRepo.findByPlayerIdAndGameId(playerId, gameId).map(this::toGameStatistics);
    }

    @Override
    public List<GameStatistics> loadGameStatisticsByGameId(UUID gameId) {
        return gameStatisticsRepo.findByGameId(gameId)
                .stream()
                .map(this::toGameStatistics)
                .toList();
    }

    @Override
    public List<GameStatistics> loadAllGameStatistics() {
        return gameStatisticsRepo.findAll()
                .stream()
                .map(this::toGameStatistics)
                .toList();
    }

    @Override
    public void updateGameStatistics(GameStatistics gameStatistics) {
        // Check if the GameStatistics exists for the given player and game
        GameStatisticsJpaEntity gameStatisticsJpaEntity = gameStatisticsRepo
                .findByPlayerIdAndGameId(gameStatistics.getPlayerId().id(), gameStatistics.getGameId().id())
                .orElseGet(() -> {
                    // If not found, create a new GameStatisticsJpaEntity
                    GameStatisticsJpaEntity newEntity = new GameStatisticsJpaEntity();
                    newEntity.setPlayerId(gameStatistics.getPlayerId().id());
                    newEntity.setGameId(gameStatistics.getGameId().id());
                    return newEntity;
                });

        gameStatisticsJpaEntity.setTotalScore(gameStatistics.getTotalScore());
        gameStatisticsJpaEntity.setTotalGamesPlayed(gameStatistics.getTotalGamesPlayed());
        gameStatisticsJpaEntity.setWins(gameStatistics.getWins());
        gameStatisticsJpaEntity.setLosses(gameStatistics.getLosses());
        gameStatisticsJpaEntity.setDraws(gameStatistics.getDraws());
        gameStatisticsJpaEntity.setWinLossRatio(gameStatistics.getWinLossRatio());
        gameStatisticsJpaEntity.setTotalTimePlayed(gameStatistics.getTotalTimePlayed());
        gameStatisticsJpaEntity.setHighestScore(gameStatistics.getHighestScore());
        gameStatisticsJpaEntity.setMovesMade(gameStatistics.getMovesMade());
        gameStatisticsJpaEntity.setAverageGameDuration(gameStatistics.getAverageGameDuration());
        gameStatisticsRepo.save(gameStatisticsJpaEntity);
        log.info("GameStatistics updated for player {} and game {}", gameStatistics.getPlayerId(), gameStatistics.getGameId());
    }

    @Override
    public void createGameStatistics(GameStatistics gameStatistics) {
        GameStatisticsJpaEntity gameStatisticsJpaEntity = new GameStatisticsJpaEntity();
        gameStatisticsJpaEntity.setPlayerId(gameStatistics.getPlayerId().id());
        gameStatisticsJpaEntity.setGameId(gameStatistics.getGameId().id());
        gameStatisticsJpaEntity.setTotalScore(gameStatistics.getTotalScore());
        gameStatisticsJpaEntity.setTotalGamesPlayed(gameStatistics.getTotalGamesPlayed());
        gameStatisticsJpaEntity.setWins(gameStatistics.getWins());
        gameStatisticsJpaEntity.setLosses(gameStatistics.getLosses());
        gameStatisticsJpaEntity.setDraws(gameStatistics.getDraws());
        gameStatisticsJpaEntity.setWinLossRatio(gameStatistics.getWinLossRatio());
        gameStatisticsJpaEntity.setTotalTimePlayed(gameStatistics.getTotalTimePlayed());
        gameStatisticsJpaEntity.setHighestScore(gameStatistics.getHighestScore());
        gameStatisticsJpaEntity.setMovesMade(gameStatistics.getMovesMade());
        gameStatisticsJpaEntity.setAverageGameDuration(gameStatistics.getAverageGameDuration());
        gameStatisticsRepo.save(gameStatisticsJpaEntity);
        log.info("GameStatistics created for player {} and game {}", gameStatistics.getPlayerId(), gameStatistics.getGameId());
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
                LocalDate.parse(playerEntity.getBirthDate()),
                Gender.valueOf(playerEntity.getGender()),
                playerEntity.getLocation());
    }

    @Override
    public List<GameId> loadGames() {
        return gameRepository.findAll()
                .stream()
                .map(gameEntity -> new GameId(gameEntity.getId(), gameEntity.getName()))
                .toList();
    }
}
