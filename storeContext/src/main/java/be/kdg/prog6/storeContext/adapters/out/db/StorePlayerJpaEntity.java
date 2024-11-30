package be.kdg.prog6.storeContext.adapter.out;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(catalog = "store", name = "player")
public class StorePlayerJpaEntity {
    @Id
    @Column(name="player_id")
    private UUID playerId;

    @Column(name="name")
    private String name;

    public StorePlayerJpaEntity() {
    }

    public StorePlayerJpaEntity(UUID playerId, String name) {
        this.playerId = playerId;
        this.name = name;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public String getName() {
        return name;
    }

}
