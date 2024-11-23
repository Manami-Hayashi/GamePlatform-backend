package be.kdg.prog6.gameManagementContext.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Admin {
    private UUID adminId;
    private final List<Game> gameList;
    private final List<Player> playerList;

    public Admin(UUID adminId, List<Game> gameList, List<Player> playerList) {
        this.adminId = adminId;
        this.gameList = gameList != null ? gameList : new ArrayList<>();
        this.playerList = playerList != null ? playerList : new ArrayList<>();
    }

    public Admin() {
        this.gameList = new ArrayList<>();
        this.playerList = new ArrayList<>();
    }

    public UUID getAdminId() {
        return adminId;
    }

    public List<Game> getGameList() {
        return gameList;
    }

    public List<Player> getPlayerList() {
        return playerList;
    }

    public void createGame(GameId gameId, String gameName, String description) {
        Game game = new Game(gameId, gameName, description);
        gameList.add(game);
    }

    public void registerPlayer(Player player) {
        playerList.add(player);
    }
}