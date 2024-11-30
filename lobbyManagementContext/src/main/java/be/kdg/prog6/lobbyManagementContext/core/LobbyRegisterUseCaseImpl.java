package be.kdg.prog6.lobbyManagementContext.core;

import be.kdg.prog6.lobbyManagementContext.domain.Player;
import be.kdg.prog6.lobbyManagementContext.domain.PlayerId;
import be.kdg.prog6.lobbyManagementContext.ports.in.RegisterUseCase;
import be.kdg.prog6.lobbyManagementContext.ports.in.RegisterUserCommand;
import be.kdg.prog6.lobbyManagementContext.ports.out.LobbyPlayerCreatedPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class LobbyRegisterUseCaseImpl implements RegisterUseCase {

    private final Logger logger = LoggerFactory.getLogger(LobbyRegisterUseCaseImpl.class);

    private final LobbyPlayerCreatedPort lobbyPlayerCreatedPort;

    public LobbyRegisterUseCaseImpl(LobbyPlayerCreatedPort lobbyPlayerCreatedPort) {
        this.lobbyPlayerCreatedPort = lobbyPlayerCreatedPort;
    }

    @Override
    public void registerPlayer(RegisterUserCommand command) {
        logger.info("Registering player with name on lobby: {}", command.name());
        PlayerId playerId = new PlayerId(command.playerId());
        Player player = new Player(playerId, command.name());

        lobbyPlayerCreatedPort.createPlayer(player);
        logger.info("Player registered with id and name: {} {}", playerId, player.getName());
    }
}
