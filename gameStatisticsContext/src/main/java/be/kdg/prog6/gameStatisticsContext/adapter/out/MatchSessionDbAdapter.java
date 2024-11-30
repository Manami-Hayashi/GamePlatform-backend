package be.kdg.prog6.gameStatisticsContext.adapter.out;

import be.kdg.prog6.gameStatisticsContext.domain.*;
import be.kdg.prog6.gameStatisticsContext.port.out.CreateMatchSessionPort;
import be.kdg.prog6.gameStatisticsContext.port.out.LoadMatchSessionsPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class MatchSessionDbAdapter implements LoadMatchSessionsPort, CreateMatchSessionPort {
    private final MatchSessionRepository matchSessionRepo;
    private final GameStatisticsRepository gameStatisticsRepo;
    private final GameStatisticsDbAdapter gameStatisticsDbAdapter;

    public MatchSessionDbAdapter(MatchSessionRepository matchSessionRepo, GameStatisticsRepository gameStatisticsRepo, GameStatisticsDbAdapter gameStatisticsDbAdapter) {
        this.matchSessionRepo = matchSessionRepo;
        this.gameStatisticsRepo = gameStatisticsRepo;
        this.gameStatisticsDbAdapter = gameStatisticsDbAdapter;
    }

    @Override
    public List<MatchSession> loadMatchSessionsByGameStatistics(GameStatistics gameStatistics) {
        List<GameStatisticsJpaEntity> gameStatisticsJpaEntities = List.of(new GameStatisticsJpaEntity(
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
        ));
        //gameStatisticsRepo.save(gameStatisticsJpaEntities.iterator().next());
        List<MatchSessionJpaEntity> matchSessionJpaEntities = matchSessionRepo.findAllByGameStatisticsIn(gameStatisticsJpaEntities);
        return matchSessionJpaEntities.stream().map(this::toMatchSession).toList();
    }

    @Transactional
    @Override
    public void createMatchSession(MatchSession matchSession) {
        List<GameStatisticsJpaEntity> gameStatisticsJpaEntities = matchSession.getGameStatistics().stream()
                .map(gameStatistics -> {
                    GameStatisticsJpaEntity gameStatisticsJpaEntity = new GameStatisticsJpaEntity(
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
                    return gameStatisticsRepo.save(gameStatisticsJpaEntity);
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
