package be.kdg.prog6.playerManagementContext.core;

import be.kdg.prog6.playerManagementContext.domain.Player;
import be.kdg.prog6.playerManagementContext.domain.PlayerId;
import be.kdg.prog6.playerManagementContext.ports.in.RegisterUseCase;
import be.kdg.prog6.playerManagementContext.ports.in.RegisterUserCommand;
import be.kdg.prog6.playerManagementContext.ports.out.CreatePlayerPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RegisterUseCaseImpl implements RegisterUseCase {
    private final Logger logger = LoggerFactory.getLogger(RegisterUseCaseImpl.class);

    private final CreatePlayerPort createPlayerPort;

    public RegisterUseCaseImpl(CreatePlayerPort createPlayerPort) {
        this.createPlayerPort = createPlayerPort;
    }

    @Override
    public void registerPlayer(RegisterUserCommand command) {
        logger.info("Registering player with name: {}", command.name());
        PlayerId playerId = new PlayerId(command.playerId());
        Player player = new Player(playerId, command.name());

        createPlayerPort.createPlayer(player);
        logger.info("Player registered with id and name: {} {}", playerId, player.getName());

    }
}
