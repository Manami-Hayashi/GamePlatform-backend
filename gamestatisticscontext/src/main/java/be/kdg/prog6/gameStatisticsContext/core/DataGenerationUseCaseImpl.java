package be.kdg.prog6.gameStatisticsContext.core;

import be.kdg.prog6.gameStatisticsContext.domain.*;
import be.kdg.prog6.gameStatisticsContext.port.in.DataGenerationUseCase;
import be.kdg.prog6.gameStatisticsContext.port.out.*;
import com.github.javafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DataGenerationUseCaseImpl implements DataGenerationUseCase {
    private static final Logger logger = LoggerFactory.getLogger(DataGenerationUseCaseImpl.class);
    private final StatsPlayerCreatedPort playerCreatedPort;
    private final LoadPlayersPort loadPlayersPort;
    private final LoadGameStatisticsPort loadGameStatisticsPort;
    private final LoadAllGameStatisticsPort loadAllGameStatisticsPort;
    private final UpdateGameStatisticsPort updateGameStatisticsPort;
    private final CreateMatchSessionPort createMatchSessionPort;

    public DataGenerationUseCaseImpl(StatsPlayerCreatedPort playerCreatedPort, LoadPlayersPort loadPlayersPort, LoadGameStatisticsPort loadGameStatisticsPort, LoadAllGameStatisticsPort loadAllGameStatisticsPort, UpdateGameStatisticsPort updateGameStatisticsPort, CreateMatchSessionPort createMatchSessionPort) {
        this.playerCreatedPort = playerCreatedPort;
        this.loadPlayersPort = loadPlayersPort;
        this.loadGameStatisticsPort = loadGameStatisticsPort;
        this.loadAllGameStatisticsPort = loadAllGameStatisticsPort;
        this.updateGameStatisticsPort = updateGameStatisticsPort;
        this.createMatchSessionPort = createMatchSessionPort;
    }

    public void generatePlayers(int count) {
        Faker faker = new Faker();

        if (count < 0) {
            throw new IllegalArgumentException("Count must be greater than or equal to 0");
        }

        for (int i = 0; i < count; i++) {
            Player player = new Player(
                    new PlayerId(UUID.randomUUID()),
                    faker.name().fullName(),
                    faker.date().birthday().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                    Gender.values()[faker.number().numberBetween(0, Gender.values().length)],
                    faker.address().city()
            );
            playerCreatedPort.createPlayer(player);
        }
    }

    public void generateGameStatistics(int gamesPerPlayer) {
        if (gamesPerPlayer < 0) {
            throw new IllegalArgumentException("Games per player must be greater than or equal to 0");
        }

        List<Player> players = loadPlayersPort.loadPlayers();
        List<GameId> existingGameIds = loadAllGameStatisticsPort.loadAllGameStatistics().stream()
                .map(GameStatistics::getGameId)
                .distinct()
                .toList();

        Faker faker = new Faker();

        for (Player player : players) {
            for (int i = 0; i < gamesPerPlayer; i++) {
                GameId gameId = existingGameIds.get(faker.number().numberBetween(0, existingGameIds.size()));
                int totalGamesPlayed = faker.number().numberBetween(10, 100);
                int wins = faker.number().numberBetween(0, totalGamesPlayed);
                int losses = totalGamesPlayed - wins;
                int draws = faker.number().numberBetween(0, 5);
                double winLossRatio = wins > 0 ? (double) wins / losses : 0.0;
                double totalTimePlayed = faker.number().randomDouble(2, 1, 100);
                int movesMade = faker.number().numberBetween(0, 2000);
                int highestScore = faker.number().numberBetween(1, 20);
                double averageGameDuration = totalTimePlayed / totalGamesPlayed;

                GameStatistics stats = new GameStatistics(
                        player.getId(),
                        gameId,
                        faker.number().numberBetween(1000, 5000),
                        totalGamesPlayed,
                        wins,
                        losses,
                        draws,
                        winLossRatio,
                        totalTimePlayed,
                        highestScore,
                        movesMade,
                        averageGameDuration
                );

                updateGameStatisticsPort.updateGameStatistics(stats);

                generateMatchSessions(player.getId(), gameId, faker.number().numberBetween(1,4));
            }
        }
    }

    private void generateMatchSessions(PlayerId playerId, GameId gameId, int matchCount) {
        Faker faker = new Faker();

        for (int i = 0; i < matchCount; i++) {
            LocalDateTime startTime = LocalDateTime.now().minusDays(faker.number().numberBetween(1, 365));
            LocalDateTime endTime = startTime.plusMinutes(faker.number().numberBetween(2, 120));
            int scoreP1 = faker.number().numberBetween(10, 500);
            int scoreP2 = faker.number().numberBetween(10, 500);
            int movesMadeP1 = faker.number().numberBetween(5, 50);
            int movesMadeP2 = faker.number().numberBetween(5, 50);

            // Determine the winner based on the scores
            Winner winner;
            if (scoreP1 > scoreP2) {
                winner = Winner.PLAYER1;
            } else if (scoreP2 > scoreP1) {
                winner = Winner.PLAYER2;
            } else {
                winner = Winner.DRAW;
            }

            // Load the game statistics for the player and game
            Optional<GameStatistics> gameStatisticsOpt = loadGameStatisticsPort.loadGameStatisticsByPlayerIdAndGameId(playerId.id(), gameId.id());
            if (gameStatisticsOpt.isEmpty()) {
                throw new IllegalArgumentException("Game statistics not found for playerId: " + playerId.id() + " and gameId: " + gameId.id());
            }
            GameStatistics gameStatistics = gameStatisticsOpt.get();

            MatchSession match = new MatchSession(
                    UUID.randomUUID(),
                    gameId,
                    List.of(gameStatistics), // Pass the game statistics
                    startTime,
                    endTime,
                    false,
                    winner,
                    scoreP1,
                    scoreP2,
                    movesMadeP1,
                    movesMadeP2
            );

            createMatchSessionPort.createMatchSession(match);
        }
    }

}
