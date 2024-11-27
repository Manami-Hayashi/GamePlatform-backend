package be.kdg.prog6.gameStatisticsContext.core;

import be.kdg.prog6.gameStatisticsContext.domain.GameStatistics;
import be.kdg.prog6.gameStatisticsContext.domain.MatchSession;
import be.kdg.prog6.gameStatisticsContext.domain.Player;
import be.kdg.prog6.gameStatisticsContext.port.in.UpdateGameStatisticsUseCase;
import be.kdg.prog6.gameStatisticsContext.port.out.LoadGameStatisticsPort;
import be.kdg.prog6.gameStatisticsContext.port.out.LoadPlayerPort;
import be.kdg.prog6.gameStatisticsContext.port.out.UpdateGameStatisticsPort;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UpdateGameStatisticsUseCaseImpl implements UpdateGameStatisticsUseCase {
    private final LoadPlayerPort loadPlayerPort;
    private final LoadGameStatisticsPort loadGameStatisticsPort;
    private final UpdateGameStatisticsPort updateGameStatisticsPort;

    public UpdateGameStatisticsUseCaseImpl(LoadPlayerPort loadPlayerPort, LoadGameStatisticsPort loadGameStatisticsPort, UpdateGameStatisticsPort updateGameStatisticsPort) {
        this.loadPlayerPort = loadPlayerPort;
        this.loadGameStatisticsPort = loadGameStatisticsPort;
        this.updateGameStatisticsPort = updateGameStatisticsPort;
    }

    @Override
    public void updateGameStatistics(MatchSession matchSession) {
        Optional<Player> optionalPlayer = loadPlayerPort.loadPlayerById(UUID.fromString("a7d9b1bc-b94d-4fa1-a1a0-65d7d4359634"));
        if (optionalPlayer.isEmpty()) {
            throw new IllegalArgumentException("Player not found");
        }
        Player player = optionalPlayer.get();
        Optional<GameStatistics> optionalGameStatistics = loadGameStatisticsPort.loadGameStatisticsByPlayerAndGameId(player, UUID.fromString("14910372-c39d-7de7-b05a-93f8166cf7af"));
        if (optionalGameStatistics.isEmpty()) {
            throw new IllegalArgumentException("GameStatistics not found");
        }
        GameStatistics gameStatistics = optionalGameStatistics.get();
        updateGameStatisticsPort.updateGameStatistics(gameStatistics);
    }
}
