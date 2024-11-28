package be.kdg.prog6.gameStatisticsContext.port.in;

import java.time.LocalDateTime;
import java.util.List;

public record UpdateGameCommand(
        int id,
        String gameId,
        List<String> playerIds,
        LocalDateTime startTime,
        LocalDateTime endTime,
        boolean isActive,
        String winner,
        int score,
        int movesMade
) {
    public UpdateGameCommand {
        if (id < 0) {
            throw new IllegalArgumentException("Id must be positive");
        }
    }
}
