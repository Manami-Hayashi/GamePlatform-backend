package be.kdg.prog6.gameManagementContext.core;

import be.kdg.prog6.gameManagementContext.domain.Player;
import be.kdg.prog6.gameManagementContext.domain.PlayerId;
import be.kdg.prog6.gameManagementContext.ports.in.RegisterUseCase;
import be.kdg.prog6.gameManagementContext.ports.in.RegisterUserCommand;
import be.kdg.prog6.gameManagementContext.ports.out.GameMngPlayerCreatedPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class GamePlayerRegisterUseCaseImpl implements RegisterUseCase {

    private final Logger logger = LoggerFactory.getLogger(GamePlayerRegisterUseCaseImpl.class);

    private GameMngPlayerCreatedPort gameMngPlayerCreatedPort;

    public GamePlayerRegisterUseCaseImpl(GameMngPlayerCreatedPort gameMngPlayerCreatedPort) {
        this.gameMngPlayerCreatedPort = gameMngPlayerCreatedPort;
    }

    @Override
    public void registerPlayer(RegisterUserCommand command) {
        logger.info("Registering player with name on gamemng db: {}", command.name());
        PlayerId playerId = new PlayerId(command.playerId());
        Player player = new Player(playerId, command.name());

        gameMngPlayerCreatedPort.createPlayer(player);
        logger.info("Player registered with id and name: {} {}", playerId, player.getName());

    }

}
