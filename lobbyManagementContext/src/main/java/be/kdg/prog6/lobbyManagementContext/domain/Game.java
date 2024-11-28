package be.kdg.prog6.lobbyManagementContext.domain;

public class Game {
    private final GameId gameId;
    private final String gameName;

    public Game(GameId gameId, String gameName) {
        this.gameId = gameId;
        this.gameName = gameName;
    }

    public Game() {
        this.gameId = null;
        this.gameName = null;
    }

    public GameId getGameId() {
        return gameId;
    }

    public String getGameName() {
        return gameName;
    }


}
