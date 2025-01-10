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

    @GetMapping("/{gameId}")
    public List<LeaderboardDto> getLeaderboard(@PathVariable String gameId) {
        List<GetLeaderboardCommand> leaderboardCommands = getLeaderboardUseCase.getLeaderboard(UUID.fromString(gameId));
        List<LeaderboardDto> leaderboardDtos = new ArrayList<>();
        for (GetLeaderboardCommand leaderboardCommand : leaderboardCommands) {
            leaderboardDtos.add(new LeaderboardDto(
                    leaderboardCommand.playerId()
            ));
        }
        return leaderboardDtos;
    }
}
