package be.kdg.prog6.gameStatisticsContext.adapter.in;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record MatchSessionDto(
        UUID id,
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
    public MatchSessionDto {
        if (id == null) {
            throw new IllegalArgumentException("Id must not be null");
        }
        if (gameId == null) {
            throw new IllegalArgumentException("GameId must not be null");
        }
        if (players == null) {
            throw new IllegalArgumentException("Players must not be null");
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
