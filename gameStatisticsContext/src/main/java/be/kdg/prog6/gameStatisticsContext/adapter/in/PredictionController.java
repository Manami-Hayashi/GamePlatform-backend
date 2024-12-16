package be.kdg.prog6.gameStatisticsContext.adapter.in;

import be.kdg.prog6.gameStatisticsContext.domain.GameStatistics;
import be.kdg.prog6.gameStatisticsContext.port.in.GetPredictionForAdminUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/prediction")
public class PredictionController {
    private final GetPredictionForAdminUseCase getPredictionForAdminUseCase;


    public PredictionController(GetPredictionForAdminUseCase getPredictionForAdminUseCase) {
        this.getPredictionForAdminUseCase = getPredictionForAdminUseCase;
    }

/*    @GetMapping("/getWinProbability")
    public double getWinProbability(@RequestParam UUID playerId, @RequestParam UUID gameId) {
        return getPredictionForAdminUseCase.getWinProbability(playerId, gameId);
    }*/


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

}
