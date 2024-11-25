package be.kdg.prog6.lobbyManagementContext.domain;

public class Player {
    private final PlayerId playerId;
    private final String name;

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
