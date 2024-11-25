package be.kdg.prog6.PlayerManagementContext.core;

import be.kdg.prog6.PlayerManagementContext.domain.Player;
import be.kdg.prog6.PlayerManagementContext.domain.PlayerId;
import be.kdg.prog6.PlayerManagementContext.port.in.RegisterUseCase;
import be.kdg.prog6.PlayerManagementContext.port.in.RegisterUserCommand;
import be.kdg.prog6.PlayerManagementContext.port.out.PlayerCreatedPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class RegisterUseCaseImpl implements RegisterUseCase {
    private final Logger logger = LoggerFactory.getLogger(RegisterUseCaseImpl.class);

    private final PlayerCreatedPort playerCreatedPort;

    public RegisterUseCaseImpl(PlayerCreatedPort playerCreatedPort) {
        this.playerCreatedPort = playerCreatedPort;
    }

    @Override
    public void registerPlayer(RegisterUserCommand command) {
        logger.info("Registering player with name: {}", command.name());
        PlayerId playerId = new PlayerId(command.playerId());
        Player player = new Player(playerId, command.name());

        playerCreatedPort.createPlayer(player);
        logger.info("Player registered with id and name: {} {}", playerId, player.getName());

    }
}
