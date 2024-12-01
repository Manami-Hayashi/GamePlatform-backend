package be.kdg.prog6.PlayerManagementContext.adapter.out.db;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(catalog="player_management", name="games")
public class GameOwnedJpaEntity {

    @Id
    private UUID gameId;
    @Column(name="game_name")
    private String gameName;
    @Column(name="is_favorite")
    private boolean isFavorite;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id")
    private PlayerJpaEntity player;

    public GameOwnedJpaEntity() {
    }

    public GameOwnedJpaEntity(UUID gameId, String gameName, boolean isFavorite) {
        this.gameId = UUID.randomUUID();
        this.gameName = gameName;
        this.isFavorite = isFavorite;
    }

    public UUID getGameId() {return gameId;}

    public void setGameId(UUID gameId) {this.gameId = gameId;}

    public String getGameName() {return gameName;}

    public void setGameName(String gameName) {this.gameName = gameName;}

    public boolean isFavorite() {return isFavorite;}

    public void setFavorite(boolean favorite) {isFavorite = favorite;}

    public PlayerJpaEntity getPlayer() {return player;}

    public void setPlayer(PlayerJpaEntity player) {this.player = player;}
}
