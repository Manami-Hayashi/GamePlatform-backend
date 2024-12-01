package be.kdg.prog6.PlayerManagementContext.core;

import be.kdg.prog6.PlayerManagementContext.domain.Game;
import be.kdg.prog6.PlayerManagementContext.domain.PlayerId;
import be.kdg.prog6.PlayerManagementContext.port.in.DisplayOwnedGameUseCase;
import be.kdg.prog6.PlayerManagementContext.port.out.GameLoadedPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DisplayOwnedGameUseCaseImpl implements DisplayOwnedGameUseCase {

    private final GameLoadedPort gameLoadedPort;
    private static final Logger logger = LoggerFactory.getLogger(DisplayOwnedGameUseCaseImpl.class);

    public DisplayOwnedGameUseCaseImpl(GameLoadedPort gameLoadedPort) {
        this.gameLoadedPort = gameLoadedPort;
    }

    @Override
    public List<Game> displayOwnedGames(PlayerId playerId) {
        logger.info("Displaying games owned by player with id {}", playerId.id());

        return gameLoadedPort.loadGames(playerId);
    }
}
