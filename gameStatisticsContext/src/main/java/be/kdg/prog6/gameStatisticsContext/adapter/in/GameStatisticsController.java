package be.kdg.prog6.gameStatisticsContext.adapter.in;

import be.kdg.prog6.gameStatisticsContext.domain.*;
import be.kdg.prog6.gameStatisticsContext.port.in.GetScoreboardUseCase;
import be.kdg.prog6.gameStatisticsContext.port.in.UpdateGameStatisticsUseCase;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
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

    @PostMapping("/update")
    public void updateGameStatistics() {
        int id = 1;
        UUID gameId = UUID.fromString("14910372-c39d-7de7-b05a-93f8166cf7af");
        List<UUID> players = new ArrayList<>();
        players.add(UUID.fromString("a7d9b1bc-b94d-4fa1-a1a0-65d7d4359634"));
        players.add(UUID.fromString("b5c0f1b7-3971-4e66-b5ab-49a0f4a71b4d"));

        LocalDateTime startTime = LocalDateTime.now();
        LocalDateTime endTime = LocalDateTime.now();
        boolean isActive = true;
        String winner = "PLAYER1";
        int score = 10;
        int movesMade = 10;
        MatchSessionDto matchSessionDto = new MatchSessionDto(id, gameId, players, startTime, endTime, isActive, winner, score, movesMade);
        updateGameStatisticsUseCase.updateGameStatistics(matchSessionDto);
    }
}
