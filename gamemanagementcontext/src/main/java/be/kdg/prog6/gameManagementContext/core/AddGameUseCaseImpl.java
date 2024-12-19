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

import java.math.BigDecimal;
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
        if (command.gameName() == null || command.gameName().isEmpty()) {
            throw new IllegalArgumentException("Game name cannot be empty");
        }
        if (command.price() == null || command.price().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price must be zero or greater");
        }
        if (command.description() == null || command.description().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty");
        }

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