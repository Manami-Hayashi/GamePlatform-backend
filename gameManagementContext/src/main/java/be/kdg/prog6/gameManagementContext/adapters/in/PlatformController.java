package be.kdg.prog6.gameManagementContext.adapters.in;

import be.kdg.prog6.gameManagementContext.ports.in.AddGameCommand;
import be.kdg.prog6.gameManagementContext.ports.in.AddGameUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/games")
public class PlatformController {

    private final AddGameUseCase addGameUseCase;

    @Autowired
    public PlatformController(AddGameUseCase addGameUseCase) {
        this.addGameUseCase = addGameUseCase;
    }

    @PostMapping("/add")
    public void addGame(@RequestBody AddGameRequest request) {
        AddGameCommand command = new AddGameCommand(request.getGameName(), request.getDescription());
        addGameUseCase.addGame(command);
    }
}