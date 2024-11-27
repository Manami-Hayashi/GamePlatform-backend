package be.kdg.prog6.gameStatisticsContext.adapter.in;

import be.kdg.prog6.gameStatisticsContext.domain.GameStatistics;
import be.kdg.prog6.gameStatisticsContext.domain.MatchSession;
import be.kdg.prog6.gameStatisticsContext.domain.PlayerId;
import be.kdg.prog6.gameStatisticsContext.port.in.GetScoreboardUseCase;
import be.kdg.prog6.gameStatisticsContext.port.in.UpdateGameStatisticsUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/game-statistics")
public class GameStatisticsController {
    private final GetScoreboardUseCase getScoreboardUseCase;
    private final UpdateGameStatisticsUseCase updateGameStatisticsUseCase;

    public GameStatisticsController(GetScoreboardUseCase getScoreboardUseCase, UpdateGameStatisticsUseCase updateGameStatisticsUseCase) {
        this.getScoreboardUseCase = getScoreboardUseCase;
        this.updateGameStatisticsUseCase = updateGameStatisticsUseCase;
    }

    @GetMapping("/{playerId}")
    public List<GameStatisticsDto> getScoreboards(@PathVariable String playerId) {
        List<GameStatistics> gameStatistics = getScoreboardUseCase.getScoreboard(new PlayerId(UUID.fromString(playerId)));
        List<GameStatisticsDto> gameStatisticsDtos = new ArrayList<>();
        for (GameStatistics gameStatistic : gameStatistics) {
            gameStatisticsDtos.add(new GameStatisticsDto(
                    gameStatistic.getGameId().id(),
                    gameStatistic.getTotalScore(),
                    gameStatistic.getTotalGamesPlayed(),
                    gameStatistic.getWins(),
                    gameStatistic.getLosses(),
                    gameStatistic.getDraws(),
                    gameStatistic.getWinLossRatio(),
                    gameStatistic.getTotalTimePlayed(),
                    gameStatistic.getHighestScore(),
                    gameStatistic.getMovesMade(),
                    gameStatistic.getAverageGameDuration()
            ));
        }
        return gameStatisticsDtos;
    }

    @GetMapping("/update")
    public void updateGameStatistics() {
        MatchSession matchSession = null;
        updateGameStatisticsUseCase.updateGameStatistics(matchSession);
    }
}
