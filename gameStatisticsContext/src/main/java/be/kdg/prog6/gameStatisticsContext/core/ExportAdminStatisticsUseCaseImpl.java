package be.kdg.prog6.gameStatisticsContext.core;

import be.kdg.prog6.gameStatisticsContext.domain.GameStatistics;
import be.kdg.prog6.gameStatisticsContext.domain.Player;
import be.kdg.prog6.gameStatisticsContext.port.in.ExportAdminStatisticsUseCase;
import be.kdg.prog6.gameStatisticsContext.port.out.LoadPlayersPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExportAdminStatisticsUseCaseImpl implements ExportAdminStatisticsUseCase {
    private final LoadPlayersPort loadPlayersPort;

    public ExportAdminStatisticsUseCaseImpl(final LoadPlayersPort loadPlayersPort) {
        this.loadPlayersPort = loadPlayersPort;
    }

    @Override
    public void exportAdminStatistics() {
        List<Player> players = loadPlayersPort.loadPlayers();
        List<GameStatistics> gameStatistics = players.stream()
                .map(Player::getGameStatistics)
                .flatMap(List::stream)
                .toList();
        //statisticsExporter.exportToCSV(gameStatistics, "adminStatistics.csv");
    }
}
