package be.kdg.prog6.lobbyManagementContext.core;

import be.kdg.prog6.lobbyManagementContext.domain.Game;
import be.kdg.prog6.lobbyManagementContext.ports.in.AddLobbyGameUseCase;
import be.kdg.prog6.lobbyManagementContext.ports.out.LobbyGameCreatedPort;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AddLobbyGameUseCaseImpl implements AddLobbyGameUseCase {
    private final LobbyGameCreatedPort lobbyGameCreatedPort;
    private static final Logger logger = LoggerFactory.getLogger(AddLobbyGameUseCaseImpl.class);

    public AddLobbyGameUseCaseImpl(LobbyGameCreatedPort lobbyGameCreatedPort) {
        this.lobbyGameCreatedPort = lobbyGameCreatedPort;
    }

    @Override
    @Transactional
    public void addLobbyGame(Game game) {
        logger.info("Adding new game to the lobby: {}", game.getGameName());
        lobbyGameCreatedPort.createLobbyGame(game);
    }
}