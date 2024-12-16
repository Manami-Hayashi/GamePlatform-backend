package be.kdg.prog6.lobbyManagementContext.ports.in;

public class GameIdResponse {
    private String gameId;

    public GameIdResponse(String gameId) {
        this.gameId = gameId;
    }

    public String getGameId() {
        return gameId;
    }

    public void setGameId(String gameId) {
        this.gameId = gameId;
    }
}