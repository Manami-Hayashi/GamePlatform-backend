package be.kdg.prog6.gameStatisticsContext.adapter.in;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record UpdateGameStatisticsDto(
        int id,
        UUID gameId,
        List<UUID> playerIds,
        LocalDateTime startTime,
        LocalDateTime endTime,
        boolean isActive,
        String winner,
        int scoreP1,
        int scoreP2,
        int movesMade
) {
    public UpdateGameStatisticsDto {
        if (id < 0) {
            throw new IllegalArgumentException("Id must be positive");
        }
    }
}
