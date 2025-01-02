package be.kdg.prog6.gameStatisticsContext.adapter.in;

import java.util.UUID;

public record AchievementDto(int id, UUID playerId, UUID gameId, String name, String description, Boolean isLocked) {
    public AchievementDto {
        if (id < 0) {
            throw new IllegalArgumentException("Id must be positive");
        }
        if (playerId == null) {
            throw new IllegalArgumentException("PlayerId must not be null");
        }
        if (gameId == null) {
            throw new IllegalArgumentException("GameId must not be null");
        }
        if (name == null) {
            throw new IllegalArgumentException("Name must not be null");
        }
        if (description == null) {
            throw new IllegalArgumentException("Description must not be null");
        }
        if (isLocked == null) {
            isLocked = true;
        }
    }

    public AchievementDto(int id, UUID playerId, UUID gameId, String name, String description) {
        this(id, playerId, gameId, name, description, true);
    }
}
