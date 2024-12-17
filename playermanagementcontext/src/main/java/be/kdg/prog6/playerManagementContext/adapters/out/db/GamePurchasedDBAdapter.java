package be.kdg.prog6.playerManagementContext.adapters.out.db;

import be.kdg.prog6.playerManagementContext.domain.Game;
import be.kdg.prog6.playerManagementContext.domain.PlayerId;
import be.kdg.prog6.playerManagementContext.ports.out.GamePurchasedPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class GamePurchasedDBAdapter implements GamePurchasedPort {
    private final GameOwnedJpaRepository gameOwnedJpaRepository;
    private final PlayerJpaRepository playerJpaRepository;
    private static final Logger logger = LoggerFactory.getLogger(GamePurchasedDBAdapter.class);

    public GamePurchasedDBAdapter(GameOwnedJpaRepository gameOwnedJpaRepository, PlayerJpaRepository playerJpaRepository) {
        this.gameOwnedJpaRepository = gameOwnedJpaRepository;
        this.playerJpaRepository = playerJpaRepository;
    }

    @Override
    public void gamePurchased(PlayerId playerId, Game game) {
        PlayerJpaEntity playerJpaEntity = playerJpaRepository.findById(playerId.id())
                .orElseThrow(() -> new IllegalArgumentException("Player not found"));

        GameOwnedJpaEntity gameOwnedJpaEntity = new GameOwnedJpaEntity();
        gameOwnedJpaEntity.setGameId(game.getGameId().id());
        gameOwnedJpaEntity.setGameName(game.getGameName());
        gameOwnedJpaEntity.setPlayer(playerJpaEntity);

        logger.info("Saving purchased game with ID {} for player with ID {}", game, playerId);

        gameOwnedJpaRepository.save(gameOwnedJpaEntity);
    }
}