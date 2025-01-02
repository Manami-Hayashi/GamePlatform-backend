package be.kdg.prog6.gameStatisticsContext.adapter.out;

import be.kdg.prog6.gameStatisticsContext.domain.*;
import be.kdg.prog6.gameStatisticsContext.port.out.CreateMatchSessionPort;
import be.kdg.prog6.gameStatisticsContext.port.out.LoadMatchSessionPort;
import be.kdg.prog6.gameStatisticsContext.port.out.LoadMatchSessionsPort;
import be.kdg.prog6.gameStatisticsContext.port.out.UpdateMatchSessionPort;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class MatchSessionDbAdapter implements LoadMatchSessionsPort, CreateMatchSessionPort, LoadMatchSessionPort, UpdateMatchSessionPort {
    private static final Logger log = LoggerFactory.getLogger(MatchSessionDbAdapter.class);
    private final MatchSessionRepository matchSessionRepo;
    private final GameStatisticsRepository gameStatisticsRepo;

    public MatchSessionDbAdapter(MatchSessionRepository matchSessionRepo, GameStatisticsRepository gameStatisticsRepo) {
        this.matchSessionRepo = matchSessionRepo;
        this.gameStatisticsRepo = gameStatisticsRepo;
    }

    @Transactional
    @Override
    public List<MatchSession> loadMatchSessionsByGameStatistics(GameStatistics gameStatistics) {
        Optional<GameStatisticsJpaEntity> gameStatisticsJpaEntityOpt = gameStatisticsRepo.findByPlayerIdAndGameId(
                gameStatistics.getPlayerId().id(), gameStatistics.getGameId().id());
        if (gameStatisticsJpaEntityOpt.isEmpty()) {
            throw new IllegalArgumentException("GameStatistics not found");
        }
        GameStatisticsJpaEntity gameStatisticsJpaEntity = gameStatisticsJpaEntityOpt.get();
        List<MatchSessionJpaEntity> matchSessionJpaEntities = matchSessionRepo.findAllByGameStatisticsIn(List.of(gameStatisticsJpaEntity));
        return matchSessionJpaEntities.stream().map(this::toMatchSession).toList();
    }

    @Transactional
    @Override
    public Optional<MatchSession> loadMatchSessionById(UUID sessionId) {
        Optional<MatchSessionJpaEntity> matchSessionJpaEntity = matchSessionRepo.findById(sessionId);
        return matchSessionJpaEntity.map(this::toMatchSession);
    }


    @Transactional
    @Override
    public void createMatchSession(MatchSession matchSession) {
        // Check if gameStatistics is null or empty, and initialize it if necessary
        List<GameStatistics> gameStatisticsList = matchSession.getGameStatistics();

        if (gameStatisticsList == null || gameStatisticsList.isEmpty()) {
            throw new IllegalArgumentException("Game statistics cannot be null or empty");
        }

        List<GameStatisticsJpaEntity> gameStatisticsJpaEntities = gameStatisticsList.stream()
                .map(gameStatistics -> {
                    Optional<GameStatisticsJpaEntity> gameStatisticsJpaEntityOpt = gameStatisticsRepo.findByPlayerIdAndGameId(
                            gameStatistics.getPlayerId().id(), gameStatistics.getGameId().id());
                    if (gameStatisticsJpaEntityOpt.isEmpty()) {
                        // Create and save new GameStatisticsJpaEntity if not found
                        GameStatisticsJpaEntity newEntity = new GameStatisticsJpaEntity(
                                gameStatistics.getPlayerId().id(),
                                gameStatistics.getGameId().id(),
                                gameStatistics.getTotalScore(),
                                gameStatistics.getTotalGamesPlayed(),
                                gameStatistics.getWins(),
                                gameStatistics.getLosses(),
                                gameStatistics.getDraws(),
                                gameStatistics.getWinLossRatio(),
                                gameStatistics.getTotalTimePlayed(),
                                gameStatistics.getHighestScore(),
                                gameStatistics.getMovesMade(),
                                gameStatistics.getAverageGameDuration()
                        );
                        gameStatisticsRepo.save(newEntity);
                        return newEntity;
                    }
                    return gameStatisticsJpaEntityOpt.get();
                })
                .collect(Collectors.toList());

        MatchSessionJpaEntity matchSessionJpaEntity = new MatchSessionJpaEntity(
                matchSession.getId(),
                matchSession.getGameId().id(),
                gameStatisticsJpaEntities,
                matchSession.getStartTime(),
                matchSession.getEndTime(),
                matchSession.isActive(),
                matchSession.getWinner().name(),
                matchSession.getScoreP1(),
                matchSession.getScoreP2(),
                matchSession.getMovesMadeP1(),
                matchSession.getMovesMadeP2()
        );
        log.info("Saving match session to database with id: {}", matchSession.getId());
        matchSessionRepo.save(matchSessionJpaEntity);
    }

    private MatchSession toMatchSession(MatchSessionJpaEntity matchSessionJpaEntity) {
        List<GameStatisticsJpaEntity> gameStatisticsJpaEntities = List.copyOf(matchSessionJpaEntity.getGameStatistics());
        List<GameStatistics> gameStatistics = gameStatisticsJpaEntities.stream().map(this::toGameStatistics).toList();
        return new MatchSession(
                matchSessionJpaEntity.getId(),
                new GameId(matchSessionJpaEntity.getGameId()),
                gameStatistics,
                matchSessionJpaEntity.getStartTime(),
                matchSessionJpaEntity.getEndTime(),
                matchSessionJpaEntity.isActive(),
                Winner.valueOf(matchSessionJpaEntity.getWinner()),
                matchSessionJpaEntity.getScoreP1(),
                matchSessionJpaEntity.getScoreP2(),
                matchSessionJpaEntity.getMovesMadeP1(),
                matchSessionJpaEntity.getMovesMadeP2()
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

    @Override
    public void updateMatchSession(MatchSession matchSession) {
        Optional<MatchSessionJpaEntity> matchSessionJpaEntityOpt = matchSessionRepo.findById(matchSession.getId());
        if (matchSessionJpaEntityOpt.isEmpty()) {
            throw new IllegalArgumentException("MatchSession not found");
        }
        MatchSessionJpaEntity matchSessionJpaEntity = matchSessionJpaEntityOpt.get();
        matchSessionJpaEntity.setGameId(matchSession.getGameId().id());
        matchSessionJpaEntity.setGameStatistics(matchSession.getGameStatistics().stream()
                .map(gameStatistics -> {
                    Optional<GameStatisticsJpaEntity> gameStatisticsJpaEntityOpt = gameStatisticsRepo.findByPlayerIdAndGameId(
                            gameStatistics.getPlayerId().id(), gameStatistics.getGameId().id());
                    if (gameStatisticsJpaEntityOpt.isEmpty()) {
                        throw new IllegalArgumentException("GameStatistics not found");
                    }
                    return gameStatisticsJpaEntityOpt.get();
                })
                .collect(Collectors.toList()));
        matchSessionJpaEntity.setStartTime(matchSession.getStartTime());
        matchSessionJpaEntity.setEndTime(matchSession.getEndTime());
        matchSessionJpaEntity.setActive(matchSession.isActive());
        matchSessionJpaEntity.setWinner(matchSession.getWinner().name());
        matchSessionJpaEntity.setScoreP1(matchSession.getScoreP1());
        matchSessionJpaEntity.setScoreP2(matchSession.getScoreP2());
        matchSessionJpaEntity.setMovesMadeP1(matchSession.getMovesMadeP1());
        matchSessionJpaEntity.setMovesMadeP2(matchSession.getMovesMadeP2());
        matchSessionRepo.save(matchSessionJpaEntity);
    }
}
