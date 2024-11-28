package be.kdg.prog6.gameStatisticsContext.adapter.out;

import be.kdg.prog6.gameStatisticsContext.domain.*;
import be.kdg.prog6.gameStatisticsContext.port.out.CreateMatchSessionPort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class MatchSessionDbAdapter implements CreateMatchSessionPort {
    private final MatchSessionRepository matchSessionRepo;
    private final GameStatisticsDbAdapter gameStatisticsDbAdapter;

    public MatchSessionDbAdapter(MatchSessionRepository matchSessionRepo, GameStatisticsDbAdapter gameStatisticsDbAdapter) {
        this.matchSessionRepo = matchSessionRepo;
        this.gameStatisticsDbAdapter = gameStatisticsDbAdapter;
    }

    @Transactional
    @Override
    public void createMatchSession(MatchSession matchSession) {
        List<GameStatisticsJpaEntity> gameStatisticsJpaEntities = List.copyOf(matchSession.getGameStatistics().stream().map(gameStatistics -> new GameStatisticsJpaEntity(gameStatistics.getPlayerId().id(), gameStatistics.getGameId().id(), gameStatistics.getTotalScore(), gameStatistics.getTotalGamesPlayed(), gameStatistics.getWins(), gameStatistics.getLosses(), gameStatistics.getDraws(), gameStatistics.getWinLossRatio(), gameStatistics.getTotalTimePlayed(), gameStatistics.getHighestScore(), gameStatistics.getMovesMade(), gameStatistics.getAverageGameDuration())).toList());
        Set<GameStatisticsJpaEntity> gameStatisticsJpaEntitySet = new HashSet<>(gameStatisticsJpaEntities);
        MatchSessionJpaEntity matchSessionJpaEntity = new MatchSessionJpaEntity(matchSession.getId(), matchSession.getGameId().id(), gameStatisticsJpaEntitySet, matchSession.getStartTime(), matchSession.getEndTime(), matchSession.isActive(), matchSession.getWinner().name(), matchSession.getScore(), matchSession.getMovesMade());
        matchSessionRepo.save(matchSessionJpaEntity);
    }

    public MatchSession toMatchSession(MatchSessionJpaEntity matchSessionJpaEntity) {
        List<GameStatisticsJpaEntity> gameStatisticsJpaEntities = List.copyOf(matchSessionJpaEntity.getGameStatistics());
        gameStatisticsDbAdapter.loadGameStatisticsByPlayerIdAndGameId(gameStatisticsJpaEntities.get(0).getPlayerId(), matchSessionJpaEntity.getGameId());
        List<GameStatistics> gameStatistics = gameStatisticsJpaEntities.stream().map(this::toGameStatistics).toList();
        return new MatchSession(matchSessionJpaEntity.getId(), new GameId(matchSessionJpaEntity.getGameId()), gameStatistics, matchSessionJpaEntity.getStartTime(), matchSessionJpaEntity.getEndTime(), matchSessionJpaEntity.isActive(), Winner.valueOf(matchSessionJpaEntity.getWinner()), matchSessionJpaEntity.getScore(), matchSessionJpaEntity.getMovesMade());
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
