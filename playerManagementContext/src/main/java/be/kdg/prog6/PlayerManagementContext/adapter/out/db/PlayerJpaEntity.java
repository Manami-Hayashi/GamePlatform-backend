package be.kdg.prog6.PlayerManagementContext.adapter.out.db;

import jakarta.persistence.*;

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

    public PlayerJpaEntity() {
    }

    public PlayerJpaEntity(UUID playerId, String name) {
        this.playerId = playerId;
        this.name = name;
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
