package be.kdg.prog6.gameStatisticsContext.adapter.in;

import be.kdg.prog6.gameStatisticsContext.domain.*;
import be.kdg.prog6.gameStatisticsContext.port.in.GetScoreboardUseCase;
import be.kdg.prog6.gameStatisticsContext.port.in.UpdateGameCommand;
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
        List<GameStatistics> gameStatistics = getScoreboardUseCase.getMatchHistory(new PlayerId(UUID.fromString(playerId)));
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
    public void updateGameStatistics(@RequestBody UpdateGameCommand updateGameCommand) {
        int id = updateGameCommand.id();
        UUID gameId = UUID.fromString(updateGameCommand.gameId());
        List<UUID> players = new ArrayList<>();
        players.add(updateGameCommand.playerIds().get(0));
        players.add(updateGameCommand.playerIds().get(1));
        LocalDateTime startTime = updateGameCommand.startTime();
        LocalDateTime endTime = updateGameCommand.endTime();
        boolean isActive = updateGameCommand.isActive();
        String winner = updateGameCommand.winner();
        int scoreP1 = updateGameCommand.scoreP1();
        int scoreP2 = updateGameCommand.scoreP2();
        int movesMade = updateGameCommand.movesMade();
        UpdateGameStatisticsDto updateGameStatisticsDto = new UpdateGameStatisticsDto(id, gameId, players, startTime, endTime, isActive, winner, scoreP1, scoreP2, movesMade);
        updateGameStatisticsUseCase.updateGameStatistics(updateGameStatisticsDto);
    }
}
