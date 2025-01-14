package be.kdg.prog6.gameManagementContext.adapters.in;

import be.kdg.prog6.gameManagementContext.ports.in.AddGameCommand;
import be.kdg.prog6.gameManagementContext.ports.in.AddGameUseCase;
import be.kdg.prog6.gameManagementContext.ports.in.RecommendGamesCommand;
import be.kdg.prog6.gameManagementContext.ports.in.RecommendGamesUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/games")
public class PlatformController {
    private final AddGameUseCase addGameUseCase;
    private final RecommendGamesUseCase recommendGamesUseCase;

    @Autowired
    public PlatformController(AddGameUseCase addGameUseCase, RecommendGamesUseCase recommendGamesUseCase) {
        this.addGameUseCase = addGameUseCase;
        this.recommendGamesUseCase = recommendGamesUseCase;
    }

    @PostMapping("/add")
    public void addGame(@RequestBody AddGameRequest request) {
        AddGameCommand command = new AddGameCommand(request.getGameName(), request.getPrice(), request.getDescription());
        addGameUseCase.addGame(command);
    }

    @GetMapping("/recommend")
    public List<String> recommendGame(@RequestParam(required = false) List<String> gameNames) {
        RecommendGamesCommand command = new RecommendGamesCommand(gameNames != null ? gameNames : List.of());
        return recommendGamesUseCase.recommendGames(command);
    }
}
