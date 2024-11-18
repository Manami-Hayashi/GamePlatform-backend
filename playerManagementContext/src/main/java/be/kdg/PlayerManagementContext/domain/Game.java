package be.kdg.PlayerManagementContext.domain;

public class Game {
    private GameId gameId;
    private String gameName;
    private boolean isFavorite;

    public Game(GameId gameId, String gameName, boolean isFavorite) {
        this.gameId = gameId;
        this.gameName = gameName;
        this.isFavorite = isFavorite;
    }


}
