package be.kdg.prog6.PlayerManagementContext.core;

import be.kdg.prog6.PlayerManagementContext.domain.Player;
import be.kdg.prog6.PlayerManagementContext.port.in.GetPlayersUseCase;
import be.kdg.prog6.PlayerManagementContext.port.out.LoadPlayersPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetPlayersUseCaseImpl implements GetPlayersUseCase {
    private static final Logger LOGGER = LoggerFactory.getLogger(GetPlayersUseCaseImpl.class);
    private final LoadPlayersPort loadPlayersPort;

    public GetPlayersUseCaseImpl(LoadPlayersPort loadPlayersPort) {
        this.loadPlayersPort = loadPlayersPort;
    }

    @Override
    public List<Player> getPlayers() {
        LOGGER.info("Retrieving all players");
        return loadPlayersPort.loadPlayers();
    }
}
