package be.kdg.prog6.gameStatisticsContext.domain;

public class Achievement {
    private final int id;
    private final PlayerId playerId;
    private final String name;
    private final String description;
    private boolean isLocked;

    public Achievement(final int id, PlayerId playerId, final String name, final String description, boolean isLocked) {
        this.id = id;
        this.playerId = playerId;
        this.name = name;
        this.description = description;
        this.isLocked = isLocked;
    }

    public int getId() {
        return id;
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

    public void unlock() {
        this.isLocked = false;
    }
}
