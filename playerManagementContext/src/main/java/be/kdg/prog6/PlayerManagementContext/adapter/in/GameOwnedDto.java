package be.kdg.prog6.PlayerManagementContext.adapter.in;

import java.util.UUID;

public class GameOwnedDto {
    private String gameId;
    private String gameName;
    private boolean isFavorite;

    public GameOwnedDto() {
    }

    public GameOwnedDto(String gameId, String gameName, boolean isFavorite) {
        this.gameId = gameId;
        this.gameName = gameName;
        this.isFavorite = isFavorite;
    }

    public String getGameId() {return gameId;}

    public String getGameName() {return gameName;}

    public boolean isFavorite() {return isFavorite;}
}
