package be.kdg.prog6.gameStatisticsContext.core;

import be.kdg.prog6.gameStatisticsContext.domain.GameStatistics;
import be.kdg.prog6.gameStatisticsContext.domain.Player;
import be.kdg.prog6.gameStatisticsContext.domain.PlayerId;
import be.kdg.prog6.gameStatisticsContext.port.in.GetScoreboardUseCase;
import be.kdg.prog6.gameStatisticsContext.port.out.LoadPlayerPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetScoreboardUseCaseImpl implements GetScoreboardUseCase {
    private final LoadPlayerPort loadPlayerPort;

    public GetScoreboardUseCaseImpl(final LoadPlayerPort loadPlayerPort) {
        this.loadPlayerPort = loadPlayerPort;
    }

    @Override
    public List<GameStatistics> getScoreboard(PlayerId playerId) {
        Player player = loadPlayerPort.loadPlayerById(playerId.id()).orElseThrow();
        return player.getGameStatistics();
    }
}
