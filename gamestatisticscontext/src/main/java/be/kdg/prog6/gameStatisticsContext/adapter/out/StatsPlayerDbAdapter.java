package be.kdg.prog6.gameStatisticsContext.adapter.out;

import be.kdg.prog6.gameStatisticsContext.domain.*;
import be.kdg.prog6.gameStatisticsContext.port.out.LoadPlayerPort;
import be.kdg.prog6.gameStatisticsContext.port.out.LoadPlayersPort;
import be.kdg.prog6.gameStatisticsContext.port.out.StatsPlayerCreatedPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class StatsPlayerDbAdapter implements LoadPlayerPort, LoadPlayersPort, StatsPlayerCreatedPort {
    private static final Logger log = LoggerFactory.getLogger(StatsPlayerDbAdapter.class);
    private final PlayerRepository playerRepository;

    public StatsPlayerDbAdapter(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Optional<Player> loadPlayerById(UUID playerId) {
        return playerRepository.findById(playerId)
                .map(this::toPlayer);
    }

    @Override
    public List<Player> loadPlayers() {
        return playerRepository.findAll()
                .stream()
                .map(this::toPlayer)
                .collect(Collectors.toList());
    }

    private Player toPlayer(StatsPlayerJpaEntity playerEntity) {
        List<GameStatistics> gameStatistics = playerEntity.getGameStatistics()
                .stream()
                .map(this::toGameStatistics)
                .collect(Collectors.toList());

        return new Player(
                new PlayerId(playerEntity.getId()),
                playerEntity.getName(),
                LocalDate.parse(playerEntity.getBirthDate()),
                Gender.valueOf(playerEntity.getGender()),
                playerEntity.getLocation(),
                gameStatistics
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

    @Override
    public void createPlayer(Player player) {
        String genderString = (player.getGender() != null) ? player.getGender().toString() : "UNKNOWN";
        LocalDate birthDate = (player.getBirthDate() != null) ? player.getBirthDate() : LocalDate.now();

        StatsPlayerJpaEntity playerJpaEntity = new StatsPlayerJpaEntity(
                player.getId().id(),
                player.getName(),
                birthDate.toString(),
                genderString,
                player.getLocation()
        );
        log.info("Creating new player with name: {}", player.getName());
        playerRepository.save(playerJpaEntity);
    }
}
