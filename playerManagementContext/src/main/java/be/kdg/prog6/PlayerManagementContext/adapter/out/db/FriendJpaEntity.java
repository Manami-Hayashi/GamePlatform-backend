package be.kdg.prog6.PlayerManagementContext.adapter.out.db;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(catalog="player_management", name="friends")
public class FriendJpaEntity {
    @Id
    @Column(name = "player_id")
    private UUID playerId;

    @Column(name = "is_favorite")
    private boolean isFavorite;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private PlayerJpaEntity player;

    public FriendJpaEntity() {
    }

    public FriendJpaEntity(UUID playerId, boolean isFavorite) {
        this.playerId = playerId;
        this.isFavorite = isFavorite;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public void setPlayerId(UUID friendId) {
        this.playerId = friendId;
    }

    public PlayerJpaEntity getPlayer() {
        return player;
    }

    public void setPlayer(PlayerJpaEntity player) {
        this.player = player;
    }
}
