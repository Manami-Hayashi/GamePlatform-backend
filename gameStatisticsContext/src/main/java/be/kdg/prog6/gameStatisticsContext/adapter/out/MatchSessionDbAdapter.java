package be.kdg.prog6.gameStatisticsContext.adapter.out;

import be.kdg.prog6.gameStatisticsContext.domain.*;
import be.kdg.prog6.gameStatisticsContext.port.out.CreateMatchSessionPort;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class MatchSessionDbAdapter implements CreateMatchSessionPort {
    private final MatchSessionRepository matchSessionRepository;

    public MatchSessionDbAdapter(MatchSessionRepository matchSessionRepository) {
        this.matchSessionRepository = matchSessionRepository;
    }

    @Override
    public void createMatchSession(MatchSession matchSession) {
        Set<GameStatisticsJpaEntity> gameStatisticsJpaEntities = new HashSet<>();
        for (GameStatistics gameStatistics : matchSession.getGameStatistics()) {
            GameStatisticsJpaEntity gameStatisticsJpaEntity = new GameStatisticsJpaEntity(gameStatistics.getPlayerId().id(), gameStatistics.getGameId().id(), gameStatistics.getTotalScore(), gameStatistics.getTotalGamesPlayed(), gameStatistics.getWins(), gameStatistics.getLosses(), gameStatistics.getDraws(), gameStatistics.getWinLossRatio(), gameStatistics.getTotalTimePlayed(), gameStatistics.getHighestScore(), gameStatistics.getMovesMade(), gameStatistics.getAverageGameDuration());
            gameStatisticsJpaEntities.add(gameStatisticsJpaEntity);
        }
        MatchSessionJpaEntity matchSessionJpaEntity = new MatchSessionJpaEntity(matchSession.getId(), matchSession.getGameId().id(), gameStatisticsJpaEntities, matchSession.getStartTime(), matchSession.getEndTime(), matchSession.isActive(), matchSession.getWinner().name(), matchSession.getScore(), matchSession.getMovesMade());
        matchSessionRepository.save(matchSessionJpaEntity);
    }

    public MatchSession toMatchSession(MatchSessionJpaEntity matchSessionJpaEntity) {
        List<GameStatisticsJpaEntity> gameStatisticsJpaEntities = List.copyOf(matchSessionJpaEntity.getGameStatistics());
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
