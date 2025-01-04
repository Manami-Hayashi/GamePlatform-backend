package be.kdg.prog6.gameStatisticsContext.core;

import be.kdg.prog6.gameStatisticsContext.domain.*;
import be.kdg.prog6.gameStatisticsContext.port.in.AddPartialSessionCommand;
import be.kdg.prog6.gameStatisticsContext.port.in.AddPartialSessionUseCase;
import be.kdg.prog6.gameStatisticsContext.port.out.CreateGameStatisticsPort;
import be.kdg.prog6.gameStatisticsContext.port.out.CreateMatchSessionPort;
import be.kdg.prog6.gameStatisticsContext.port.out.LoadGameStatisticsPort;
import be.kdg.prog6.gameStatisticsContext.port.out.UpdateGameStatisticsPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AddPartialSessionUseCaseImpl implements AddPartialSessionUseCase {
    private static final Logger logger = LoggerFactory.getLogger(AddPartialSessionUseCaseImpl.class);
    private final CreateMatchSessionPort createMatchSessionPort;
    private final LoadGameStatisticsPort loadGameStatisticsPort;
    private final UpdateGameStatisticsPort updateGameStatisticsPort;
    private final CreateGameStatisticsPort createGameStatisticsPort;

    public AddPartialSessionUseCaseImpl(CreateMatchSessionPort createMatchSessionPort, LoadGameStatisticsPort loadGameStatisticsPort, UpdateGameStatisticsPort updateGameStatisticsPort, CreateGameStatisticsPort createGameStatisticsPort) {
        this.createMatchSessionPort = createMatchSessionPort;
        this.loadGameStatisticsPort = loadGameStatisticsPort;
        this.updateGameStatisticsPort = updateGameStatisticsPort;
        this.createGameStatisticsPort = createGameStatisticsPort;
    }

    @Override
    public void addPartialSession(AddPartialSessionCommand command) {
        Optional<GameStatistics> gameStatisticsOpt1 = loadGameStatisticsPort.loadGameStatisticsByPlayerIdAndGameId(command.playerId1(), command.gameId());
        Optional<GameStatistics> gameStatisticsOpt2 = loadGameStatisticsPort.loadGameStatisticsByPlayerIdAndGameId(command.playerId2(), command.gameId());

        Optional<GameStatistics> optionalGameStatsP1 = loadGameStatisticsPort.loadGameStatisticsByPlayerIdAndGameId(command.playerId1(), command.gameId());
        if (optionalGameStatsP1.isEmpty()) {
            GameStatistics newGameStatsP1 = new GameStatistics(
                    new PlayerId(command.playerId1()),
                    new GameId(command.gameId()),
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0
            );
            createGameStatisticsPort.createGameStatistics(newGameStatsP1);        }
        GameStatistics gameStatsP1 = loadGameStatisticsPort.loadGameStatisticsByPlayerIdAndGameId(command.playerId1(), command.gameId()).get();
        Optional<GameStatistics> optionalGameStatsP2 = loadGameStatisticsPort.loadGameStatisticsByPlayerIdAndGameId(command.playerId2(), command.gameId());
        if (optionalGameStatsP2.isEmpty()) {
            GameStatistics newGameStatsP2 = new GameStatistics(
                    new PlayerId(command.playerId2()),
                    new GameId(command.gameId()),
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0
            );
            createGameStatisticsPort.createGameStatistics(newGameStatsP2);        }
        GameStatistics gameStatsP2 = loadGameStatisticsPort.loadGameStatisticsByPlayerIdAndGameId(command.playerId2(), command.gameId()).get();

        MatchSession matchSession = new MatchSession(
                command.sessionId(),
                new GameId(command.gameId()),
                List.of(gameStatsP1, gameStatsP2),
                command.startTime(),
                null,
                command.isActive(),
                Winner.NOT_FINISHED
        );

        createMatchSessionPort.createMatchSession(matchSession);
        updateGameStatisticsPort.updateGameStatistics(gameStatsP1);
        updateGameStatisticsPort.updateGameStatistics(gameStatsP2);

        logger.info("Match session created and saved for session ID: {}", command.sessionId());
    }

    private GameStatistics createNewGameStatistics(UUID playerId, UUID gameId) {
        GameStatistics gameStatistics = new GameStatistics(
                new PlayerId(playerId),
                new GameId(gameId),
                0, 0, 0, 0, 0, 0.0, 0.0, 0, 0, 0.0
        );
        logger.info("Created new game statistics for player: {}", playerId);
        return gameStatistics;
    }
}
