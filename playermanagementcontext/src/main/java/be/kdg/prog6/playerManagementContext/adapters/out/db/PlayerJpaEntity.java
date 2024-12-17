package be.kdg.prog6.playerManagementContext.adapters.out.db;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(catalog="player_management", name="players")
public class PlayerJpaEntity{
    @Id
    @Column(name = "player_id")
    private UUID playerId;

    @Column(name="name")
    private String name;

    @OneToMany(mappedBy = "requester", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FriendJpaEntity> friendsInitiated = new ArrayList<>();

    @OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FriendJpaEntity> friendsReceived = new ArrayList<>();

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<GameOwnedJpaEntity> gamesOwned = new ArrayList<>();

    public PlayerJpaEntity() {
    }

    public PlayerJpaEntity(UUID playerId, String name) {
        this.playerId = playerId;
        this.name = name;
    }

    public PlayerJpaEntity(UUID playerId, String name, List<FriendJpaEntity> friends) {
        this.playerId = playerId;
        this.name = name;
        this.friendsInitiated = friends;
    }

    public PlayerJpaEntity(UUID playerId, String name, List<FriendJpaEntity> friendsInitiated, List<FriendJpaEntity> friendsReceived) {
        this.playerId = playerId;
        this.name = name;
        this.friendsInitiated = friendsInitiated;
        this.friendsReceived = friendsReceived;
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

    public List<FriendJpaEntity> getFriendsInitiated() {
        return friendsInitiated;
    }

    public void setFriendsInitiated(List<FriendJpaEntity> friendsInitiated) {
        this.friendsInitiated = friendsInitiated != null ? friendsInitiated : new ArrayList<>();
    }

    public List<FriendJpaEntity> getFriendsReceived() {
        return friendsReceived;
    }

    public void setFriendsReceived(List<FriendJpaEntity> friendsReceived) {
        this.friendsReceived = friendsReceived != null ? friendsReceived : new ArrayList<>();
    }

    public List<GameOwnedJpaEntity> getGameOwned() {return gamesOwned;}

    public void setGamesOwned(List<GameOwnedJpaEntity> gameOwned) {
        this.gamesOwned = gameOwned != null ? gameOwned : new ArrayList<>();
    }

    // Method to add a favorite game (optional)
    public void addFavoriteGame(GameOwnedJpaEntity game) {
        // You can add a specific "favorite" check here if needed
        this.gamesOwned.add(game);
    }
}
