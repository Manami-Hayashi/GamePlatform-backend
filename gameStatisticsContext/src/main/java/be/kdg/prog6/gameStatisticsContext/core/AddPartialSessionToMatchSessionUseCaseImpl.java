package be.kdg.prog6.gameStatisticsContext.core;

import be.kdg.prog6.gameStatisticsContext.domain.*;
import be.kdg.prog6.gameStatisticsContext.port.in.AddPartialSessionCommand;
import be.kdg.prog6.gameStatisticsContext.port.in.AddPartialSessionToMatchSessionUseCase;
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
public class AddPartialSessionToMatchSessionUseCaseImpl implements AddPartialSessionToMatchSessionUseCase {
    private static final Logger logger = LoggerFactory.getLogger(AddPartialSessionToMatchSessionUseCaseImpl.class);
    private final CreateMatchSessionPort createMatchSessionPort;
    private final LoadGameStatisticsPort loadGameStatisticsPort;
    private final UpdateGameStatisticsPort updateGameStatisticsPort;

    public AddPartialSessionToMatchSessionUseCaseImpl(CreateMatchSessionPort createMatchSessionPort, LoadGameStatisticsPort loadGameStatisticsPort, UpdateGameStatisticsPort updateGameStatisticsPort) {
        this.createMatchSessionPort = createMatchSessionPort;
        this.loadGameStatisticsPort = loadGameStatisticsPort;
        this.updateGameStatisticsPort = updateGameStatisticsPort;
    }

    @Override
    public void addPartialSession(AddPartialSessionCommand command) {
        Optional<GameStatistics> gameStatisticsOpt1 = loadGameStatisticsPort.loadGameStatisticsByPlayerIdAndGameId(command.playerId1(), command.gameId());
        Optional<GameStatistics> gameStatisticsOpt2 = loadGameStatisticsPort.loadGameStatisticsByPlayerIdAndGameId(command.playerId2(), command.gameId());

        GameStatistics gameStatistics1 = gameStatisticsOpt1.orElseGet(() -> createNewGameStatistics(command.playerId1(), command.gameId()));
        GameStatistics gameStatistics2 = gameStatisticsOpt2.orElseGet(() -> createNewGameStatistics(command.playerId2(), command.gameId()));

        MatchSession matchSession = new MatchSession(
                command.sessionId(),
                new GameId(command.gameId()),
                List.of(gameStatistics1, gameStatistics2),
                command.startTime(),
                null,
                command.isActive(),
                Winner.NOT_FINISHED
        );

        createMatchSessionPort.createMatchSession(matchSession);
        updateGameStatisticsPort.updateGameStatistics(gameStatistics1);
        updateGameStatisticsPort.updateGameStatistics(gameStatistics2);

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