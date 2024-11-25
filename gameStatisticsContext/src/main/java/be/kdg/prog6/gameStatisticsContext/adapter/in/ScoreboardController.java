package be.kdg.prog6.gameStatisticsContext.adapter.in;

import be.kdg.prog6.gameStatisticsContext.domain.GameStatistics;
import be.kdg.prog6.gameStatisticsContext.port.in.GetGameStatisticsUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ScoreboardController {
    private final GetGameStatisticsUseCase getGameStatisticsUseCase;

    public ScoreboardController(GetGameStatisticsUseCase getGameStatisticsUseCase) {
        this.getGameStatisticsUseCase = getGameStatisticsUseCase;
    }

    @GetMapping("/game-statistics")
    public List<GameStatisticsDto> getGameStatistics() {
        List<GameStatistics> gameStatistics = getGameStatisticsUseCase.getGameStatistics();
        List<GameStatisticsDto> gameStatisticsDtos = new ArrayList<>();
        for (GameStatistics gameStatistic : gameStatistics) {
            gameStatisticsDtos.add(new GameStatisticsDto(gameStatistic.getGameId().id(), gameStatistic.getTotalScore(), gameStatistic.getMatchesPlayed().size()));
        }
        return gameStatisticsDtos;
    }

}
