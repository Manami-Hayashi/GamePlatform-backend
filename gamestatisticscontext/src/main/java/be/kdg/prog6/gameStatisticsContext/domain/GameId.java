package be.kdg.prog6.gameStatisticsContext.domain;

import java.util.UUID;

public record GameId(UUID id, String name) {
    public GameId(UUID id) {
        this(id, null); // Default constructor for backward compatibility
    }
}
