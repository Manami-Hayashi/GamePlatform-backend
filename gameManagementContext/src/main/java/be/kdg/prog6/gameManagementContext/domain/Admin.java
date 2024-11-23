package be.kdg.prog6.gameManagementContext.domain;

import java.util.List;
import java.util.UUID;

public class Admin {
    private UUID adminId;
    private List<Game> gameList;
    private List<Player> playerList;

    public Admin(UUID adminId, List<Game> gameList, List<Player> playerList) {
        this.adminId = adminId;
        this.gameList = gameList;
        this.playerList = playerList;
    }
    public Admin() {
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
        {
            Game game = new Game(gameId, gameName, description);
            gameList.add(game);
        }
    }

    public void registerPlater(Player player){
        playerList.add(player);
    }
}
