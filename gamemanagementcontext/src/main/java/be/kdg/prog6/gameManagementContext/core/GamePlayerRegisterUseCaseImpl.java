package be.kdg.prog6.gameManagementContext.core;

import be.kdg.prog6.gameManagementContext.domain.Player;
import be.kdg.prog6.gameManagementContext.domain.PlayerId;
import be.kdg.prog6.gameManagementContext.ports.in.RegisterUseCase;
import be.kdg.prog6.gameManagementContext.ports.in.RegisterUserCommand;
import be.kdg.prog6.gameManagementContext.ports.out.GameMngPlayerCreatedPort;
import be.kdg.prog6.gameManagementContext.ports.out.LoadPlayerPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class GamePlayerRegisterUseCaseImpl implements RegisterUseCase {

    private final Logger logger = LoggerFactory.getLogger(GamePlayerRegisterUseCaseImpl.class);

    private final LoadPlayerPort loadPlayerPort;
    private final GameMngPlayerCreatedPort gameMngPlayerCreatedPort;

    public GamePlayerRegisterUseCaseImpl(LoadPlayerPort loadPlayerPort, GameMngPlayerCreatedPort gameMngPlayerCreatedPort) {
        this.loadPlayerPort = loadPlayerPort;
        this.gameMngPlayerCreatedPort = gameMngPlayerCreatedPort;
    }

    @Override
    public void registerPlayer(RegisterUserCommand command) {
        logger.info("Registering player with name on gamemng db: {}", command.name());
        PlayerId playerId = new PlayerId(command.playerId());
        Player player = new Player(playerId, command.name());
        if (loadPlayerPort.loadPlayerById(playerId.id()).isPresent()) {
            throw new IllegalArgumentException("Player with id " + playerId + " already exists.");
        }
        gameMngPlayerCreatedPort.createPlayer(player);
        logger.info("Player registered with id and name: {} {}", playerId, player.getName());

    }

}
