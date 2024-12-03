package be.kdg.prog6.gameStatisticsContext.core;

import be.kdg.prog6.gameStatisticsContext.domain.GameStatistics;
import be.kdg.prog6.gameStatisticsContext.domain.MatchSession;
import be.kdg.prog6.gameStatisticsContext.domain.Player;
import be.kdg.prog6.gameStatisticsContext.domain.Winner;
import be.kdg.prog6.gameStatisticsContext.port.in.GetMatchHistoryCommand;
import be.kdg.prog6.gameStatisticsContext.port.in.GetMatchHistoryUseCase;
import be.kdg.prog6.gameStatisticsContext.port.out.LoadAllGameStatisticsPort;
import be.kdg.prog6.gameStatisticsContext.port.out.LoadMatchSessionsPort;
import be.kdg.prog6.gameStatisticsContext.port.out.LoadPlayerPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GetMatchHistoryUseCaseImpl implements GetMatchHistoryUseCase {
    private final static Logger LOGGER = LoggerFactory.getLogger(GetMatchHistoryUseCaseImpl.class);
    private final LoadMatchSessionsPort loadMatchSessionsPort;
    private final LoadAllGameStatisticsPort loadAllGameStatisticsPort;
    private final LoadPlayerPort loadPlayerPort;

    public GetMatchHistoryUseCaseImpl(LoadMatchSessionsPort loadMatchSessionsPort, LoadAllGameStatisticsPort loadAllGameStatisticsPort, LoadPlayerPort loadPlayerPort) {
        this.loadMatchSessionsPort = loadMatchSessionsPort;
        this.loadAllGameStatisticsPort = loadAllGameStatisticsPort;
        this.loadPlayerPort = loadPlayerPort;
    }

    @Override
    public List<GetMatchHistoryCommand> getMatchHistory(UUID playerId) {
        List<GameStatistics> gameStatistics = loadAllGameStatisticsPort.loadAllGameStatistics();
        List<GetMatchHistoryCommand> matchSessionCommands = new ArrayList<>();
        for (GameStatistics gameStat : gameStatistics) {
            try {
                if (gameStat.getPlayerId().id().equals(playerId)) {
                    List<MatchSession> matchSessions = loadMatchSessionsPort.loadMatchSessionsByGameStatistics(gameStat);
                    for (MatchSession matchSession : matchSessions) {
                        Optional<Player> optionalPlayer1 = loadPlayerPort.loadPlayerById(gameStat.getPlayerId().id());
                        if (optionalPlayer1.isEmpty()) {
                            continue;
                        }
                        String player1 = optionalPlayer1.get().getName();
                        Optional<Player> optionalPlayer2 = loadPlayerPort.loadPlayerById(gameStat.getPlayerId().id());
                        if (optionalPlayer2.isEmpty()) {
                            continue;
                        }
                        String player2 = optionalPlayer2.get().getName();
                        List<String> players = new ArrayList<>();
                        players.add(player1);
                        players.add(player2);
                        String winner;
                        if (matchSession.getWinner().equals(Winner.PLAYER1)) {
                            winner = player1;
                        } else if (matchSession.getWinner().equals(Winner.PLAYER2)) {
                            winner = player2;
                        } else {
                            winner = "Draw";
                        }
                        matchSessionCommands.add(new GetMatchHistoryCommand(
                                matchSession.getId(),
                                matchSession.getGameId().id(),
                                players,
                                matchSession.getStartTime(),
                                matchSession.getEndTime(),
                                matchSession.isActive(),
                                winner,
                                matchSession.getScoreP1(),
                                matchSession.getScoreP2(),
                                matchSession.getMovesMadeP1(),
                                matchSession.getMovesMadeP2()
                        ));
                    }
                }
            } catch (NullPointerException ignored) {
            }
        }
        return matchSessionCommands;
    }
}
