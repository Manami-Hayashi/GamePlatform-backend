package be.kdg.prog6.gameStatisticsContext.core;

import be.kdg.prog6.gameStatisticsContext.domain.GameStatistics;
import be.kdg.prog6.gameStatisticsContext.domain.MatchSession;
import be.kdg.prog6.gameStatisticsContext.port.in.GetMatchHistoryUseCase;
import be.kdg.prog6.gameStatisticsContext.port.out.LoadAllGameStatisticsPort;
import be.kdg.prog6.gameStatisticsContext.port.out.LoadMatchSessionsPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class GetMatchHistoryUseCaseImpl implements GetMatchHistoryUseCase {
    private final static Logger LOGGER = LoggerFactory.getLogger(GetMatchHistoryUseCaseImpl.class);
    private final LoadMatchSessionsPort loadMatchSessionsPort;
    private final LoadAllGameStatisticsPort loadAllGameStatisticsPort;

    public GetMatchHistoryUseCaseImpl(LoadMatchSessionsPort loadMatchSessionsPort, LoadAllGameStatisticsPort loadAllGameStatisticsPort) {
        this.loadMatchSessionsPort = loadMatchSessionsPort;
        this.loadAllGameStatisticsPort = loadAllGameStatisticsPort;
    }

    @Override
    public List<MatchSession> getMatchHistory(UUID playerId) {
        List<GameStatistics> gameStatistics = loadAllGameStatisticsPort.loadAllGameStatistics();
        List<MatchSession> matchSessions = new ArrayList<>();
        for (GameStatistics gameStat : gameStatistics) {
            try {
                if (gameStat.getPlayerId().id().equals(playerId)) {
                    matchSessions.addAll(loadMatchSessionsPort.loadMatchSessionsByGameStatistics(gameStat));
                }
            } catch (NullPointerException ignored) {
            }
        }
        return matchSessions;
    }
}
