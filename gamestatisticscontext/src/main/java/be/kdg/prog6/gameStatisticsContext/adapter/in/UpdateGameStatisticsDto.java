package be.kdg.prog6.gameStatisticsContext.adapter.in;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record UpdateGameStatisticsDto(
        String sessionId, // Add this
        String gameId,
        List<UUID> playerIds,
        LocalDateTime startTime,
        LocalDateTime endTime,
        boolean isActive,
        String winner,
        int scoreP1,
        int scoreP2,
        int movesMadeP1,
        int movesMadeP2
) {}