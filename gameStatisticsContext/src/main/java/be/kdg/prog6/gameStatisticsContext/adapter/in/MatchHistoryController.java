package be.kdg.prog6.gameStatisticsContext.adapter.in;

import be.kdg.prog6.gameStatisticsContext.domain.MatchSession;
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
        List<MatchSession> matchSessions = getMatchHistoryUseCase.getMatchHistory(UUID.fromString(playerId));
        List<MatchSessionDto> matchSessionDtos = new ArrayList<>();
        for (MatchSession matchSession : matchSessions) {
            List<UUID> playerIds = List.of(matchSession.getGameStatistics().get(0).getPlayerId().id(), matchSession.getGameStatistics().get(1).getPlayerId().id());
            matchSessionDtos.add(new MatchSessionDto(matchSession.getId(), matchSession.getGameId().id(), playerIds, matchSession.getStartTime(), matchSession.getEndTime(), matchSession.isActive(), matchSession.getWinner().toString(), matchSession.getScore(), matchSession.getMovesMade()));
        }
        return matchSessionDtos;
    }
}
