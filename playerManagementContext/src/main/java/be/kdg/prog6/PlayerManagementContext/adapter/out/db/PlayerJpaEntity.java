package be.kdg.prog6.PlayerManagementContext.adapter.out.db;

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

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<GameOwnedJpaEntity> gamesOwned = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @JoinTable(
            catalog = "player_management",
            name = "player_friends",
            joinColumns = @JoinColumn(name = "player_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    private List<FriendJpaEntity> friends = new ArrayList<>();

    public PlayerJpaEntity() {
    }

    public PlayerJpaEntity(UUID playerId, String name) {
        this.playerId = playerId;
        this.name = name;
    }

    // Constructor for Player entity
    public PlayerJpaEntity(UUID playerId, String name, List<FriendJpaEntity> friends, List<GameOwnedJpaEntity> gamesOwned) {
        this.playerId = playerId;
        this.name = name;
        this.friends = friends;
        this.gamesOwned = gamesOwned;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public String getName() {
        return name;
    }

    public List<GameOwnedJpaEntity> getGameOwned() {return gamesOwned;}

    public void setGamesOwned(List<GameOwnedJpaEntity> gameOwned) {
        this.gamesOwned = gameOwned != null ? gameOwned : new ArrayList<>();
    }

    public List<FriendJpaEntity> getFriends() {
        return friends;
    }

    public void setFriends(List<FriendJpaEntity> friends) {
        this.friends = friends != null ? friends : new ArrayList<>();
    }

    // Method to add a favorite game (optional)
    public void addFavoriteGame(GameOwnedJpaEntity game) {
        // You can add a specific "favorite" check here if needed
        this.gamesOwned.add(game);
    }
}
