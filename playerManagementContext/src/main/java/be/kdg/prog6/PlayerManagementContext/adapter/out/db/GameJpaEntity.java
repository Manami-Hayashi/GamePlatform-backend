package be.kdg.prog6.PlayerManagementContext.adapter.out.db;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.rmi.server.UID;
import java.util.UUID;

@Entity
@Table(catalog="player_management", name="games")
public class GameJpaEntity {

    @Id
    private UUID gameId;
    private String gameName;
    private boolean isFavorite;

    public GameJpaEntity() {
    }

    public GameJpaEntity(UUID gameId, String gameName, boolean isFavorite) {
        this.gameId = gameId;
        this.gameName = gameName;
        this.isFavorite = isFavorite;
    }

    public UUID getGameId() {return gameId;}

    public void setGameId(UUID gameId) {this.gameId = gameId;}

    public String getGameName() {return gameName;}

    public void setGameName(String gameName) {this.gameName = gameName;}

    public boolean isFavorite() {return isFavorite;}

    public void setFavorite(boolean favorite) {isFavorite = favorite;}
}
