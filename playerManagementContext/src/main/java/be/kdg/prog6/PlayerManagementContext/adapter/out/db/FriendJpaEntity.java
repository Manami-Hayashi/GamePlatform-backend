package be.kdg.prog6.PlayerManagementContext.adapter.out.db;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
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

    @Column(name = "friend_request_status")
    private String friendRequestStatus;

    @ManyToMany(mappedBy = "friends")
    private List<PlayerJpaEntity> players = new ArrayList<>();

    public FriendJpaEntity() {
    }

    public FriendJpaEntity(UUID playerId, String name, boolean isFavorite, String friendRequestStatus, List<PlayerJpaEntity> players) {
        this.playerId = playerId;
        this.name = name;
        this.isFavorite = isFavorite;
        this.friendRequestStatus = friendRequestStatus;
        this.players = players;
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

    public String getFriendRequestStatus() {
        return friendRequestStatus;
    }

    public void setFriendRequestStatus(String accepted) {
        friendRequestStatus = accepted;
    }

    public void setPlayerId(UUID friendId) {
        this.playerId = friendId;
    }

    public List<PlayerJpaEntity> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerJpaEntity> players) {
        this.players = players;
    }
}
