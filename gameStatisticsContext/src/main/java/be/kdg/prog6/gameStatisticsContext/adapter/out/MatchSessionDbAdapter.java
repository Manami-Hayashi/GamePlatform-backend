package be.kdg.prog6.gameStatisticsContext.adapter.out;

import be.kdg.prog6.gameStatisticsContext.domain.*;
import be.kdg.prog6.gameStatisticsContext.port.out.CreateMatchSessionPort;
import be.kdg.prog6.gameStatisticsContext.port.out.LoadMatchSessionsPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class MatchSessionDbAdapter implements LoadMatchSessionsPort, CreateMatchSessionPort {
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
    public void createMatchSession(MatchSession matchSession) {
        List<GameStatisticsJpaEntity> gameStatisticsJpaEntities = matchSession.getGameStatistics().stream()
                .map(gameStatistics -> {
                    Optional<GameStatisticsJpaEntity> gameStatisticsJpaEntityOpt = gameStatisticsRepo.findByPlayerIdAndGameId(
                            gameStatistics.getPlayerId().id(), gameStatistics.getGameId().id());
                    if (gameStatisticsJpaEntityOpt.isEmpty()) {
                        throw new IllegalArgumentException("GameStatistics not found");
                    }
                    return gameStatisticsJpaEntityOpt.get();
                })
                .collect(Collectors.toList());

        MatchSessionJpaEntity matchSessionJpaEntity = new MatchSessionJpaEntity(
                matchSession.getGameId().id(),
                gameStatisticsJpaEntities,
                matchSession.getStartTime(),
                matchSession.getEndTime(),
                matchSession.isActive(),
                matchSession.getWinner().name(),
                matchSession.getScore(),
                matchSession.getMovesMade()
        );
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
                matchSessionJpaEntity.getScore(),
                matchSessionJpaEntity.getMovesMade()
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
