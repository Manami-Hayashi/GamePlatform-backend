package be.kdg.prog6.PlayerManagementContext.adapter.out.db;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(catalog="player_management", name="friends")
public class FriendJpaEntity {

    @Id
    @Column(name = "friend_id")
    private UUID friendId;

    @Column(name = "is_favorite")
    private boolean isFavorite;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private PlayerJpaEntity player;

    public FriendJpaEntity() {
    }

    public FriendJpaEntity(UUID friendId, boolean isFavorite) {
        this.friendId = friendId;
        this.isFavorite = isFavorite;
    }

    public UUID getFriendId() {
        return friendId;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public void setFriendId(UUID friendId) {
        this.friendId = friendId;
    }

    public PlayerJpaEntity getPlayer() {
        return player;
    }

    public void setPlayer(PlayerJpaEntity player) {
        this.player = player;
    }
}
