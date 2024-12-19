package be.kdg.prog6.playerManagementContext.domain;

public class Game {
    private GameId gameId;
    private String gameName;
    private boolean isFavorite;

    public Game(GameId gameId, String gameName, boolean isFavorite) {
        this.gameId = gameId;
        this.gameName = gameName;
        this.isFavorite = isFavorite;
    }

    public Game(GameId gameId, String gameName) {
        this.gameId = gameId;
        this.gameName = gameName;
        this.isFavorite = false;
    }

    public GameId getGameId() {return gameId;}

    public void setGameId(GameId gameId) {this.gameId = gameId;}

    public String getGameName() {return gameName;}

    public void setGameName(String gameName) {this.gameName = gameName;}

    public boolean isFavorite() {return isFavorite;}

    public void setFavorite(boolean favorite) {isFavorite = favorite;}
}
