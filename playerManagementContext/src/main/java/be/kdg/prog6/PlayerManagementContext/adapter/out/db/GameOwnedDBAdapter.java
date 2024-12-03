package be.kdg.prog6.PlayerManagementContext.adapter.out.db;

import be.kdg.prog6.PlayerManagementContext.domain.Game;
import be.kdg.prog6.PlayerManagementContext.domain.GameId;
import be.kdg.prog6.PlayerManagementContext.domain.PlayerId;
import be.kdg.prog6.PlayerManagementContext.port.out.GameCreatedPort;
import be.kdg.prog6.PlayerManagementContext.port.out.GameLoadedPort;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component("playerGameDBAdapter")
public class GameOwnedDBAdapter implements GameLoadedPort, GameCreatedPort {
    private final GameOwnedJpaRepository gameOwnedJpaRepository;
    private final PlayerJpaRepository playerJpaRepository;

    private static final Logger logger = LoggerFactory.getLogger(GameOwnedDBAdapter.class);

    public GameOwnedDBAdapter(GameOwnedJpaRepository gameOwnedJpaRepository, PlayerJpaRepository playerJpaRepository) {
        this.gameOwnedJpaRepository = gameOwnedJpaRepository;
        this.playerJpaRepository = playerJpaRepository;
    }


    @Override
    public void gameCreated(PlayerId playerId, Game game) {
        GameOwnedJpaEntity gameOwnedJpaEntity = new GameOwnedJpaEntity(
                game.getGameId().id(),
                game.getGameName(),
                game.isFavorite()
        );

        PlayerJpaEntity playerJpaEntity = playerJpaRepository.findByIdWithGameOwned(playerId)
                .orElseThrow(() -> new IllegalArgumentException("Player not found"));

        gameOwnedJpaEntity.setPlayer(playerJpaEntity);

        logger.info("creating new Game with name {}", game.getGameName());

        gameOwnedJpaRepository.save(gameOwnedJpaEntity);

    }


    @Override
    @Transactional
    public List<Game> loadGames(PlayerId playerId) {
        PlayerJpaEntity playerJpaEntity = playerJpaRepository.findById(playerId.id())
                .orElseThrow(() -> new IllegalArgumentException("Player not found"));

        List<Game> gamesOwned = playerJpaEntity.getGameOwned().stream()
                .map(this::toDomain)
                .collect(toList());

        return gamesOwned;
    }

    private Game toDomain(GameOwnedJpaEntity jpaEntity) {
        return new Game (
                new GameId(jpaEntity.getGameId()),
                jpaEntity.getGameName(),
                jpaEntity.isFavorite()
        );
    }
}
