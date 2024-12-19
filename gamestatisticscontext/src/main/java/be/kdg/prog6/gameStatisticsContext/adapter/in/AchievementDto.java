package be.kdg.prog6.gameStatisticsContext.adapter.in;

import java.util.UUID;

public record AchievementDto(int id, UUID playerId, String name, String description, boolean isLocked) {
    public AchievementDto {
        if (id < 0) {
            throw new IllegalArgumentException("Id must be positive");
        }
        if (playerId == null) {
            throw new IllegalArgumentException("PlayerId must not be null");
        }
        if (name == null) {
            throw new IllegalArgumentException("Name must not be null");
        }
        if (description == null) {
            throw new IllegalArgumentException("Description must not be null");
        }
    }
}
