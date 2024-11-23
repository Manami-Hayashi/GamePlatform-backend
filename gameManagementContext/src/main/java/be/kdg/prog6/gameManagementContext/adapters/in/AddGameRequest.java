package be.kdg.prog6.gameManagementContext.adapters.in;

public class AddGameRequest {
    private String gameName;
    private String description;

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}