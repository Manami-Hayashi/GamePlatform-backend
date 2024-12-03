package be.kdg.prog6.PlayerManagementContext.core;

import be.kdg.prog6.PlayerManagementContext.domain.Game;
import be.kdg.prog6.PlayerManagementContext.domain.GameId;
import be.kdg.prog6.PlayerManagementContext.domain.Player;
import be.kdg.prog6.PlayerManagementContext.domain.PlayerId;
import be.kdg.prog6.PlayerManagementContext.port.in.FavoriteGameUseCase;
import be.kdg.prog6.PlayerManagementContext.port.out.GameLoadedPort;
import be.kdg.prog6.PlayerManagementContext.port.out.PlayerLoadedPort;
import be.kdg.prog6.PlayerManagementContext.port.out.UpdatePlayerPort;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FavoriteGameUseCaseImpl implements FavoriteGameUseCase {

    private final PlayerLoadedPort loadPlayerPort;
    private final UpdatePlayerPort updatePlayerPort;
    private final GameLoadedPort gameLoadedPort;
    private final Logger logger = org.slf4j.LoggerFactory.getLogger(FavoriteGameUseCaseImpl.class);

    public FavoriteGameUseCaseImpl(PlayerLoadedPort loadPlayerPort, UpdatePlayerPort updatePlayerPort, GameLoadedPort gameLoadedPort) {
        this.loadPlayerPort = loadPlayerPort;
        this.updatePlayerPort = updatePlayerPort;
        this.gameLoadedPort = gameLoadedPort;
    }

    public void toggleFavoriteGame(PlayerId playerId, GameId gameId) {
        logger.info("Toggling favorite game for player with ID {}", playerId.id());
        Player player = loadPlayerPort.loadPlayer(playerId.id());

        List<Game> games = gameLoadedPort.loadGames(playerId);
        Game gameToToggle = games.stream().filter(game -> game.getGameId().equals(gameId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Game with ID " + gameId + " is not owned by the player."));

        logger.info("games: {}", games.size());
        logger.info("Game to toggle: {}", gameToToggle.getGameName());

        // Step 3: Delegate the toggling logic to the Player entity
        player.toggleFavoriteGame(gameToToggle);


        logger.info("Favorite game toggled for player with ID {} game name: {}", playerId.id(), gameToToggle.getGameName());
        // Persist the updated Player entity back to the database
        updatePlayerPort.updatePlayer(player);

    }

}
