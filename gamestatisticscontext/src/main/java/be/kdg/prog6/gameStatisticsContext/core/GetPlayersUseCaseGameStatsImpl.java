package be.kdg.prog6.gameStatisticsContext.core;

import be.kdg.prog6.gameStatisticsContext.domain.Player;
import be.kdg.prog6.gameStatisticsContext.port.in.GetPlayersUseCase;
import be.kdg.prog6.gameStatisticsContext.port.out.LoadPlayersPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetPlayersUseCaseGameStatsImpl implements GetPlayersUseCase {
    private final Logger logger = LoggerFactory.getLogger(GetPlayersUseCaseGameStatsImpl.class);
    private final LoadPlayersPort loadPlayersPort;

    public GetPlayersUseCaseGameStatsImpl(LoadPlayersPort loadPlayersPort) {
        this.loadPlayersPort = loadPlayersPort;
    }

    @Override
    public List<Player> getPlayers() {
        logger.info("Retrieving all players");
        return loadPlayersPort.loadPlayers();
    }
}
