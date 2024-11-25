package be.kdg.prog6.gameStatisticsContext.domain;

import java.util.UUID;

public class Achievement {
    private UUID achievementId;
    private PlayerId playerId;
    private String name;
    private String description;
    private boolean isLocked;

    public Achievement(UUID achievementId, PlayerId playerId, String name, String description, boolean isLocked) {
        this.achievementId = achievementId;
        this.playerId = playerId;
        this.name = name;
        this.description = description;
        this.isLocked = isLocked;
    }


    public void unLock() {
        isLocked = false;
    }


    public UUID getAchievementId() {
        return achievementId;
    }

    public PlayerId getPlayerId() {
        return playerId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isLocked() {
        return isLocked;
    }
}
