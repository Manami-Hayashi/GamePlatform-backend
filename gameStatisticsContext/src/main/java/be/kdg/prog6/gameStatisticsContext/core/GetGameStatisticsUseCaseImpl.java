package be.kdg.prog6.gameStatisticsContext.core;

import be.kdg.prog6.gameStatisticsContext.domain.GameStatistics;
import be.kdg.prog6.gameStatisticsContext.domain.Player;
import be.kdg.prog6.gameStatisticsContext.port.in.GetGameStatisticsUseCase;
import be.kdg.prog6.gameStatisticsContext.port.out.LoadPlayerPort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GetGameStatisticsUseCaseImpl implements GetGameStatisticsUseCase {
    private final UUID PLAYER_ID = UUID.randomUUID();
    private final LoadPlayerPort loadPlayerPort;

    public GetGameStatisticsUseCaseImpl(final LoadPlayerPort loadPlayerPort) {
        this.loadPlayerPort = loadPlayerPort;
    }

    @Override
    public List<GameStatistics> getGameStatistics() {
        Player player = loadPlayerPort.loadPlayerById(PLAYER_ID).orElseThrow();
        List<GameStatistics> gameStatistics = player.getGameStatistics();
        return gameStatistics;
    }
}
