package be.kdg.prog6.playerManagementContext.core;

import be.kdg.prog6.playerManagementContext.domain.Player;
import be.kdg.prog6.playerManagementContext.domain.PlayerId;
import be.kdg.prog6.playerManagementContext.ports.in.RegisterUseCase;
import be.kdg.prog6.playerManagementContext.ports.in.RegisterUserCommand;
import be.kdg.prog6.playerManagementContext.ports.out.CreatePlayerPort;
import be.kdg.prog6.playerManagementContext.ports.out.LoadPlayerPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RegisterUseCaseImpl implements RegisterUseCase {
    private final Logger logger = LoggerFactory.getLogger(RegisterUseCaseImpl.class);
    private final LoadPlayerPort loadPlayerPort;
    private final CreatePlayerPort createPlayerPort;

    public RegisterUseCaseImpl(LoadPlayerPort loadPlayerPort, CreatePlayerPort createPlayerPort) {
        this.loadPlayerPort = loadPlayerPort;
        this.createPlayerPort = createPlayerPort;
    }

    @Override
    public void registerPlayer(RegisterUserCommand command) {
        logger.info("Registering player with name: {}", command.name());
        PlayerId playerId = new PlayerId(command.playerId());
        Player player = new Player(playerId, command.name());

        if (loadPlayerPort.loadPlayer(playerId.id()) != null) {
            throw new IllegalArgumentException("Player with id " + playerId + " already exists.");
        }
        createPlayerPort.createPlayer(player);
    }
}
