package be.kdg.prog6.PlayerManagementContext.adapter.in;

import be.kdg.prog6.PlayerManagementContext.core.DisplayOwnedGameUseCaseImpl;
import be.kdg.prog6.PlayerManagementContext.domain.Game;
import be.kdg.prog6.PlayerManagementContext.domain.PlayerId;
import be.kdg.prog6.PlayerManagementContext.port.in.DisplayOwnedGameUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/players")
public class PlayerGameController {
    private final DisplayOwnedGameUseCase displayOwnedGameUseCase;
    private static final Logger logger = LoggerFactory.getLogger(PlayerGameController.class);

    public PlayerGameController(DisplayOwnedGameUseCase displayOwnedGameUseCase) {
        this.displayOwnedGameUseCase = displayOwnedGameUseCase;
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

}
