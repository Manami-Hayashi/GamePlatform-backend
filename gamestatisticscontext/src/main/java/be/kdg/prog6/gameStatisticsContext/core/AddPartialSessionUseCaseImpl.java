package be.kdg.prog6.gameStatisticsContext.core;

import be.kdg.prog6.gameStatisticsContext.domain.*;
import be.kdg.prog6.gameStatisticsContext.port.in.AddPartialSessionCommand;
import be.kdg.prog6.gameStatisticsContext.port.in.AddPartialSessionUseCase;
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

    public AddPartialSessionUseCaseImpl(CreateMatchSessionPort createMatchSessionPort, LoadGameStatisticsPort loadGameStatisticsPort, UpdateGameStatisticsPort updateGameStatisticsPort) {
        this.createMatchSessionPort = createMatchSessionPort;
        this.loadGameStatisticsPort = loadGameStatisticsPort;
        this.updateGameStatisticsPort = updateGameStatisticsPort;
    }

    @Override
    public void addPartialSession(AddPartialSessionCommand command) {
        Optional<GameStatistics> gameStatisticsOpt1 = loadGameStatisticsPort.loadGameStatisticsByPlayerIdAndGameId(command.playerId1(), command.gameId());
        Optional<GameStatistics> gameStatisticsOpt2 = loadGameStatisticsPort.loadGameStatisticsByPlayerIdAndGameId(command.playerId2(), command.gameId());

        Optional<GameStatistics> optionalGameStatsP1 = loadGameStatisticsPort.loadGameStatisticsByPlayerIdAndGameId(command.playerId1(), command.gameId());
        if (optionalGameStatsP1.isEmpty()) {
            throw new IllegalArgumentException("GameStatistics not found");
        }
        GameStatistics gameStatsP1 = optionalGameStatsP1.get();
        Optional<GameStatistics> optionalGameStatsP2 = loadGameStatisticsPort.loadGameStatisticsByPlayerIdAndGameId(command.playerId2(), command.gameId());
        if (optionalGameStatsP2.isEmpty()) {
            throw new IllegalArgumentException("GameStatistics not found");
        }
        GameStatistics gameStatsP2 = optionalGameStatsP2.get();

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
