package be.kdg.prog6.playerManagementContext.adapters.in;

import be.kdg.prog6.playerManagementContext.domain.Game;
import be.kdg.prog6.playerManagementContext.domain.GameId;
import be.kdg.prog6.playerManagementContext.domain.PlayerId;
import be.kdg.prog6.playerManagementContext.ports.in.DisplayOwnedGameUseCase;
import be.kdg.prog6.playerManagementContext.ports.in.FavoriteGameUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/players")
public class PlayerGameController {
    private final DisplayOwnedGameUseCase displayOwnedGameUseCase;
    private final FavoriteGameUseCase favoriteGameUseCase;
    private static final Logger logger = LoggerFactory.getLogger(PlayerGameController.class);

    public PlayerGameController(DisplayOwnedGameUseCase displayOwnedGameUseCase, FavoriteGameUseCase favoriteGameUseCase) {
        this.displayOwnedGameUseCase = displayOwnedGameUseCase;
        this.favoriteGameUseCase = favoriteGameUseCase;
    }

    @GetMapping("/{playerId}/gamesOwned")
    public ResponseEntity<List<GameOwnedDto>> getGamesOwnedByPlayer(@PathVariable UUID playerId) {
        logger.info("Getting games owned by player with id {}", playerId);

        List<Game> games = displayOwnedGameUseCase.displayOwnedGames(new PlayerId(playerId));
        if (games.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        List<GameOwnedDto> gameOwnedDtos = games.stream()
                .map(game -> new GameOwnedDto(game.getGameId().id().toString(), game.getGameName(), game.isFavorite()))
                .collect(toList());

        return ResponseEntity.ok(gameOwnedDtos);
    }

    @PostMapping("/{playerId}/games/{gameId}/toggle-favorite")
    public ResponseEntity<String> toggleFavoriteGame(
            @PathVariable UUID playerId,
            @PathVariable UUID gameId) {
        try {
            favoriteGameUseCase.toggleFavoriteGame(new PlayerId(playerId), new GameId(gameId));
            return ResponseEntity.ok("Game favorite status toggled successfully");
        } catch (IllegalArgumentException e) {
            logger.error("Error toggling favorite for game with ID {} for player with ID {}: {}", gameId, playerId, e.getMessage());
            return ResponseEntity.badRequest().body("The game is not owned by the player or invalid request.");
        }
    }

}
