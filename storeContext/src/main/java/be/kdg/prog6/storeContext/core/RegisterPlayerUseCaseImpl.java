package be.kdg.prog6.storeContext.core;

import be.kdg.prog6.storeContext.domain.Player;
import be.kdg.prog6.storeContext.domain.PlayerId;
import be.kdg.prog6.storeContext.port.in.RegisterPlayerUseCase;
import be.kdg.prog6.storeContext.port.in.RegisterUserCommand;
import be.kdg.prog6.storeContext.port.out.PlayerCreatedPort;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class RegisterPlayerUseCaseImpl implements RegisterPlayerUseCase {
    private final Logger logger = org.slf4j.LoggerFactory.getLogger(RegisterPlayerUseCaseImpl.class);
    private final PlayerCreatedPort playerCreatedPort;

    public RegisterPlayerUseCaseImpl(PlayerCreatedPort playerCreatedPort) {
        this.playerCreatedPort = playerCreatedPort;
    }

    @Override
    public void registerPlayer(RegisterUserCommand command) {
        logger.info("Registering player with name on store: {}", command.name());
        PlayerId playerId = new PlayerId(command.playerId());
        Player player = new Player(playerId, command.name());

        playerCreatedPort.createPlayer(player);
        logger.info("Player registered with id and name: {} {}", playerId, player.getName());
    }

}
