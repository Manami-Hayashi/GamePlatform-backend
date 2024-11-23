package be.kdg.prog6.gameManagementContext.domain;

public class Game {
    private final GameId gameId;
    private final String gameName;
    private final String description;

    public Game(GameId gameId, String gameName, String description) {
        this.gameId = gameId;
        this.gameName = gameName;
        this.description = description;
    }

    public GameId getGameId() {
        return gameId;
    }

    public String getGameName() {
        return gameName;
    }

    public String getDescription() {
        return description;
    }
}