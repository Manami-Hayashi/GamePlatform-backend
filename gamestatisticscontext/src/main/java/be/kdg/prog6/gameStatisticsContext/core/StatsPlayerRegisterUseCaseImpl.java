package be.kdg.prog6.gameStatisticsContext.core;

import be.kdg.prog6.gameStatisticsContext.domain.Player;
import be.kdg.prog6.gameStatisticsContext.domain.PlayerId;
import be.kdg.prog6.gameStatisticsContext.port.in.RegisterPlayerUseCase;
import be.kdg.prog6.gameStatisticsContext.port.in.RegisterUserCommand;
import be.kdg.prog6.gameStatisticsContext.port.out.LoadPlayerPort;
import be.kdg.prog6.gameStatisticsContext.port.out.StatsPlayerCreatedPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class StatsPlayerRegisterUseCaseImpl implements RegisterPlayerUseCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatsPlayerRegisterUseCaseImpl.class);
    private final LoadPlayerPort loadPlayerPort;
    private final StatsPlayerCreatedPort statsPlayerCreatedPort;

    public StatsPlayerRegisterUseCaseImpl(LoadPlayerPort loadPlayerPort, StatsPlayerCreatedPort gameMngPlayerCreatedPort) {
        this.loadPlayerPort = loadPlayerPort;
        this.statsPlayerCreatedPort = gameMngPlayerCreatedPort;
    }

    @Override
    public void registerPlayer(RegisterUserCommand command) {
        LOGGER.info("Registering player with name on stats db: {}", command.name());
        PlayerId playerId = new PlayerId(command.playerId());
        Player player = new Player(playerId, command.name());

        if (loadPlayerPort.loadPlayerById(playerId.id()).isPresent()) {
            throw new IllegalArgumentException("Player already exists");
        }

        statsPlayerCreatedPort.createPlayer(player);
        LOGGER.info("Player registered with id and name: {} {}", playerId, player.getName());
    }
}
