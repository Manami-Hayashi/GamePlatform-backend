package be.kdg.prog6.gameStatisticsContext.adapter.in;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record UpdateGameStatisticsDto(
        String id,
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
) {
    public UpdateGameStatisticsDto {
        if (id == null) {
            throw new IllegalArgumentException("Id must not be null");
        }
        if (gameId == null) {
            throw new IllegalArgumentException("GameId must not be null");
        }
        if (playerIds == null) {
            throw new IllegalArgumentException("PlayerIds must not be null");
        }
        if (startTime == null) {
            throw new IllegalArgumentException("StartTime must not be null");
        }
        if (endTime == null) {
            throw new IllegalArgumentException("EndTime must not be null");
        }
        if (winner == null) {
            throw new IllegalArgumentException("Winner must not be null");
        }
    }
}
