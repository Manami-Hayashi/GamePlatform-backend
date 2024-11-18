package be.kdg.prog6.gameManagementContext.domain;

public class Game {
    private GameId gameId;
    private String gameName;

    public Game(GameId gameId, String gameName) {
        this.gameId = gameId;
        this.gameName = gameName;
    }

    public GameId getGameId() {
        return gameId;
    }

    public String getGameName() {
        return gameName;
    }
}
