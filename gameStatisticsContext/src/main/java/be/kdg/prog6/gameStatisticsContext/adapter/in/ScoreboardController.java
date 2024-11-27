package be.kdg.prog6.gameStatisticsContext.adapter.in;

import be.kdg.prog6.gameStatisticsContext.domain.GameStatistics;
import be.kdg.prog6.gameStatisticsContext.domain.PlayerId;
import be.kdg.prog6.gameStatisticsContext.port.in.GetScoreboardUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class ScoreboardController {
    private final GetScoreboardUseCase getScoreboardUseCase;

    public ScoreboardController(GetScoreboardUseCase getScoreboardUseCase) {
        this.getScoreboardUseCase = getScoreboardUseCase;
    }

    @GetMapping("/scoreboards/{playerId}")
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
}
