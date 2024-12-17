package be.kdg.prog6.gameStatisticsContext.domain;

import java.util.List;

public class Leaderboard {
    private final GameId gameId;
    List<Player> playerList;

    public Leaderboard(final GameId gameId, List<Player> playerList) {
        this.gameId = gameId;
        this.playerList = playerList;
    }

    public GameId getGameId() {
        return gameId;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(List<Player> playerList) {
        this.playerList = playerList;
    }
}
