package be.kdg.prog6.gameStatisticsContext.port.in;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record GetMatchHistoryCommand(
        int id,
        UUID gameId,
        List<String> players,
        LocalDateTime startTime,
        LocalDateTime endTime,
        boolean isActive,
        String winner,
        int scoreP1,
        int scoreP2,
        int movesMadeP1,
        int movesMadeP2

) {
}
