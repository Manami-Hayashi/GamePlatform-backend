package be.kdg.prog6.PlayerManagementContext.adapter.out.db;

import be.kdg.prog6.PlayerManagementContext.domain.Friend;
import be.kdg.prog6.PlayerManagementContext.domain.GameId;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(catalog="player_management", name="players")
public class PlayerJpaEntity{
    @Id
    @Column(name="player_id")
    private UUID playerId;

    @Column(name="name")
    private String name;

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<GameOwnedJpaEntity> gameOwned;

    @OneToMany(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FriendJpaEntity> friends;

    @OneToMany
    @JoinColumn(name="player_id")
    private List<GameOwnedJpaEntity> favoriteGames;

    public PlayerJpaEntity() {
    }

    public PlayerJpaEntity(UUID playerId, String name) {
        this.playerId = playerId;
        this.name = name;
    }

    public PlayerJpaEntity(UUID id, String name, List<FriendJpaEntity> friends, List<GameOwnedJpaEntity> favoriteGames) {
        this.playerId = id;
        this.name = name;
        this.friends = friends;
        this.favoriteGames = favoriteGames;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public String getName() {
        return name;
    }

    public List<GameOwnedJpaEntity> getGameOwned() {return gameOwned;}

    public void setGameOwned(List<GameOwnedJpaEntity> gameOwned) {this.gameOwned = gameOwned;}

}
