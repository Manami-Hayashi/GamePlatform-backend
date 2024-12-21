package be.kdg.prog6.gameStatisticsContext.adapter.in;

import be.kdg.prog6.gameStatisticsContext.domain.*;
import be.kdg.prog6.gameStatisticsContext.port.in.GetScoreboardUseCase;
import be.kdg.prog6.gameStatisticsContext.port.in.UpdateGameStatisticsCommand;
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
    public void updateGameStatistics(@RequestBody UpdateGameStatisticsDto updateGameStatisticsDto) {
        UUID id = UUID.randomUUID();
        UUID gameId = UUID.fromString(updateGameStatisticsDto.gameId());
        List<UUID> players = new ArrayList<>();
        players.add(updateGameStatisticsDto.playerIds().get(0));
        players.add(updateGameStatisticsDto.playerIds().get(1));
        LocalDateTime startTime = updateGameStatisticsDto.startTime();
        LocalDateTime endTime = updateGameStatisticsDto.endTime();
        boolean isActive = updateGameStatisticsDto.isActive();
        String winner = updateGameStatisticsDto.winner();
        int scoreP1 = updateGameStatisticsDto.scoreP1();
        int scoreP2 = updateGameStatisticsDto.scoreP2();
        int movesMadeP1 = updateGameStatisticsDto.movesMadeP1();
        int movesMadeP2 = updateGameStatisticsDto.movesMadeP2();
        UpdateGameStatisticsCommand updateGameStatisticsCommand = new UpdateGameStatisticsCommand(id, gameId, players, startTime, endTime, isActive, winner, scoreP1, scoreP2, movesMadeP1, movesMadeP2);
        updateGameStatisticsUseCase.updateGameStatistics(updateGameStatisticsCommand);
    }
}
