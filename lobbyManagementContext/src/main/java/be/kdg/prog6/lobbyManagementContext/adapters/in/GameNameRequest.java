package be.kdg.prog6.lobbyManagementContext.adapters.in;

public class GameNameRequest {
    private String gameName;

    public GameNameRequest() {
    }

    public GameNameRequest(String gameName) {
        this.gameName = gameName;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }
}