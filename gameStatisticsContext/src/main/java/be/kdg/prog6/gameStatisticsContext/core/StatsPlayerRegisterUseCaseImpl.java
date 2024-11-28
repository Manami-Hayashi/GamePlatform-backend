package be.kdg.prog6.gameStatisticsContext.core;

import be.kdg.prog6.gameStatisticsContext.domain.Player;
import be.kdg.prog6.gameStatisticsContext.domain.PlayerId;
import be.kdg.prog6.gameStatisticsContext.port.in.RegisterPlayerUseCase;
import be.kdg.prog6.gameStatisticsContext.port.in.RegisterUserCommand;
import be.kdg.prog6.gameStatisticsContext.port.out.StatsPlayerCreatedPort;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class StatsPlayerRegisterUseCaseImpl implements RegisterPlayerUseCase {

    private final Logger logger = org.slf4j.LoggerFactory.getLogger(StatsPlayerRegisterUseCaseImpl.class);

    private final StatsPlayerCreatedPort statsPlayerCreatedPort;

    public StatsPlayerRegisterUseCaseImpl(StatsPlayerCreatedPort gameMngPlayerCreatedPort) {
        this.statsPlayerCreatedPort = gameMngPlayerCreatedPort;
    }

    @Override
    public void registerPlayer(RegisterUserCommand command) {
        logger.info("Registering player with name on stats db: {}", command.name());
        PlayerId playerId = new PlayerId(command.playerId());
        Player player = new be.kdg.prog6.gameStatisticsContext.domain.Player(playerId, command.name());

        statsPlayerCreatedPort.createPlayer(player);
        logger.info("Player registered with id and name: {} {}", playerId, player.getName());
    }
}
