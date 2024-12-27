package be.kdg.prog6.playerManagementContext.core;

import be.kdg.prog6.playerManagementContext.domain.Game;
import be.kdg.prog6.playerManagementContext.domain.PlayerId;
import be.kdg.prog6.playerManagementContext.ports.in.GamePurchasedUseCase;
import be.kdg.prog6.playerManagementContext.ports.out.GamePurchasedPort;
import be.kdg.prog6.playerManagementContext.ports.out.LoadPlayerPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class GamePurchasedUseCaseImpl implements GamePurchasedUseCase {
    private final LoadPlayerPort loadPlayerPort;
    private final GamePurchasedPort gamePurchasedPort;
    private final Logger logger = LoggerFactory.getLogger(GamePurchasedUseCaseImpl.class);

    public GamePurchasedUseCaseImpl(LoadPlayerPort loadPlayerPort, GamePurchasedPort gamePurchasedPort) {
        this.loadPlayerPort = loadPlayerPort;
        this.gamePurchasedPort = gamePurchasedPort;
    }

    @Override
    public void handleGamePurchased(PlayerId playerId, Game game) {
        logger.info("Handling game purchased for player: {} and game: {}", playerId, game.getGameName());
        try {
            loadPlayerPort.loadPlayer(playerId.id());
        } catch (Exception e) {
            logger.error("Failed to retrieve player with ID {}", playerId, e);
            throw e;
        }
        gamePurchasedPort.gamePurchased(playerId, game);
    }
}
