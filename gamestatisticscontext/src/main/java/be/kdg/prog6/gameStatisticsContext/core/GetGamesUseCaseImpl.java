package be.kdg.prog6.gameStatisticsContext.core;

import be.kdg.prog6.gameStatisticsContext.domain.GameId;
import be.kdg.prog6.gameStatisticsContext.port.in.GetGamesUseCase;
import be.kdg.prog6.gameStatisticsContext.port.out.LoadGamesPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetGamesUseCaseImpl implements GetGamesUseCase {
    private final LoadGamesPort loadGamesPort;
    private final Logger logger = LoggerFactory.getLogger(GetGamesUseCaseImpl.class);

    public GetGamesUseCaseImpl(LoadGamesPort loadGamesPort) {
        this.loadGamesPort = loadGamesPort;
    }

    @Override
    public List<GameId> getGames() {
        logger.info("Retrieving games");
        return loadGamesPort.loadGames();
    }
}
