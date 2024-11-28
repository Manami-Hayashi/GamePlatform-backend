package be.kdg.prog6.gameStatisticsContext.core;

import be.kdg.prog6.gameStatisticsContext.adapter.in.MatchSessionDto;
import be.kdg.prog6.gameStatisticsContext.domain.*;
import be.kdg.prog6.gameStatisticsContext.port.in.UpdateGameStatisticsUseCase;
import be.kdg.prog6.gameStatisticsContext.port.out.CreateMatchSessionPort;
import be.kdg.prog6.gameStatisticsContext.port.out.LoadGameStatisticsPort;
import be.kdg.prog6.gameStatisticsContext.port.out.UpdateGameStatisticsPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UpdateGameStatisticsUseCaseImpl implements UpdateGameStatisticsUseCase {
    private final static Logger LOGGER = LoggerFactory.getLogger(UpdateGameStatisticsUseCaseImpl.class.getName());
    private final LoadGameStatisticsPort loadGameStatisticsPort;
    private final UpdateGameStatisticsPort updateGameStatisticsPort;
    private final CreateMatchSessionPort createMatchSessionPort;

    public UpdateGameStatisticsUseCaseImpl(LoadGameStatisticsPort loadGameStatisticsPort, UpdateGameStatisticsPort updateGameStatisticsPort, CreateMatchSessionPort createMatchSessionPort) {
        this.loadGameStatisticsPort = loadGameStatisticsPort;
        this.updateGameStatisticsPort = updateGameStatisticsPort;
        this.createMatchSessionPort = createMatchSessionPort;
    }

    @Override
    public void updateGameStatistics(MatchSessionDto matchSessionDto) {
        Winner winner = Winner.valueOf(matchSessionDto.winner());
        Optional<GameStatistics> optionalGameStatsP1 = loadGameStatisticsPort.loadGameStatisticsByPlayerIdAndGameId(matchSessionDto.players().get(0), matchSessionDto.gameId());
        if (optionalGameStatsP1.isEmpty()) {
            throw new IllegalArgumentException("GameStatistics not found");
        }
        GameStatistics gameStatsP1 = optionalGameStatsP1.get();
        Optional<GameStatistics> optionalGameStatsP2 = loadGameStatisticsPort.loadGameStatisticsByPlayerIdAndGameId(matchSessionDto.players().get(1), matchSessionDto.gameId());
        if (optionalGameStatsP2.isEmpty()) {
            throw new IllegalArgumentException("GameStatistics not found");
        }
        GameStatistics gameStatsP2 = optionalGameStatsP2.get();

        MatchSession matchSession = new MatchSession(matchSessionDto.id(), new GameId(matchSessionDto.gameId()), List.of(gameStatsP1, gameStatsP2), matchSessionDto.startTime(), matchSessionDto.endTime(), matchSessionDto.isActive(), winner, matchSessionDto.score(), matchSessionDto.movesMade());
        createMatchSessionPort.createMatchSession(matchSession);

        gameStatsP1.setTotalScore(gameStatsP1.getTotalScore() + matchSessionDto.score());
        gameStatsP2.setTotalScore(gameStatsP2.getTotalScore() + matchSessionDto.score());

        gameStatsP1.setTotalGamesPlayed(gameStatsP1.getTotalGamesPlayed() + 1);
        gameStatsP2.setTotalGamesPlayed(gameStatsP2.getTotalGamesPlayed() + 1);

        if (winner == Winner.PLAYER1) {
            gameStatsP1.setWins(gameStatsP1.getWins() + 1);
            gameStatsP2.setLosses(gameStatsP2.getLosses() + 1);
        } else if (winner == Winner.PLAYER2) {
            gameStatsP1.setLosses(gameStatsP1.getLosses() + 1);
            gameStatsP2.setWins(gameStatsP2.getWins() + 1);
        } else {
            gameStatsP1.setDraws(gameStatsP1.getDraws() + 1);
            gameStatsP2.setDraws(gameStatsP2.getDraws() + 1);
        }
        LOGGER.info("Winner: {}", winner);

        gameStatsP1.setWinLossRatio(gameStatsP1.getLosses() == 0 ? 0 : (double) gameStatsP1.getWins() / gameStatsP1.getLosses());
        gameStatsP2.setWinLossRatio(gameStatsP2.getLosses() == 0 ? 0 : (double) gameStatsP2.getWins() / gameStatsP2.getLosses());
        gameStatsP1.setTotalTimePlayed(gameStatsP1.getTotalTimePlayed() + matchSessionDto.endTime().getSecond() - matchSessionDto.startTime().getSecond());
        gameStatsP2.setTotalTimePlayed(gameStatsP2.getTotalTimePlayed() + matchSessionDto.endTime().getSecond() - matchSessionDto.startTime().getSecond());

        gameStatsP1.setHighestScore(Math.max(gameStatsP1.getHighestScore(), matchSessionDto.score()));
        gameStatsP2.setHighestScore(Math.max(gameStatsP2.getHighestScore(), matchSessionDto.score()));

        gameStatsP1.setMovesMade(gameStatsP1.getMovesMade() + matchSessionDto.movesMade());
        gameStatsP2.setMovesMade(gameStatsP2.getMovesMade() + matchSessionDto.movesMade());

        gameStatsP1.setAverageGameDuration(gameStatsP1.getTotalTimePlayed() / gameStatsP1.getTotalGamesPlayed());
        gameStatsP2.setAverageGameDuration(gameStatsP2.getTotalTimePlayed() / gameStatsP2.getTotalGamesPlayed());

        updateGameStatisticsPort.updateGameStatistics(gameStatsP1);
        updateGameStatisticsPort.updateGameStatistics(gameStatsP2);
    }
}
