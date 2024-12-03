package be.kdg.prog6.gameStatisticsContext.port.in;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record UpdateGameCommand(
        int id,
        String gameId,
        List<UUID> playerIds,
        LocalDateTime startTime,
        LocalDateTime endTime,
        boolean isActive,
        String winner,
        int scoreP1,
        int scoreP2,
        int movesMade
) {
    public UpdateGameCommand {
        if (id < 0) {
            throw new IllegalArgumentException("Id must be positive");
        }
    }
}
