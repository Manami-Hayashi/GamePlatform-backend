package be.kdg.prog6.gameStatisticsContext.adapter.in;

import be.kdg.prog6.gameStatisticsContext.port.in.GetLeaderboardCommand;
import be.kdg.prog6.gameStatisticsContext.port.in.GetLeaderboardUseCase;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/leaderboard")
public class LeaderboardController {
    private final GetLeaderboardUseCase getLeaderboardUseCase;

    public LeaderboardController(GetLeaderboardUseCase getLeaderboardUseCase) {
        this.getLeaderboardUseCase = getLeaderboardUseCase;
    }

    @GetMapping
    public List<LeaderboardDto> getLeaderboard() {
        List<GetLeaderboardCommand> leaderboardCommands = getLeaderboardUseCase.getLeaderboard();
        List<LeaderboardDto> leaderboardDtos = new ArrayList<>();
        for (GetLeaderboardCommand leaderboardCommand : leaderboardCommands) {
            leaderboardDtos.add(new LeaderboardDto(
                    leaderboardCommand.gameName(), leaderboardCommand.playerName(), leaderboardCommand.wins(), leaderboardCommand.totalGamesPlayed()
            ));
        }
        return leaderboardDtos;
    }
}
