package be.kdg.prog6.gameManagementContext.core;

import be.kdg.prog6.gameManagementContext.domain.Admin;
import be.kdg.prog6.gameManagementContext.domain.Game;
import be.kdg.prog6.gameManagementContext.domain.GameId;
import be.kdg.prog6.gameManagementContext.ports.in.AddGameCommand;
import be.kdg.prog6.gameManagementContext.ports.in.AddGameUseCase;
import be.kdg.prog6.gameManagementContext.ports.out.SaveGamePort;
import be.kdg.prog6.gameManagementContext.ports.out.UpdateGamePort;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AddGameUseCaseImpl implements AddGameUseCase {
    private final SaveGamePort saveGamePort;
    private final List<UpdateGamePort> updateGamePorts;

    public AddGameUseCaseImpl(SaveGamePort saveGamePort, List<UpdateGamePort> updateGamePorts) {
        this.saveGamePort = saveGamePort;
        this.updateGamePorts = updateGamePorts;
    }

    @Override
    @Transactional
    public void addGame(AddGameCommand command) {
        // Create a new game using the provided details
        GameId gameId = new GameId(UUID.randomUUID());
        Game game = new Game(gameId, command.gameName(), command.price(), command.description());

        Admin admin = new Admin();
        admin.createGame(gameId, game.getGameName(), game.getPrice(), game.getDescription());
        saveGamePort.saveGame(game);

        // Update the game using all UpdateGamePort implementations
        updateGamePorts.forEach(updateGamePort -> updateGamePort.updateGame(game));
    }
}