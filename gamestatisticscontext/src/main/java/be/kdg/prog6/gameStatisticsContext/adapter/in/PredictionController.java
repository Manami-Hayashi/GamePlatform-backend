package be.kdg.prog6.gameStatisticsContext.adapter.in;

import be.kdg.prog6.gameStatisticsContext.domain.GameId;
import be.kdg.prog6.gameStatisticsContext.domain.GameStatistics;
import be.kdg.prog6.gameStatisticsContext.domain.Player;
import be.kdg.prog6.gameStatisticsContext.port.in.GetGamesUseCase;
import be.kdg.prog6.gameStatisticsContext.port.in.GetPlayersUseCase;
import be.kdg.prog6.gameStatisticsContext.port.in.GetPredictionForAdminUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/prediction")
public class PredictionController {
    private final GetPredictionForAdminUseCase getPredictionForAdminUseCase;
    private final GetGamesUseCase getGamesUseCase;
    private final GetPlayersUseCase getPlayersUseCase;

    public PredictionController(GetPredictionForAdminUseCase getPredictionForAdminUseCase, GetGamesUseCase getGamesUseCase, GetPlayersUseCase getPlayersUseCase) {
        this.getPredictionForAdminUseCase = getPredictionForAdminUseCase;
        this.getGamesUseCase = getGamesUseCase;
        this.getPlayersUseCase = getPlayersUseCase;
    }

    @GetMapping("/getGameStatisticsOrPrediction")
    public ResponseEntity<Object> getGameStatisticsOrPrediction(
            @RequestParam UUID playerId,
            @RequestParam UUID gameId,
            @RequestParam(required = false) boolean predict,
            @RequestHeader("Authorization") String authorizationHeader) {  // Get the token from the request
        if (predict) {
            // Pass the token to the prediction use case
            double winProbability = getPredictionForAdminUseCase.getWinProbability(playerId, gameId, authorizationHeader);
            return ResponseEntity.ok(Map.of("winProbability", winProbability));
        } else {
            // Return game statistics
            GameStatistics gameStatistics = getPredictionForAdminUseCase.getGameStatistics(playerId, gameId);
            return ResponseEntity.ok(gameStatistics);
        }
    }

    @GetMapping("/getGameStatistics")
    public GameStatistics getGameStatistics(@RequestParam UUID playerId, @RequestParam UUID gameId) {
        return getPredictionForAdminUseCase.getGameStatistics(playerId, gameId);
    }

    @GetMapping("/players")
    public ResponseEntity<List<Player>> getPlayers() {
        List<Player> players = getPlayersUseCase.getPlayers();
        return ResponseEntity.ok(players);
    }

    @GetMapping("/games")
    public ResponseEntity<List<GameId>> getGames() {
        List<GameId> games = getGamesUseCase.getGames();
        return ResponseEntity.ok(games);
    }
}
