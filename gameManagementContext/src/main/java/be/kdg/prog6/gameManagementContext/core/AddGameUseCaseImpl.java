package be.kdg.prog6.gameManagementContext.core;

import be.kdg.prog6.gameManagementContext.adapters.out.external.ExternalGameService;
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
    private final ExternalGameService externalGameService;

    public AddGameUseCaseImpl(SaveGamePort saveGamePort, ExternalGameService externalGameService) {
        this.saveGamePort = saveGamePort;
        this.externalGameService = externalGameService;
    }

    @Override
    public void addGame(AddGameCommand command) {
        // Fetch game details from the external application
        Long gameIdLong = (long) command.gameName().hashCode();
        Game externalGame = externalGameService.fetchGame(gameIdLong);

        // Use the fetched game details to create a new game
        GameId gameId = new GameId(UUID.randomUUID());
        Game game = new Game(gameId, externalGame.getGameName(), externalGame.getDescription());

        Admin admin = new Admin();
        admin.createGame(gameId, game.getGameName(), game.getDescription());
        saveGamePort.saveGame(game);
    }
}