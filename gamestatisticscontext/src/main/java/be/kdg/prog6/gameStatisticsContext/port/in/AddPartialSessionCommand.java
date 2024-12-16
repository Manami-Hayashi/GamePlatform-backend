package be.kdg.prog6.gameStatisticsContext.port.in;

import java.time.LocalDateTime;
import java.util.UUID;

public record AddPartialSessionCommand(UUID sessionId, UUID gameId, LocalDateTime startTime, boolean isActive, UUID playerId1, UUID playerId2) {
}