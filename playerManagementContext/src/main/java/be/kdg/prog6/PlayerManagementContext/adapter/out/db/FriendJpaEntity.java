package be.kdg.prog6.PlayerManagementContext.adapter.out.db;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(catalog="player_management", name="friends")
public class FriendJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(name = "friend_player_id")
    private UUID playerId;

    @Column(name = "name")
    private String name;

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

    public FriendJpaEntity(UUID playerId, boolean isFavorite, PlayerJpaEntity player) {
        this.playerId = playerId;
        this.isFavorite = isFavorite;
        this.player = player;
    }

    public FriendJpaEntity(UUID playerId, String name, boolean isFavorite) {
        this.playerId = playerId;
        this.name = name;
        this.isFavorite = isFavorite;
    }

    public FriendJpaEntity(UUID playerId, String name, boolean isFavorite, PlayerJpaEntity player) {
        this.playerId = playerId;
        this.name = name;
        this.isFavorite = isFavorite;
        this.player = player;
    }

    public int getId() {
        return id;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
