package be.kdg.prog6.gameManagementContext.core;

import be.kdg.prog6.gameManagementContext.domain.Admin;
import be.kdg.prog6.gameManagementContext.domain.Game;
import be.kdg.prog6.gameManagementContext.domain.GameId;
import be.kdg.prog6.gameManagementContext.ports.in.AddGameCommand;
import be.kdg.prog6.gameManagementContext.ports.in.AddGameUseCase;
import be.kdg.prog6.gameManagementContext.ports.out.SaveGamePort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AddGameUseCaseImpl implements AddGameUseCase {
    private final SaveGamePort saveGamePort;

    public AddGameUseCaseImpl(SaveGamePort saveGamePort) {
        this.saveGamePort = saveGamePort;
    }

    @Override
    public void addGame(AddGameCommand command) {
        // Create a new game using the provided details
        GameId gameId = new GameId(UUID.randomUUID());
        Game game = new Game(gameId, command.gameName(), command.description());

        Admin admin = new Admin();
        admin.createGame(gameId, game.getGameName(), game.getDescription());
        saveGamePort.saveGame(game);
    }
}