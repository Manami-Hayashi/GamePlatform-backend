package be.kdg.storeContext.domain;

public class Player {
    private PlayerId playerId;
    private String name;

    public Player(PlayerId playerId, String name) {
        this.playerId = playerId;
        this.name = name;
    }

    public PlayerId getPlayerId() {
        return playerId;
    }

    public String getName() {
        return name;
    }
}
