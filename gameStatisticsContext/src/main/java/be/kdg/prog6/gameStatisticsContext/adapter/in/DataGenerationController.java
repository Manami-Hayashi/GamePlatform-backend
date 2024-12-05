package be.kdg.prog6.gameStatisticsContext.adapter.in;


import be.kdg.prog6.gameStatisticsContext.port.in.DataGenerationUseCase;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataGenerationController {
    private final DataGenerationUseCase dataGenerationUseCase;

    public DataGenerationController(DataGenerationUseCase dataGenerationUseCase) {
        this.dataGenerationUseCase = dataGenerationUseCase;
    }

    @PostMapping("/generate")
    public String generateData(@RequestParam int players, @RequestParam int gamesPerPlayer) {
        dataGenerationUseCase.generatePlayers(players);
        dataGenerationUseCase.generateGameStatistics(gamesPerPlayer);
        return "Data generation completed successfully.";
    }
}
