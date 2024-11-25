package be.kdg.prog6.gameStatisticsContext.domain;

import java.util.List;

public class Leaderboard {
    private GameId gameId;
    List<Player> playerList;

    public Leaderboard(GameId gameId, List<Player> playerList) {
        this.gameId = gameId;
        this.playerList = playerList;
    }

    public GameId getGameId() {
        return gameId;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }
}
