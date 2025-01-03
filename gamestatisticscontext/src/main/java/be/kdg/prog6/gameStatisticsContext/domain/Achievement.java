package be.kdg.prog6.gameStatisticsContext.domain;

public class Achievement {
    private final PlayerId playerId;
    private final GameId gameId;
    private final String name;
    private final String description;
    private boolean isLocked;

    public Achievement(PlayerId playerId, GameId gameId, String name, String description) {
        this.playerId = playerId;
        this.gameId = gameId;
        this.name = name;
        this.description = description;
        this.isLocked = true;
    }

    public Achievement(PlayerId playerId, GameId gameId, String name, String description, boolean isLocked) {
        this.playerId = playerId;
        this.gameId = gameId;
        this.name = name;
        this.description = description;
        this.isLocked = isLocked;
    }

    public PlayerId getPlayerId() {
        return playerId;
    }

    public GameId getGameId() {
        return gameId;
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
