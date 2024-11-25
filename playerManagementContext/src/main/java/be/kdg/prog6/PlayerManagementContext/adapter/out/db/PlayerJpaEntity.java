package be.kdg.prog6.PlayerManagementContext.adapter.out.db;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(catalog="player_management", name="players")
public class PlayerJpaEntity{
    @Id
    @Column(name="player_id", columnDefinition = "CHAR(36)")
    private UUID playerId;

    @Column(name="name")
    private String name;


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

}
