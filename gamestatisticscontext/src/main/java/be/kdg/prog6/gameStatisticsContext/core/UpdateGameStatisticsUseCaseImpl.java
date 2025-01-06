package be.kdg.prog6.gameStatisticsContext.core;

import be.kdg.prog6.gameStatisticsContext.port.in.UpdateGameStatisticsCommand;
import be.kdg.prog6.gameStatisticsContext.domain.*;
import be.kdg.prog6.gameStatisticsContext.port.in.UpdateGameStatisticsUseCase;
import be.kdg.prog6.gameStatisticsContext.port.out.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class UpdateGameStatisticsUseCaseImpl implements UpdateGameStatisticsUseCase {
    private final static Logger LOGGER = LoggerFactory.getLogger(UpdateGameStatisticsUseCaseImpl.class.getName());
    private final LoadGameStatisticsPort loadGameStatisticsPort;
    private final UpdateGameStatisticsPort updateGameStatisticsPort;
    private final CreateMatchSessionPort createMatchSessionPort;
    private final LoadMatchSessionPort loadMatchSessionPort;
    private final UpdateMatchSessionPort updateMatchSessionPort;

    public UpdateGameStatisticsUseCaseImpl(LoadGameStatisticsPort loadGameStatisticsPort, UpdateGameStatisticsPort updateGameStatisticsPort, CreateMatchSessionPort createMatchSessionPort, LoadMatchSessionPort loadMatchSessionPort, UpdateMatchSessionPort updateMatchSessionPort) {
        this.loadGameStatisticsPort = loadGameStatisticsPort;
        this.updateGameStatisticsPort = updateGameStatisticsPort;
        this.createMatchSessionPort = createMatchSessionPort;
        this.loadMatchSessionPort = loadMatchSessionPort;
        this.updateMatchSessionPort = updateMatchSessionPort;
    }

    private void updatePlayerStatistics(GameStatistics gameStatsP1, GameStatistics gameStatsP2, Winner winnerEnum, String winnerString, UpdateGameStatisticsCommand command) {
        gameStatsP1.setTotalScore(gameStatsP1.getTotalScore() + command.scoreP1());
        gameStatsP2.setTotalScore(gameStatsP2.getTotalScore() + command.scoreP2());

        gameStatsP1.setTotalGamesPlayed(gameStatsP1.getTotalGamesPlayed() + 1);
        gameStatsP2.setTotalGamesPlayed(gameStatsP2.getTotalGamesPlayed() + 1);

        if (winnerEnum == Winner.PLAYER1 || winnerString.equals(gameStatsP1.getPlayerId().id().toString())) {
            gameStatsP1.setWins(gameStatsP1.getWins() + 1);
            gameStatsP2.setLosses(gameStatsP2.getLosses() + 1);
        } else if (winnerEnum == Winner.PLAYER2 || winnerString.equals(gameStatsP2.getPlayerId().id().toString())) {
            gameStatsP1.setLosses(gameStatsP1.getLosses() + 1);
            gameStatsP2.setWins(gameStatsP2.getWins() + 1);
        } else {
            gameStatsP1.setDraws(gameStatsP1.getDraws() + 1);
            gameStatsP2.setDraws(gameStatsP2.getDraws() + 1);
        }

        LOGGER.info("Winner: {}", winnerEnum);

        gameStatsP1.setWinLossRatio(gameStatsP1.getLosses() == 0 ? 1 : (double) gameStatsP1.getWins() / gameStatsP1.getLosses());
        gameStatsP2.setWinLossRatio(gameStatsP2.getLosses() == 0 ? 1 : (double) gameStatsP2.getWins() / gameStatsP2.getLosses());

        double gameDuration = calculateGameDuration(command.startTime(), command.endTime());
        gameStatsP1.setTotalTimePlayed(gameStatsP1.getTotalTimePlayed() + gameDuration);
        gameStatsP2.setTotalTimePlayed(gameStatsP2.getTotalTimePlayed() + gameDuration);

        // Log updated total time played
        LOGGER.info("Updated total time played for Player 1: {}, Player 2: {}", gameStatsP1.getTotalTimePlayed(), gameStatsP2.getTotalTimePlayed());

        gameStatsP1.setAverageGameDuration(gameStatsP1.getTotalGamesPlayed() == 0 ? 0 : gameStatsP1.getTotalTimePlayed() / gameStatsP1.getTotalGamesPlayed() * 60);
        gameStatsP2.setAverageGameDuration(gameStatsP2.getTotalGamesPlayed() == 0 ? 0 : gameStatsP2.getTotalTimePlayed() / gameStatsP2.getTotalGamesPlayed() * 60);

        double averageDuration = gameStatsP1.getTotalGamesPlayed() == 0 ? 0 : (gameStatsP1.getTotalTimePlayed() / gameStatsP1.getTotalGamesPlayed()) * 60;
        gameStatsP1.setAverageGameDuration(averageDuration);
        LOGGER.info("Calculated average game duration for Player 1: {}", averageDuration);

        // Log updated average game duration
        LOGGER.info("Updated average game duration for Player 1: {}, Player 2: {}", gameStatsP1.getAverageGameDuration(), gameStatsP2.getAverageGameDuration());
        gameStatsP1.setHighestScore(Math.max(gameStatsP1.getHighestScore(), command.scoreP1()));
        gameStatsP2.setHighestScore(Math.max(gameStatsP2.getHighestScore(), command.scoreP2()));

        gameStatsP1.setMovesMade(gameStatsP1.getMovesMade() + command.movesMadeP1());
        gameStatsP2.setMovesMade(gameStatsP2.getMovesMade() + command.movesMadeP2());

        gameStatsP1.setAverageGameDuration(gameStatsP1.getTotalGamesPlayed() == 0 ? 0 : gameStatsP1.getTotalTimePlayed() / gameStatsP1.getTotalGamesPlayed() * 60);
        gameStatsP2.setAverageGameDuration(gameStatsP2.getTotalGamesPlayed() == 0 ? 0 : gameStatsP2.getTotalTimePlayed() / gameStatsP2.getTotalGamesPlayed() * 60);

        // Save or update the updated game statistics (use repository or port method)
        updateGameStatisticsPort.updateGameStatistics(gameStatsP1);
        updateGameStatisticsPort.updateGameStatistics(gameStatsP2);

        LOGGER.info("Updated player statistics for game session: {}. Final scores - Player 1: {}, Player 2: {}.", command.id(), command.scoreP1(), command.scoreP2());
    }

    private double calculateGameDuration(LocalDateTime startTime, LocalDateTime endTime) {
        if (startTime == null || endTime == null) {
            LOGGER.error("Start time or end time is null. Cannot calculate game duration.");
            return 0;
        }
        double duration = (endTime.getHour() - startTime.getHour()) +
                (double) (endTime.getMinute() - startTime.getMinute()) / 60;
        LOGGER.info("Calculated game duration for start time: {}, end time: {}", startTime, endTime);
        LOGGER.info("Calculated game duration: {} hours", duration);
        return duration;
    }

    @Override
    public void updateGameStatistics(UpdateGameStatisticsCommand updateGameStatisticsCommand) {
        UUID sessionId = updateGameStatisticsCommand.id();  // Use sessionId from the command

        // Check if a MatchSession already exists with the provided sessionId
        Optional<MatchSession> existingSession = loadMatchSessionPort.loadMatchSessionById(sessionId);

        LOGGER.info(updateGameStatisticsCommand.toString());

        // If session exists, update it; otherwise, create a new one
        if (existingSession.isPresent()) {
            LOGGER.warn("MatchSession already exists for sessionId: {}", sessionId);
            // Update the existing match session as needed (e.g., end time, winner, score)
            updateMatchSession(existingSession.get(), updateGameStatisticsCommand);
        } else {
            // Create a new MatchSession if not found
            LOGGER.info("Creating a new MatchSession for sessionId: {}", sessionId);
            createNewMatchSession(updateGameStatisticsCommand);
        }
    }

    private void updateMatchSession(MatchSession matchSession, UpdateGameStatisticsCommand command) {
        matchSession.setStartTime(command.startTime());
        matchSession.setEndTime(command.endTime());
        matchSession.setActive(command.isActive());
        matchSession.setWinner(Winner.valueOf(command.winner()));
        matchSession.setScoreP1(command.scoreP1());
        matchSession.setScoreP2(command.scoreP2());
        matchSession.setMovesMadeP1(command.movesMadeP1());
        matchSession.setMovesMadeP2(command.movesMadeP2());

        // Save or update the existing match session (use repository or port method)
        updateMatchSessionPort.updateMatchSession(matchSession);

        // Log when the game ends
        if (!command.isActive()) {
            LOGGER.info("Game has ended for MatchSession: {}. Winner: {}", matchSession.getId(), command.winner());
        }

        // Save or update the existing match session (use repository or port method)
        updateMatchSessionPort.updateMatchSession(matchSession);

        LOGGER.info(String.valueOf(command));

        // Update player statistics after match session update
        Optional<GameStatistics> gameStatsP1 = loadGameStatisticsPort.loadGameStatisticsByPlayerIdAndGameId(command.playerIds().get(0), command.gameId());
        Optional<GameStatistics> gameStatsP2 = loadGameStatisticsPort.loadGameStatisticsByPlayerIdAndGameId(command.playerIds().get(1), command.gameId());

        if (gameStatsP1.isPresent() && gameStatsP2.isPresent()) {
            updatePlayerStatistics(gameStatsP1.get(), gameStatsP2.get(), Winner.valueOf(command.winner()), command.winner(), command);
        } else {
            LOGGER.error("Game statistics not found for players. Cannot update statistics.");
        }
    }

    private void createNewMatchSession(UpdateGameStatisticsCommand updateGameStatisticsCommand) {
        List<GameStatistics> gameStats = loadGameStatisticsForPlayers(updateGameStatisticsCommand);

        MatchSession matchSession = new MatchSession(
                updateGameStatisticsCommand.id(),
                new GameId(updateGameStatisticsCommand.gameId()),
                gameStats,
                updateGameStatisticsCommand.startTime(),
                updateGameStatisticsCommand.endTime(),
                updateGameStatisticsCommand.isActive(),
                Winner.valueOf(updateGameStatisticsCommand.winner()),
                updateGameStatisticsCommand.scoreP1(),
                updateGameStatisticsCommand.scoreP2(),
                updateGameStatisticsCommand.movesMadeP1(),
                updateGameStatisticsCommand.movesMadeP2()
        );

        // Create a new match session
        createMatchSessionPort.createMatchSession(matchSession);

        updatePlayerStatistics(gameStats.get(0), gameStats.get(1), Winner.valueOf(updateGameStatisticsCommand.winner()), updateGameStatisticsCommand.winner(), updateGameStatisticsCommand);

    }

    private List<GameStatistics> loadGameStatisticsForPlayers(UpdateGameStatisticsCommand command) {
        return command.playerIds().stream()
                .map(playerId -> {
                    try {
                        return loadGameStatisticsPort
                                .loadGameStatisticsByPlayerIdAndGameId(playerId, command.gameId())
                                .orElseThrow(() -> new IllegalStateException("Stats missing for Player ID: " + playerId));
                    } catch (IllegalStateException e) {
                        System.err.println(e.getMessage());
                        return null; // Or handle differently
                    }
                })
                .filter(Objects::nonNull) // Skip missing entries
                .toList();
    }
}

