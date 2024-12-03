package be.kdg.prog6.gameStatisticsContext.adapter.in;

import be.kdg.prog6.gameStatisticsContext.domain.MatchSession;
import be.kdg.prog6.gameStatisticsContext.port.in.GetMatchHistoryCommand;
import be.kdg.prog6.gameStatisticsContext.port.in.GetMatchHistoryUseCase;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/match-history")
public class MatchHistoryController {
    private final GetMatchHistoryUseCase getMatchHistoryUseCase;

    public MatchHistoryController(GetMatchHistoryUseCase getMatchHistoryUseCase) {
        this.getMatchHistoryUseCase = getMatchHistoryUseCase;
    }

    @GetMapping("/{playerId}")
    public List<MatchSessionDto> getMatchHistory(@PathVariable String playerId) {
        List<GetMatchHistoryCommand> matchSessions = getMatchHistoryUseCase.getMatchHistory(UUID.fromString(playerId));
        List<MatchSessionDto> matchSessionDtos = new ArrayList<>();
        for (GetMatchHistoryCommand matchSession : matchSessions) {
            List<String> players = List.of(matchSession.players().get(0), matchSession.players().get(1));
            matchSessionDtos.add(new MatchSessionDto(matchSession.id(), matchSession.gameId(), players, matchSession.startTime(), matchSession.endTime(), matchSession.isActive(), matchSession.winner(), matchSession.scoreP1(), matchSession.scoreP2(), matchSession.movesMade()));
        }
        return matchSessionDtos;
    }
}
