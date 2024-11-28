package be.kdg.prog6.gameStatisticsContext.adapter.in;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record MatchSessionDto(
        int id,
        UUID gameId,
        List<UUID> players,
        LocalDateTime startTime,
        LocalDateTime endTime,
        boolean isActive,
        String winner,
        int score,
        int movesMade
) {
}
