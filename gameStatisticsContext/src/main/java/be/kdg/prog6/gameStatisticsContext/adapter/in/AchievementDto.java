package be.kdg.prog6.gameStatisticsContext.adapter.in;

import java.util.UUID;

public record AchievementDto(int id, UUID playerId, String name, String description, boolean isLocked) {
}
